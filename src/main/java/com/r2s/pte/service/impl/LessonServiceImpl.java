package com.r2s.pte.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.r2s.pte.common.CodeCategory;
import com.r2s.pte.common.File;
import com.r2s.pte.common.Logger;
import com.r2s.pte.common.ObjectMapper;
import com.r2s.pte.common.TypeError;
import com.r2s.pte.dto.CategoryDTO;
import com.r2s.pte.dto.LessonAudioCreateDTO;
import com.r2s.pte.dto.LessonCreateDIDTO;
import com.r2s.pte.dto.LessonCreateDTO;
import com.r2s.pte.dto.LessonCreationDTO;
import com.r2s.pte.dto.LessonDetailViewDTO;
import com.r2s.pte.dto.LessonMediaDTO;
import com.r2s.pte.dto.LessonViewDTO;
import com.r2s.pte.dto.PaginationDTO;
import com.r2s.pte.dto.RequestLessonDTO;
import com.r2s.pte.dto.UserContext;
import com.r2s.pte.entity.Category;
import com.r2s.pte.entity.Lesson;
import com.r2s.pte.entity.LessonCategory;
import com.r2s.pte.entity.QuestionGroup;
import com.r2s.pte.exception.ErrorMessageException;
import com.r2s.pte.mapper.CategoryMapper;
import com.r2s.pte.mapper.LessonMapper;
import com.r2s.pte.repository.LessonRepository;
import com.r2s.pte.service.CategoryService;
import com.r2s.pte.service.DiscussionService;
import com.r2s.pte.service.LessonCategoryService;
import com.r2s.pte.service.LessonMediaService;
import com.r2s.pte.service.LessonService;
import com.r2s.pte.service.LessonTestedService;
import com.r2s.pte.service.QuestionGroupService;
import com.r2s.pte.service.QuestionService;
import com.r2s.pte.service.strategy.LessonStrategy;
import com.r2s.pte.util.FileHandle;
import com.r2s.pte.util.LessonDAO;

@Transactional
@Service
public class LessonServiceImpl implements LessonService {
	@Autowired
	private LessonRepository lessonRepository;
	@Autowired
	private LessonDAO lessonDAO;
	@Autowired
	private LessonCategoryService lessonCategoryService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private LessonMapper lessonMapper;
	@Autowired
	private CategoryMapper categoryMapper;
	@Autowired
	private QuestionService questionService;
	private PaginationDTO paginationDTO;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private QuestionGroupService questionGroupService;
	@Autowired
	private FileHandle fileHandle;
	@Autowired
	private LessonMediaService lessonMediaService;
	@Resource(name = "ReadAloudStrategy")
	private LessonStrategy readAloudStrategy;
	@Autowired
	private DiscussionService discussionService;
	@Autowired
	private LessonTestedService lessonTestedService;

	@Override
	public LessonCreateDTO save(LessonCreateDTO entity) {
		List<CategoryDTO> categoryDTOs = entity.getCategoryDTO();
		if (categoryDTOs != null && categoryDTOs.size() > 0) {
			return saveWithCategory(categoryDTOs, entity);
		} else {
			return saveWithNullCategory(entity);
		}
	}

	private LessonCreateDTO saveWithNullCategory(LessonCreateDTO entity) {
		// save lesson
		Lesson lessonSaved = setOrderAndSave(entity, null);
		// save question Group
		QuestionGroup questionGroup = this.questionGroupService.save(lessonSaved, entity.getQuestionGroup());
		this.questionService.saveFromLessonService(entity.getQuestionDTO(), questionGroup,
				questionGroup.getIsEmbedded());
		// Name Lesson added successfully
				Logger.logInfo.info(this.messageSource.getMessage("AddedEntitySuccess", null, null), UserContext.getId(),
						lessonSaved.getTitle(), "lesson", "unknow");
		return this.lessonMapper.mapToDTO(lessonSaved);
	}

