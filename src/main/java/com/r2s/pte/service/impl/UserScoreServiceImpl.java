package com.r2s.pte.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.r2s.pte.common.CodeCategory;
import com.r2s.pte.common.TypeError;
import com.r2s.pte.dto.CategoryDTO;
import com.r2s.pte.dto.DiscussionCreateDTO;
import com.r2s.pte.dto.LessonCreateDTO;
import com.r2s.pte.dto.ParamUserScoreDTO;
import com.r2s.pte.dto.QuestionDTO;
import com.r2s.pte.dto.QuestionResponseUserDTO;
import com.r2s.pte.dto.QuestionSolutionDTO;
import com.r2s.pte.dto.ScoreResponseDTO;
import com.r2s.pte.dto.UserContext;
import com.r2s.pte.dto.UserScoreDTO;
import com.r2s.pte.entity.Category;
import com.r2s.pte.entity.Question;
import com.r2s.pte.entity.QuestionResponseUser;
import com.r2s.pte.entity.QuestionSolution;
import com.r2s.pte.entity.UserScore;
import com.r2s.pte.exception.ErrorMessageException;
import com.r2s.pte.mapper.UserScoreMapper;
import com.r2s.pte.mapper.strategy.UserScoreMapperStrategy;
import com.r2s.pte.repository.CategoryRepository;
import com.r2s.pte.repository.QuestionSolutionRepository;
import com.r2s.pte.repository.UserScoreRepository;
import com.r2s.pte.service.CategoryService;
import com.r2s.pte.service.DiscussionService;
import com.r2s.pte.service.LessonService;
import com.r2s.pte.service.QuestionResponseUserService;
import com.r2s.pte.service.QuestionService;
import com.r2s.pte.service.QuestionSolutionService;
import com.r2s.pte.service.UserScoreService;
import com.r2s.pte.service.strategy.QuestionScoreStrategy;
import com.r2s.pte.util.HandleGeneral;

@Service
@Transactional
public class UserScoreServiceImpl implements UserScoreService {

	@Autowired
	private UserScoreRepository userScoreRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private UserScoreMapper userScoreMapper;
	@Autowired
	private QuestionResponseUserService questionResponseUserService;
	@Resource(name = "SingleScoreStrategy")
	private QuestionScoreStrategy singleScoreStrategy;
	@Resource(name = "MultipleChoiceScoreStrategy")
	private QuestionScoreStrategy multipleScoreStrategy;
	@Resource(name = "FillInTheBlankScore")
	private QuestionScoreStrategy fillInTheBlankStrategy;
	@Resource(name = "ReOrderScoreStrategy")
	private QuestionScoreStrategy reOrderScoreStrategy;
	@Resource(name = "WriteFromDictationStrategy")
	private QuestionScoreStrategy writeDictationStrategy;
	@Resource(name = "HighlightIncorrectWordScore")
	private QuestionScoreStrategy highlightIncorrectWordStrategy;
	@Autowired
	private QuestionSolutionService questionSolutionService;
	@Autowired
	private QuestionSolutionRepository questionSolutionRepository;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private HandleGeneral handleGeneral;
	@Autowired
	private DiscussionService discussionService;
	@Autowired
	private QuestionService questionService;
	@Autowired
	private LessonService lessonService;
	@Resource(name = "FIBMapper")
	private UserScoreMapperStrategy userScoreMapperStrategy;
	@Resource(name = "AISpeechMapper")
	private UserScoreMapperStrategy AISpeechMapperStrategy;
	private String codeCategory;
	private Long idQuestion;
	private LocalDateTime CREATE_DATE;

	@Override
	public void setDateTimeNow(UserScore userScore, LocalDateTime createDate) {
		userScore.setCreatedBy(UserContext.getId());
		userScore.setCreatedDate(createDate);
		userScore.setModifiedBy(UserContext.getId());
		userScore.setModifiedDate(createDate);
	}

	@Override
	public List<ScoreResponseDTO> getByLessoAndUser(Long lessonId, Long userId) {
		CategoryDTO categoryDTO = this.categoryService.getByLessonId(lessonId);
		String codeCategory = categoryDTO.getCode();
		List<UserScore> userScores = new ArrayList<>();
		if (userId != null) {
			userScores = this.userScoreRepository.getByLessonAndUser(lessonId, userId);
		} else {
			userScores = this.userScoreRepository.getByLesson(lessonId);
		}
		List<UserScore> userScoreSorted = sortDESCByCreateDate(userScores);
		List<List<UserScore>> userScoreGroupDates = handleGroupByCreateDate(userScoreSorted);
		List<ScoreResponseDTO> scoreResponseDTOs = handleUserScoreToShow(userScoreGroupDates, codeCategory);
		return scoreResponseDTOs;
	}