	private LessonCreateDTO saveWithCategory(List<CategoryDTO> categoryDTOs, LessonCreateDTO entity) {
		int firstIndex = 0;
		CategoryDTO firstCategory = categoryDTOs.get(firstIndex);
		LessonCategory lessonCategory = null;
		Lesson lessonSaved = setOrderAndSave(entity, firstCategory);
		String nameCategory = "";
		for (CategoryDTO item : categoryDTOs) {
			{
				Category category = this.categoryMapper.map(categoryService.findById(item.getId()));
				// Valid Category
				this.categoryService.checkCategoryIsParent(category);
				nameCategory = category.getName();
				lessonCategory = this.lessonCategoryService.saveLessonCategory(lessonSaved, category);
			}
		}
		Boolean isEmbedded = lessonCategory.getLesson().getQuestionGroup().getIsEmbedded();
		// Save Question
		this.questionService.saveFromLessonService(entity.getQuestionDTO(),
				lessonCategory.getLesson().getQuestionGroup(), isEmbedded);
		// Name Lesson added successfully
		Logger.logInfo.info(this.messageSource.getMessage("AddedEntitySuccess", null, null), UserContext.getId(),
				lessonSaved.getTitle(), "lesson", nameCategory);
		return this.lessonMapper.mapToDTO(lessonSaved);
	}

	private Lesson setOrderAndSave(LessonCreateDTO dto, CategoryDTO firstCategory) {
		Lesson lesson = lessonMapper.mapToEntity(dto);
		// Set order
		if (firstCategory != null)
			lesson.setZOrder(setOrder(firstCategory.getId()));
		// Save Lesson
		return save(lesson);
	}

	@Override
	public void save(LessonCreationDTO lesson) {
		LessonAudioCreateDTO lessonAudioDTO = lesson.getLesson();
		MultipartFile image = lesson.getImage();
		MultipartFile shadowing = lesson.getShadowing();
		this.lessonMediaService.uploadFile(lessonAudioDTO, image, shadowing);
		save(lessonAudioDTO);
	}
	public void update(LessonCreationDTO lesson, Long id)
	{
		LessonAudioCreateDTO lessonAudioDTO = lesson.getLesson();
		MultipartFile image = lesson.getImage();
		MultipartFile shadowing = lesson.getShadowing();
		lessonMediaService.uploadFile(lessonAudioDTO, image, shadowing);
		update(lessonAudioDTO,id);
	}
	private void update(LessonAudioCreateDTO dto, Long id)
	{
		LessonCreateDTO lessonCreateDTO = dto.getLessonCreate();
		List<LessonMediaDTO> lessonMediaDTOs = dto.getLessonMedias();
		//update lesson
		update(id, lessonCreateDTO);
		Lesson lesson = findById(id);
		if(lessonMediaDTOs != null && lessonMediaDTOs.size()>0)
		{
			this.lessonMediaService.update(lessonMediaDTOs, lesson);
		}
	}

	@Override
	public void save(LessonCreateDIDTO lessonCreateDIDTO) {
		LessonCreateDTO lessonCreateDTO = lessonCreateDIDTO.getLessonCreateDTO();
		MultipartFile fileUp = lessonCreateDIDTO.getImage();
		if (fileUp != null) {
			File file = new File();
			file.setFile(fileUp);
			this.fileHandle.update(file);
			lessonCreateDTO.setSourceMediaLinkImage(file.getFileName());
		}
		save(lessonCreateDTO);
	}

	@Override
	public LessonCreateDTO save(LessonAudioCreateDTO lessonAudioCreateDTO) {
		LessonCreateDTO lessonCreateDTO = lessonAudioCreateDTO.getLessonCreate();
		LessonCreateDTO lessonCreateDTOSaved = save(lessonCreateDTO);
		Lesson lesson = findById(lessonCreateDTOSaved.getId());
		List<LessonMediaDTO> lessonMediaDTOs = lessonAudioCreateDTO.getLessonMedias();
		if (lessonMediaDTOs != null && lessonMediaDTOs.size() > 0)
			lessonMediaService.save(lessonMediaDTOs, lesson);
		return lessonCreateDTOSaved;
	}