	@Override
	public List<ScoreResponseDTO> getByQuestionAndUser(ParamUserScoreDTO paramUserScoreDTO) {
		String codeCategory = paramUserScoreDTO.getCodeCategory();
		// valid code category
		this.categoryService.checkCodeCategory(codeCategory);
		List<Long> questionIds = paramUserScoreDTO.getQuestionIds();
		Long userId = paramUserScoreDTO.getUserId();
		List<UserScore> userScores = new ArrayList<UserScore>();
		if (paramUserScoreDTO.getUserId() != null)
			userScores = this.userScoreRepository.getByQuestionAndUser(questionIds, userId);
		else
			userScores = this.userScoreRepository.getByQuestion(questionIds);
		List<UserScore> userScoreSorted = sortDESCByCreateDate(userScores);
		List<List<UserScore>> userScoreGroupDates = handleGroupByCreateDate(userScoreSorted);
		List<ScoreResponseDTO> scoreResponseDTOs = handleUserScoreToShow(userScoreGroupDates, codeCategory);
		return scoreResponseDTOs;
	}

	private List<UserScore> sortDESCByCreateDate(List<UserScore> userScores) {
		List<UserScore> userScoreList = new ArrayList<UserScore>();

		for (int i = userScores.size() - 1; i >= 0; i--) {
			userScoreList.add(userScores.get(i));
		}
		return userScoreList;
	}

	private List<List<UserScore>> handleGroupByCreateDate(List<UserScore> userScores) {
		List<List<UserScore>> userScoreGroupDates = new ArrayList<>();
		int sizeUserScore = userScores.size();
		for (int i = 0; i < sizeUserScore; i++) {
			UserScore userScoreCurrent = userScores.get(i);
			LocalDateTime createDate = userScoreCurrent.getCreatedDate();
			if (userScoreGroupDates.size() == 0) {
				addNewItemUserScore(userScoreCurrent, userScoreGroupDates);
			} else {
				boolean isExistsItem = false;
				int sizeGroupDate = userScoreGroupDates.size();
				for (int j = 0; j < sizeGroupDate; j++) {
					List<UserScore> userScoreList = userScoreGroupDates.get(j);
					int firstIndex = 0;
					UserScore userScore = userScoreList.get(firstIndex);
					if (createDate.compareTo(userScore.getCreatedDate()) == 0) {
						userScoreList.add(userScoreCurrent);
						// mark done
						isExistsItem = true;
						break;
					}
				}
				if (!isExistsItem) {
					addNewItemUserScore(userScoreCurrent, userScoreGroupDates);
				}
			}
		}
		return userScoreGroupDates;
	}

	List<ScoreResponseDTO> handleUserScoreToShow(List<List<UserScore>> userScoreGroupDates, String codeCategory) {
		List<ScoreResponseDTO> scoreResponseDTOs = new ArrayList<>();
		for (List<UserScore> userScoreList : userScoreGroupDates) {
			if (codeCategory.equals(CodeCategory.CODE_ROD)) {
				scoreResponseDTOs.add(this.userScoreMapper.mapToOrder(userScoreList));
			} else if (codeCategory.equals(CodeCategory.CODE_WFD)) {
				scoreResponseDTOs.add(this.userScoreMapper.mapToWFD(userScoreList));
			} else if (codeCategory.equals(CodeCategory.CODE_FIB)) {
				scoreResponseDTOs.add(userScoreMapperStrategy.map(userScoreList));
			} else if (CodeCategory.AI_SPEECH_CATEGORIES.contains(codeCategory)) {
				scoreResponseDTOs.add(AISpeechMapperStrategy.map(userScoreList));
			} else {
				scoreResponseDTOs.add(this.userScoreMapper.mapToResponse(userScoreList));
			}
		}
		return scoreResponseDTOs;
	}

	void addNewItemUserScore(UserScore userScoreCurrent, List<List<UserScore>> userScoreGroupDates) {
		List<UserScore> userScoreItems = new ArrayList<>();
		userScoreItems.add(userScoreCurrent);
		userScoreGroupDates.add(userScoreItems);
	}

	private Integer handleScore(String codeCategory, QuestionDTO questionDTO,
			QuestionResponseUserDTO questionResponseUsers, QuestionSolutionDTO questionSolutionDTO) {
		Integer score = 0;
		if (codeCategory.equals(CodeCategory.CODE_MCQSA) || codeCategory.equals(CodeCategory.CODE_SMW)
				|| codeCategory.equals(CodeCategory.CODE_HCS)) {
			score = this.singleScoreStrategy.doScore(questionDTO, questionResponseUsers, questionSolutionDTO);
		} else if (codeCategory.equals(CodeCategory.CODE_MCQMA)) {
			score = this.multipleScoreStrategy.doScore(questionDTO, questionResponseUsers, questionSolutionDTO);
		} else if (codeCategory.equals(CodeCategory.CODE_FIB) || codeCategory.equals(CodeCategory.CODE_DD)) {
			score = this.fillInTheBlankStrategy.doScore(questionDTO, questionResponseUsers, questionSolutionDTO);
		} else if (codeCategory.equals(CodeCategory.CODE_ROD))
			score = this.reOrderScoreStrategy.doScore(questionDTO, questionResponseUsers, questionSolutionDTO);
		else if (codeCategory.equals(CodeCategory.CODE_WFD))
			score = writeDictationStrategy.doScore(questionDTO, questionResponseUsers, questionSolutionDTO);
		return score;
	}

	public ScoreResponseDTO save(List<UserScoreDTO> userScoreDTOs) {
		List<UserScore> userScores = new ArrayList<UserScore>();
		idQuestion = getIdQuestion(userScoreDTOs.get(0));
		codeCategory = getCategoryByQuestion(idQuestion);
		QuestionSolutionDTO questionSolutionDTO = null;
		// switch do save for ROD
		if (codeCategory.equals(CodeCategory.CODE_ROD)) {
			return this.reOrderScoreStrategy.save(userScoreDTOs);
		}
		CREATE_DATE = LocalDateTime.now();
		for (UserScoreDTO userScoreDTO : userScoreDTOs) {
			QuestionDTO questionDTO = userScoreDTO.getQuestion();
			// set solution
			setSolutionByQuestionId(questionDTO);
			List<QuestionResponseUserDTO> questionResponseUserDTOs = userScoreDTO.getQuestionResponseUsers();
			if (questionResponseUserDTOs != null) {
				for (QuestionResponseUserDTO questionResponseUserDTO : questionResponseUserDTOs) {
					Integer score = handleScore(codeCategory, questionDTO, questionResponseUserDTO,
							questionSolutionDTO);
					// Save question Response User
					questionResponseUserDTO.setQuestionDTO(questionDTO);
					QuestionResponseUser questionResponseUserSaved = this.questionResponseUserService
							.save(questionResponseUserDTO);
					UserScore userScoreSaved = setAndSaveScore(userScoreDTO, score, questionResponseUserSaved);
					userScores.add(userScoreSaved);
				}
			}
		}
		// Save Discussion Include Answer
		LessonCreateDTO lessonCreateDTO = new LessonCreateDTO();
		lessonCreateDTO.setId(lessonService.getByQuestion(idQuestion));
		discussionService.save(
				new DiscussionCreateDTO(null, null, true, null, CREATE_DATE, CREATE_DATE, null, lessonCreateDTO, null));

		switch (codeCategory) {
		case CodeCategory.CODE_ROD: {
			return this.userScoreMapper.mapToOrder(userScores);
		}
		case CodeCategory.CODE_WFD: {
			return this.userScoreMapper.mapToWFD(userScores);
		}
		default:
			ScoreResponseDTO scoreResponseDTO = this.userScoreMapper.mapToResponse(userScores);
			return scoreResponseDTO;
		}
	}

	@Override
	public void deleteByQuestionId(Long questionId) {
		List<UserScore> userScores = this.userScoreRepository.getByQuestionId(questionId);
		if (userScores != null && userScores.size() > 0) {
			userScores.forEach(userScore -> {
				userScore.setQuestion(null);
				userScore.setQuestionSolution(null);
				userScore.setQuestionResponseUser(null);
				this.userScoreRepository.save(userScore);
				this.userScoreRepository.deleteById(userScore.getId());
			});
		}
	}