	@Override
	public Long getByQuestion(Long questionId) {
		Lesson lesson = this.lessonRepository.findByQuestionId(questionId).orElseThrow(
				() -> new ErrorMessageException(String.format(messageSource.getMessage("NotFound", null, null),
						"Question", "Id", String.valueOf(questionId)), TypeError.NotFound));
		return lesson.getId();

	}

	@Override
	public boolean isExists(Long id) {
		return lessonRepository.existsById(id);
	}

	@Override
	public PaginationDTO findAll(RequestLessonDTO requestDTO) {
		paginationDTO = new PaginationDTO();
		Pageable pageable = PageRequest.of(requestDTO.getPageNumber(), requestDTO.getLimit());// Page: 0 and Member: 10
		List<Object> lessons = this.lessonRepository.findAll(pageable).toList().stream()
				.map(item -> this.lessonMapper.mapToViewDTO(item)).collect(Collectors.toList());
		Page<Lesson> pageLesson = this.lessonRepository.findAll(pageable);
		PaginationDTO pageDTO = new PaginationDTO(lessons, pageLesson.isFirst(), pageLesson.isLast(),
				pageLesson.getTotalPages(), pageLesson.getTotalElements(), pageLesson.getSize(),
				pageLesson.getNumber());

		return pageDTO;
	}

	@Override
	public Lesson findById(Long id) {
		Lesson lesson = lessonRepository.findById(id).orElseThrow(() -> new ErrorMessageException(
				String.format(messageSource.getMessage("NotFound", null, null), "Lesson", "Id", String.valueOf(id)),
				TypeError.NotFound));
		Logger.logInfo.info("Get lesson by id {}", "Lesson");
		return lesson;

	}

	@Override
	public boolean existsById(Long id) {
		return lessonRepository.existsById(id);
	}

	@Override
	public long count() {
		return lessonRepository.count();
	}

	@Override
	public String getContentByQuestionId(Long questionId) {
		return this.lessonRepository.getContentByQuestionId(questionId);
	}

	public Lesson save(Lesson lesson) {
		LocalDateTime dateTime = LocalDateTime.now();
		lesson.setCreatedDate(dateTime);
		lesson.setModifiedDate(dateTime);
		// Set time Question Group
		lesson.getQuestionGroup().setCreatedDate(dateTime);
		lesson.getQuestionGroup().setModifiedDate(dateTime);
		lesson.getQuestionGroup().setCreatedBy(lesson.getCreatedBy());
		lesson.getQuestionGroup().setModifiedBy(lesson.getModifiedBy());
		lesson.getQuestionGroup().setLesson(lesson);
		return this.lessonRepository.save(lesson);
	}
	

	@Override
	public void update(long id, LessonCreateDTO dto) {
		// Get Category from Database
		List<CategoryDTO> categoryDTOs = dto.getCategoryDTO();
		int firstIndex = 0;
		CategoryDTO categoryDTO = categoryDTOs.get(firstIndex);
		Category category = this.categoryMapper.map(categoryService.findById(categoryDTO.getId()));
		String codeCategory = category.getCode();
		// for lesson without update question group and question.
		if (codeCategory.equals(CodeCategory.CODE_RA) || codeCategory.equals(CodeCategory.CODE_WFD)
				|| codeCategory.equals(CodeCategory.CODE_RS) || codeCategory.equals(CodeCategory.CODE_RL)
				|| codeCategory.equals(CodeCategory.CODE_SWT) || codeCategory.equals(CodeCategory.CODE_ESSAY))
			readAloudStrategy.update(id, dto, category);
		else {
			Lesson lesson = findById(id);
			// map dto
			lesson.setModifiedDate(LocalDateTime.now());
			lesson.setModifiedBy(UserContext.getId());
			this.lessonMapper.mapToDtoUpdate(lesson, dto);
			//set zorder
			Long zOrder = dto.getZorder();
			if(zOrder != null)
				lesson.setZOrder(zOrder);
			// Update lesson
			Lesson lessonUpdated = this.lessonRepository.save(lesson);
			// Update LessonCategory
			lessonCategoryService.updateListLessonCategory(categoryDTOs, lessonUpdated);
			// Find Question Type
			// Update Question Group
			QuestionGroup questionGroupUpdated = this.questionGroupService.updateByLesson(id, dto.getQuestionGroup());
			Boolean isEmbedded = questionGroupUpdated.getIsEmbedded();
			// fixing
			this.questionService.updateByQuestionGroup(questionGroupUpdated.getId(), dto.getQuestionDTO(),
					questionGroupUpdated, isEmbedded);
			Logger.logInfo.info(this.messageSource.getMessage("UpdateEntitySuccess", null, null),
					lessonUpdated.getTitle(), "lesson");
		}
	}