	public ScoreResponseDTO saveHighlightIncorrect(List<UserScoreDTO> userScoreDTOs, Long lessonId) {
		List<UserScore> userScores = new ArrayList<UserScore>();
		if (userScoreDTOs == null || userScoreDTOs.size() == 0)
			return null;
		CREATE_DATE = LocalDateTime.now();
		for (UserScoreDTO userScoreDTO : userScoreDTOs) {
			Long idQuestion = getIdQuestion(userScoreDTO);
			codeCategory = getCategoryByQuestion(idQuestion);
			Question question = this.questionService.findById(idQuestion);
			Integer score;
			if (question.getName() != null && question.getName().equals("orther"))
				score = -1;
			else
				score = 1;
			int first = 0;
			QuestionResponseUserDTO questionResponseUserDTO = userScoreDTO.getQuestionResponseUsers().get(first);
			QuestionDTO questionDTO = userScoreDTO.getQuestion();
			questionResponseUserDTO.setQuestionDTO(questionDTO);
			QuestionResponseUser questionResponseUserSaved = this.questionResponseUserService
					.save(questionResponseUserDTO);
			userScoreDTO.setQuestion(questionDTO);
			UserScore userScoreSaved = setAndSaveScore(userScoreDTO, score, questionResponseUserSaved);
			userScores.add(userScoreSaved);
		}
		ScoreResponseDTO scoreResponseDTO = this.userScoreMapper.mapToHighlightIncorrect(userScores, lessonId);

		// Save Discussion Include Answer
		LessonCreateDTO lessonCreateDTO = new LessonCreateDTO();
		lessonCreateDTO.setId(lessonId);
		discussionService.save(
				new DiscussionCreateDTO(null, null, true, null, CREATE_DATE, CREATE_DATE, null, lessonCreateDTO, null));

		return scoreResponseDTO;
	}

	private UserScore setAndSaveScore(UserScoreDTO userScoreDTO, Integer score,
			QuestionResponseUser questionResponseUserSaved) {
		UserScore userScore = this.userScoreMapper.mapToEntity(userScoreDTO);
		userScore.setScore(score);
		userScore.setQuestionResponseUser(questionResponseUserSaved);
		if (!codeCategory.equals(CodeCategory.CODE_WFD))
			setSolution(userScore);
		else
			setSolutionWFD(userScore);
		String component = userScore.getComponent();
		if (component == null || component.equals(""))
			component = " ";
		userScore.setComponent(component);
		setDateTimeNow(userScore, CREATE_DATE);
		return this.userScoreRepository.save(userScore);
	}

	private String getCategoryByQuestion(Long idQuestion) {
		List<Category> categories = this.categoryRepository.getByQuestion(idQuestion);
		if (categories == null || categories.size() == 0)
			throw new ErrorMessageException(String.format(messageSource.getMessage("NotFound", null, null), "Question",
					"id", String.valueOf(idQuestion)), TypeError.NotFound);
		Category category = categories.get(0);
		return category.getCode();
	}

	private Long getIdQuestion(UserScoreDTO dto) {
		return dto.getQuestion().getId();
	}

	@Override
	public void setSolutionByQuestionId(QuestionDTO questionDTO) {
		List<QuestionSolutionDTO> questionSolutionDTOs = this.questionSolutionService
				.findByQuestion(questionDTO.getId());
		questionDTO.setSolutions(questionSolutionDTOs);
	}

	// set Solution when response is TRUE
	private void setSolution(UserScore userScore) {
		if(userScore.getScore()!=null) {
			if (userScore.getScore() > 0) {
				List<QuestionSolution> questionSolutions = this.questionSolutionRepository
						.getQuestionSolutionByQuestionId(userScore.getQuestion().getId());
				for (QuestionSolution questionSolution : questionSolutions) {
					String response = userScore.getQuestionResponseUser().getValueText();
					String result = questionSolution.getValueText();
					if (response.equals(result)) {
						userScore.setQuestionSolution(questionSolution);
						return;
					}
					if (codeCategory.equals(CodeCategory.CODE_SMW)) {
						userScore.setQuestionSolution(questionSolution);
					}
				}
			} else
				userScore.setQuestionSolution(null);
		}else {
			userScore.setQuestionSolution(null);
		}
	}

	@Override
	public void deleteByCreate(String dateTime) {

		LocalDateTime localDateTime = this.handleGeneral.parseLocalDateTime(dateTime);
		List<Long> userScoreIds = this.userScoreRepository.getIdByCreateDateEquals(localDateTime);
		for (Long id : userScoreIds) {
			UserScore userScore = userScoreRepository.findById(id).get();
			userScore.setQuestion(null);
			userScore.setQuestionSolution(null);
			UserScore userScoreUpdate = this.userScoreRepository.save(userScore);
			this.userScoreRepository.deleteById(userScoreUpdate.getId());
		}
	}

	private void setSolutionWFD(UserScore userScore) {
		List<QuestionSolution> questionSolutions = this.questionSolutionRepository
				.getQuestionSolutionByQuestionId(userScore.getQuestion().getId());
		for (QuestionSolution questionSolution : questionSolutions) {
			userScore.setQuestionSolution(questionSolution);
		}

	}