	@Override
	public PaginationDTO findByRequestLessonDTO(RequestLessonDTO requestDTO) {
		paginationDTO = new PaginationDTO();
		String query = this.makeQueryWithRequestDAO(requestDTO);
		try {
			List<LessonViewDTO> lessons = lessonDAO.findByQuery(query, requestDTO.getPageNumber(), requestDTO.getLimit())
					.stream().map(item -> this.lessonMapper.mapToViewDTO(item)).collect(Collectors.toList());
			if (lessons.size() < 1 || lessons == null) {
				throw new ErrorMessageException(String.format(messageSource.getMessage("NotFound", null, null), "Lesson",
						"request", String.valueOf("null")), TypeError.NotFound);
			}
			Logger.logInfo.info("Get lesson by requestDAO {}", "Lesson");
			paginationDTO = paginationDTO.customPaginationLesson(lessons, lessonDAO.countTotalRecords(query),
					requestDTO.getPageNumber(), requestDTO.getLimit());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return paginationDTO;
	}

	@Override
	public LessonDetailViewDTO getDetailById(Long id) {
		LessonDetailViewDTO detailViewDTO = lessonMapper.mapToViewDetailDTO(findById(id));
		Logger.logInfo.info("Get lessondetail by id {}", "Lesson");
		return detailViewDTO;
	}

	@Override
	public LessonCreateDIDTO readJson(String lesson, MultipartFile image) {
		LessonCreateDIDTO lessonCreateDIDTO = new LessonCreateDIDTO();
		LessonCreateDTO lessonCreateDTO = new LessonCreateDTO();
		try {
			lessonCreateDTO = ObjectMapper.getMapper().readValue(lesson, LessonCreateDTO.class);
		} catch (JsonProcessingException ex) {
			ex.printStackTrace();
		}
		lessonCreateDIDTO.setLessonCreateDTO(lessonCreateDTO);
		if (image != null)
			lessonCreateDIDTO.setImage(image);
		else
			throw new ErrorMessageException(this.messageSource.getMessage("FileRequired", null, null),
					TypeError.BadRequest);
		return lessonCreateDIDTO;
	}

	@Override
	public LessonCreationDTO readJson(String lesson, MultipartFile image, MultipartFile shadowing) {
		LessonCreationDTO lessonCreationDTO = new LessonCreationDTO();
		try {
			lessonCreationDTO = ObjectMapper.getMapper().readValue(lesson, LessonCreationDTO.class);
		} catch (JsonProcessingException ex) {
			ex.printStackTrace();
		}
		if (image != null)
			lessonCreationDTO.setImage(image);
		if (shadowing != null)
			lessonCreationDTO.setShadowing(shadowing);
		return lessonCreationDTO;
	}

	@Override
	public List<Lesson> getAllByZorderCategory(Long zOrder, Long idCategory) {
		return this.lessonRepository.findByCategoryAndZorder(zOrder, idCategory);
	}

	@Override
	public void update(LessonCreateDIDTO dto, long id) {
		boolean isExistsLesson = this.lessonRepository.existsById(id);
		if (!isExistsLesson)
			throw new ErrorMessageException(
					String.format(messageSource.getMessage("NotFound", null, null), "Lesson", "id", String.valueOf(id)),
					TypeError.NotFound);
		LessonCreateDTO lessonCreateDTO = dto.getLessonCreateDTO();
		MultipartFile fileUp = dto.getImage();
		File file = updateImage(fileUp);
		lessonCreateDTO.setSourceMediaLinkImage(file.getFileName());
		update(id, lessonCreateDTO);

	}

	private File updateImage(MultipartFile image) {
		File file = null;
		if (image != null) {
			file = new File();
			file.setFile(image);
			this.fileHandle.update(file);
		}
		return file;
	}

	Long setOrder(Long id) {
		Long count = this.lessonRepository.count();
		return count + 1;
	}

	@Override
	public void active(Long id) {
		Lesson lesson = findById(id);
		lesson.setStatus(true);
		lesson.setUserIdAsAppover(UserContext.getId());
		this.lessonRepository.save(lesson);
	}

	@Override
	public void deleteById(Long id) {
		this.lessonCategoryService.deleteByLessonId(id);
		// delete question group
		this.questionGroupService.deleteByLessonId(id);
		// delete lesson media
		this.lessonMediaService.deleteByLesson(id);
		// delete discussion
		discussionService.deleteByLesson(id);
		// delete lesson tested
		lessonTestedService.deleteByLessonId(id);
		this.lessonRepository.deleteById(id);
	}

	@Override
	public void deleteByListId(List<Long> ids) {
		if (ids != null && ids.size() > 0) {
			ids.forEach(id -> {
				deleteById(id);
			});
		}
	}

	@Override
	public void deleteByIdAndCode(Long id, String code) {
		checkExistId(id);
		boolean isSingleCategory = this.lessonCategoryService.lessonIsSingleCategory(id);
		if (isSingleCategory == true) {
			deleteById(id);
		} else {
			Lesson lesson = this.findById(id);
			lessonCategoryService.deleteByLessonIdAndCodeCategory(lesson.getId(), code);
		}
	}

	private void checkExistId(Long id) {
		boolean isExists = isExists(id);
		if (!isExists)
			throw new ErrorMessageException(String.format(this.messageSource.getMessage("NotFound", null, null),
					"Lesson", "id", String.valueOf(id)), TypeError.NotFound);
	}

	@Override
	public void disableById(Long id) {
		Lesson lesson = findById(id);
		lesson.setStatus(false);
		this.lessonRepository.save(lesson);
	}

	@Override
	public LessonDetailViewDTO getLessonDetailDefault() {
		LessonDetailViewDTO detailViewDTO = lessonMapper
				.mapToViewDetailDTO(lessonRepository.findFirstByOrderByCreatedDateDesc());
		return detailViewDTO;
	}

	@Override
	public String makeQueryWithRequestDAO(RequestLessonDTO requestDTO) {
		// Set up for query
		String categoryQuery = "";
		LocalDate firstDayOfWeek = LocalDate.now();
		LocalDate lastDayOfWeek = LocalDate.now();

		// Set up for query by Week
		int thisWeek = LocalDate.now().get(WeekFields.of(Locale.getDefault()).weekOfMonth());
		while (firstDayOfWeek.get(WeekFields.of(Locale.getDefault()).weekOfMonth()) == thisWeek) {
			firstDayOfWeek = firstDayOfWeek.minusDays(1);
		}
		firstDayOfWeek = firstDayOfWeek.plusDays(1);
		while (lastDayOfWeek.get(WeekFields.of(Locale.getDefault()).weekOfMonth()) == thisWeek) {
			lastDayOfWeek = lastDayOfWeek.plusDays(1);
		}
		lastDayOfWeek = lastDayOfWeek.minusDays(1);

		if (requestDTO.getCategoryId() != null) {
			if(requestDTO.getCategoryId() == 0) {
				categoryQuery = "L.id not in (select lc.lesson.id from LessonCategory lc  )";
			}else {
				String categoryCode = categoryService.findById(requestDTO.getCategoryId()).getCode();
				categoryQuery = "L.id in (select lc.lesson.id from LessonCategory lc where lc.category.id = "
						+ requestDTO.getCategoryId() + " or lc.category.parentCode = '" + categoryCode + "')";
			}
			
		}
		String query = "from Lesson L ";
		// Where in query
		String statusQuery = " L.status = " + requestDTO.getStatus();
		String zOrderQuery = " L.zOrder = " + requestDTO.getSearchKeyWord();
		String sharedQuery = " L.shared = " + requestDTO.getShared();
		String explanationQuery = " L.id in (select QG.lesson.id from QuestionGroup QG where QG.id in (select  Q.questionGroup.id from Question Q where Q.id in (select QS.question.id from QuestionSolution QS where QS.explanation is  ";
		String practiceStatusQuery = " L.id in (select QG.lesson.id from QuestionGroup QG where QG.id in (select  Q.questionGroup.id from Question Q where Q.id in (select US.question.id from UserScore US where US.user =  ";
		String shadowingQuery = " L.lessonSourceMediaLinkShadow is ";
		String titleQuery = "";
		if (requestDTO.getSearchKeyWord() != null) {
			titleQuery = " L.title like '%" + requestDTO.getSearchKeyWord().trim() + "%'";
		}
		String monthQuery = "";
		if (requestDTO.getMonth() != null) {
			LocalDateTime firstDayOfMonth = LocalDateTime.of(LocalDate.now().getYear(), requestDTO.getMonth(), 1, 0, 0,
					0);
			LocalDateTime lastDayOfMonth = LocalDateTime.of(LocalDate.now().getYear(), requestDTO.getMonth(),
					LocalDate.now().lengthOfMonth(), 23, 59, 59);
			monthQuery = "L.createdDate BETWEEN '" + firstDayOfMonth + "' AND '" + lastDayOfMonth + "' ";
		}
		String weekQuery = "";
		if (requestDTO.getMonth() != null) {
			weekQuery = "L.createdDate BETWEEN '" + firstDayOfWeek + "' AND '" + lastDayOfWeek + "' ";
		}

		// Order in query
		String sortByZOrderQuery = " GROUP BY L.zOrder, L.id, L.content, L.createdBy, L.createdDate, L.modifiedDate, L.modifiedBy, L.description, "
				+ "L.explanation, L.internalNotes, L.preparationTime, L.duration, L.shared, L.status, L.languageId, L.lessonSourceMediaLinkShadow, "
				+ "L.lessonSourceMediaLinkVideo, L.lessonSourceMediaLinkImage, L.userIdAsReviewer, L.userIdAsAppover, L.title  Order by L.zOrder DESC ";
		String sortByCreateDateQuery = " GROUP BY L.zOrder, L.id, L.content, L.createdBy, L.createdDate, L.modifiedDate, L.modifiedBy, L.description,"
				+ " L.explanation, L.internalNotes, L.preparationTime, L.duration, L.shared, L.status, L.languageId, L.lessonSourceMediaLinkShadow, "
				+ "L.lessonSourceMediaLinkVideo, L.lessonSourceMediaLinkImage, L.userIdAsReviewer, L.userIdAsAppover, L.title  Order by L.createdDate DESC ";
		String sortByTitleQuery = " GROUP BY L.zOrder, L.id, L.content, L.createdBy, L.createdDate, L.modifiedDate, L.modifiedBy, L.description,"
				+ " L.explanation, L.internalNotes, L.preparationTime, L.duration, L.shared, L.status, L.languageId, L.lessonSourceMediaLinkShadow, "
				+ "L.lessonSourceMediaLinkVideo, L.lessonSourceMediaLinkImage, L.userIdAsReviewer, L.userIdAsAppover, L.title  Order by L.title ASC ";
		// Get lessons by category
		if (requestDTO.getCategoryId() != null) {
			if (query.contains("where")) {
				query = query.concat(" and ").concat(categoryQuery);
			} else {
				query = query.concat("  where ").concat(categoryQuery);
			}

		}
		// Get lessons by status
		if (requestDTO.getStatus() != null) {
			if (query.contains("where")) {
				query = query.concat(" and ").concat(statusQuery);
			} else {
				query = query.concat(" where ").concat(statusQuery);
			}
		}
		// Get lessons by shared
		if (requestDTO.getShared() != null) {
			if (query.contains("where")) {
				query = query.concat(" and ").concat(sharedQuery);
			} else {
				query = query.concat("  where ").concat(sharedQuery);
			}

		}
		// Get lessons by shadowing
		if (requestDTO.getShadowing() != null) {
			if (query.contains("where")) {
				if (requestDTO.getShadowing()) {
					query = query.concat(" and ").concat(shadowingQuery) + " not null ";
				} else {
					query = query.concat(" and ").concat(shadowingQuery) + " null";
				}
			} else {
				if (requestDTO.getShadowing()) {
					query = query.concat(" where ").concat(shadowingQuery) + " not null";
				} else {
					query = query.concat(" where ").concat(shadowingQuery) + " null";
				}
			}

		}
		// Get lessons by explanation
		if (requestDTO.getExplanation() != null) {
			if (query.contains("where")) {
				if (requestDTO.getExplanation()) {
					query = query.concat(" and ").concat(explanationQuery) + " not null )))";
				} else {
					query = query.concat(" and ").concat(explanationQuery) + " null )))";
				}
			} else {
				if (requestDTO.getExplanation()) {
					query = query.concat(" where ").concat(explanationQuery) + " not null";
				} else {
					query = query.concat(" where ").concat(explanationQuery) + " null";
				}
			}

		}

		// Get lessons by practiceStatus
		if (requestDTO.getPracticeStatus() != null) {
			if (requestDTO.getPracticeStatus() == true) {
				if (query.contains("where")) {
					query = query.concat(" and ").concat(practiceStatusQuery) + requestDTO.getUserId() + " )))";
				} else {
					query = query.concat(" where ").concat(practiceStatusQuery) + requestDTO.getUserId() + " )))";

				}

			}
		}
		// Get lessons by searchKeyWord
		if (requestDTO.getSearchKeyWord() != null) {
			if (query.contains("where")) {
				if (isInteger(requestDTO.getSearchKeyWord())) {
					query = query.concat(" and ").concat(zOrderQuery);
				} else {
					query = query.concat(" and ").concat(titleQuery);
				}
			} else {
				if (isInteger(requestDTO.getSearchKeyWord())) {
					query = query.concat("  where ").concat(zOrderQuery);
				} else {
					query = query.concat("  where ").concat(titleQuery);
				}
			}

		}
		// Get lessons by month
		if (requestDTO.getMonth() != null) {
			if (query.contains("where")) {
				query = query.concat(" and ").concat(monthQuery);
			} else {
				query = query.concat("  where ").concat(monthQuery);
			}
		}
		// Get lessons by week
		if (requestDTO.getWeekly() != null) {
			if (requestDTO.getWeekly() == true) {
				if (query.contains("where")) {
					query = query.concat(" and ").concat(weekQuery);
				} else {
					query = query.concat("  where ").concat(weekQuery);
				}
			}

		}

		// Get lessons with Order
		if (requestDTO.getSort() != null) {
			if (requestDTO.getSort() == 1) {
				query = query.concat(sortByCreateDateQuery);
			}
			if (requestDTO.getSort() == 2) {
				query = query.concat(sortByZOrderQuery);
			}
			if (requestDTO.getSort() == 3) {
				query = query.concat(sortByTitleQuery);
			}

		}
		System.out.print(query);
		return query;
	}

	@Override
	public boolean isInteger(String s) {
		if (s == null || s.equals("")) {
			return false;
		}

		try {
			Integer.parseInt(s);
			return true;
		} catch (NumberFormatException e) {

		}
		return false;
	}

}