	@Override
	public ScoreResponseDTO saveByAIText(UserScoreDTO userScoreDTO) {
		List<QuestionResponseUserDTO> questionResponseUserDTOS = userScoreDTO.getQuestionResponseUsers();
		for (QuestionResponseUserDTO response : questionResponseUserDTOS) {
			// save Response
			QuestionDTO questionDTO = userScoreDTO.getQuestion();
			response.setQuestionDTO(questionDTO);
			QuestionResponseUser questionResponseUser = this.questionResponseUserService.save(response);
			// set User Score
			UserScore userScore = this.userScoreMapper.mapToEntity(userScoreDTO);
			userScore.setQuestionResponseUser(questionResponseUser);
			CREATE_DATE = LocalDateTime.now();
			setDateTimeNow(userScore, CREATE_DATE);
			UserScore saved = this.userScoreRepository.save(userScore);
			saveDiscussionIncludeAnswer(questionDTO.getId());
			return this.userScoreMapper.mapToAIText(saved);
		}
		return null;
	}

	@Override
	public ScoreResponseDTO saveSpeech(MultipartFile speech, Long questionId) {
		if (speech != null && questionId != null) {
			try {
				QuestionResponseUser questionResponseUser = questionResponseUserService.saveBySpeech(speech, questionId);
				UserScore userScore = new UserScore();
				userScore.setQuestionResponseUser(questionResponseUser);
				userScore.setQuestion(this.questionService.findById(questionId));
				setDateTimeNow(userScore, questionResponseUser.getCreatedDate());
				userScore.setUser(UserContext.getId());
				UserScore saved = this.userScoreRepository.save(userScore);
				saveDiscussionIncludeAnswer(questionId);
				return this.userScoreMapper.mapToAISpeech(saved);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		return null;
	}

	@Override
	public void AIDoScore(List<UserScoreDTO> userScoreDTOs) {
		UserScoreDTO userScoreFirst = userScoreDTOs.get(0);
		QuestionResponseUser questionResponseUser = this.questionResponseUserService
				.findByQuestionId(userScoreFirst.getQuestion().getId(), userScoreFirst.getCreateDate());
		CREATE_DATE = questionResponseUser.getCreatedDate();
		// delete
		deleteByCreateDate(CREATE_DATE);
		for (UserScoreDTO userScoreDTO : userScoreDTOs) {
			UserScore userScore = this.userScoreMapper.mapToEntity(userScoreDTO);
			setDateTimeNow(userScore, CREATE_DATE);
			userScore.setQuestionResponseUser(questionResponseUser);
			userScore.setScore(userScoreDTO.getScore());
			userScore.setUser(UserContext.getId());
			this.userScoreRepository.save(userScore);
		}
		saveDiscussionIncludeAnswer(userScoreFirst.getQuestion().getId());
	}

	private List<Long> findByCreateDate(LocalDateTime createDate) {
		List<Long> ids = this.userScoreRepository.getIdByCreateDateEquals(createDate);
		if (ids != null && ids.size() > 0)
			return ids;
		throw new ErrorMessageException(String.format(messageSource.getMessage("NotFound", null, null), "UserScore",
				"CreateDate", createDate.toString()), TypeError.NotFound);
	}

	private UserScore findById(Long id) {
		return this.userScoreRepository.findById(id).orElseThrow(() -> new ErrorMessageException(String.format(messageSource.getMessage("NotFound", null, null), "UserScore", "Id", String.valueOf(id)),
				TypeError.NotFound));
	}

	private UserScore findByCreateAndFirstId(LocalDateTime dateTime) {
		List<Long> ids = findByCreateDate(dateTime);
		return this.findById(ids.get(0));
	}

	private void deleteByCreateDate(LocalDateTime dateTime) {
		UserScore userScore = findByCreateAndFirstId(CREATE_DATE);
		userScore.setQuestion(null);
		userScore.setQuestionResponseUser(null);
		userScore.setQuestionSolution(null);
		// update
		this.userScoreRepository.save(userScore);
		// delete
		this.userScoreRepository.deleteById(userScore.getId());
	}

	// Save Discussion Include Answer
	private void saveDiscussionIncludeAnswer(Long questionId) {
		LessonCreateDTO lessonCreateDTO = new LessonCreateDTO();
		lessonCreateDTO.setId(lessonService.getByQuestion(questionId));
		discussionService.save(
				new DiscussionCreateDTO(null, null, true, null, CREATE_DATE, CREATE_DATE, null, lessonCreateDTO, null));

	}
}