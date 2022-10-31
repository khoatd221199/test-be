package com.r2s.pte.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.r2s.pte.common.TypeError;
import com.r2s.pte.dto.QuestionDTO;
import com.r2s.pte.dto.QuestionOptionDTO;
import com.r2s.pte.dto.QuestionSolutionDTO;
import com.r2s.pte.dto.QuestionViewDTO;
import com.r2s.pte.dto.UserContext;
import com.r2s.pte.entity.Category;
import com.r2s.pte.entity.Question;
import com.r2s.pte.entity.QuestionGroup;
import com.r2s.pte.entity.QuestionType;
import com.r2s.pte.exception.ErrorMessageException;
import com.r2s.pte.mapper.QuestionMapper;
import com.r2s.pte.repository.CategoryRepository;
import com.r2s.pte.repository.QuestionRepository;
import com.r2s.pte.service.QuestionOptionService;
import com.r2s.pte.service.QuestionResponseUserService;
import com.r2s.pte.service.QuestionService;
import com.r2s.pte.service.QuestionSolutionService;
import com.r2s.pte.service.QuestionTypeService;
import com.r2s.pte.service.UserScoreService;
import com.r2s.pte.util.HandleGeneral;

@Service
@Transactional
public class QuestionServiceImpl implements QuestionService {
	@Autowired
	private QuestionRepository questionRepository;
	@Autowired
	private QuestionMapper questionMapper;
	@Autowired
	private QuestionSolutionService questionSolutionService;
	@Autowired
	private QuestionOptionService questionOptionService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private UserScoreService userScoreService;
	@Autowired
	private QuestionTypeService questionTypeService;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private HandleGeneral handleGeneral;
	@Autowired
	private QuestionResponseUserService questionResponseUserService;

	@Override
	public List<Question> findQuestionByLessonId(long id) {
		return questionRepository.findQuestionByLessonId(id);
	}

	@Override
	public List<Question> findQuestionByQuestionGroupId(long id) {
		return questionRepository.findQuestionByQuestionGroupId(id);
	}

	@Override
	public void saveFromLessonService(List<QuestionDTO> dtos, QuestionGroup questionGroup, Boolean isEmbedded) {
		if (!isEmbedded.booleanValue() && dtos == null) {
			Question question = new Question();
			question.setQuestionGroup(questionGroup);
			QuestionType questionType = getQuestionTypeByQuestionGroup(questionGroup);
			question.setQuestionType(questionType);
			question.setCreatedDate(LocalDateTime.now());
			question.setCreatedBy(UserContext.getId());
			question.setModifiedDate(LocalDateTime.now());
			question.setModifiedBy(UserContext.getId());
			this.questionRepository.save(question);

		} else {
			if (dtos != null && dtos.size() > 0) {
				for (QuestionDTO questionDTO : dtos) {
					QuestionType questionType = this.questionTypeService
							.findById(questionDTO.getQuestionType().getId());
					Question questionSaved = create(questionDTO, questionGroup, questionType);
					List<QuestionOptionDTO> optionDTOs = questionDTO.getOptions();
					if (optionDTOs != null && optionDTOs.size() > 0) {
						optionDTOs.forEach(option -> {
							this.questionOptionService.save(option, questionSaved);
						});
					}
					List<QuestionSolutionDTO> solutionDTOs = questionDTO.getSolutions();
					if (solutionDTOs != null && solutionDTOs.size() > 0) {
						solutionDTOs.forEach(solution -> {
							this.questionSolutionService.save(solution, questionSaved);
						});
					}
				}
			}
		}

	}

	private QuestionType getQuestionTypeByQuestionGroup(QuestionGroup questionGroup) {
		Long lessonId = questionGroup.getLesson().getId();
		Category category = this.categoryRepository.getByLesson(lessonId).orElseThrow(() -> new ErrorMessageException(
				String.format(messageSource.getMessage("NotFound", null, null), "Category", "Id", lessonId.toString()),
				TypeError.NotFound));

		return this.handleGeneral.setQuestionTypeMatchCategrory(category);
	}

	@Override
	public List<Question> findAll() {
		return questionRepository.findAll();
	}

	@Override
	public Page<Question> findAll(Pageable pageable) {
		return questionRepository.findAll(pageable);
	}

	@Override
	public Question findById(Long id) {
		Question question = questionRepository.findById(id).orElseThrow(() -> new ErrorMessageException(
				String.format(messageSource.getMessage("NotFound", null, null), "Question", "Id", String.valueOf(id)),
				TypeError.NotFound));
		return question;
	}

	@Override
	public boolean existsById(Long id) {
		return questionRepository.existsById(id);
	}

	@Override
	public <S extends Question> Page<S> findAll(Example<S> example, Pageable pageable) {
		return questionRepository.findAll(example, pageable);
	}

	@Override
	public long count() {
		return questionRepository.count();
	}

	@Override
	public void deleteByQuestionGroupId(Long id) {
		List<Question> questions = this.questionRepository.findQuestionByQuestionGroupId(id);
		if (questions != null && questions.size() > 0) {
			for (Question question : questions) {
				// delete user score
				userScoreService.deleteByQuestionId(question.getId());
				//delete Question Response
				questionResponseUserService.deleteByQuestionId(question.getId());
				question.setQuestionType(null);
				question.setQuestionGroup(null);
				this.questionRepository.save(question);
				this.questionRepository.deleteById(question.getId());
			}
		}
	}

	// SAVE QUESTION IN LessonCategoryService
	@Override
	public Question create(QuestionDTO dto, QuestionGroup questionGroup, QuestionType questionType) {
		Question question = this.questionMapper.mapToEntity(dto);
		question.setQuestionGroup(questionGroup);
		question.setQuestionType(questionType);
		question.setCreatedDate(LocalDateTime.now());
		question.setCreatedBy(UserContext.getId());
		question.setModifiedDate(LocalDateTime.now());
		question.setModifiedBy(UserContext.getId());
		return this.questionRepository.save(question);
	}

	// update in updade lesson service
	@Override
	public Question update(QuestionDTO dto, QuestionGroup questionGroup,
			LocalDateTime createDate, Long createDateBy) {
		Question question = this.questionMapper.mapToEntity(dto);
		QuestionType questionType = this.questionTypeService.findById(dto.getQuestionType().getId());
		question.setQuestionGroup(questionGroup);
		question.setQuestionType(questionType);
		question.setCreatedDate(createDate);
		question.setCreatedBy(createDateBy);
		question.setModifiedDate(LocalDateTime.now());
		question.setModifiedBy(UserContext.getId());
		Question questionSaved = this.questionRepository.save(question);
		return questionSaved;
	}

	private void updateOptionAndSolution(QuestionDTO dto, Question question, LocalDateTime createDate,
			Long createDateBy) {
		// update question option
		this.questionOptionService.update(dto.getOptions(), question, createDate, createDateBy);
		// update question solution
		this.questionSolutionService.update(dto.getSolutions(), question, createDate, createDateBy);
	}
	@Override
	public void updateByQuestionGroup(Long idQuestionGroup, List<QuestionDTO> dtos, QuestionGroup questionGroup, Boolean isEmbedded) 
	{
		List<Question> questions = this.questionRepository.findQuestionByQuestionGroupId(idQuestionGroup);
		LocalDateTime createDate = questions.get(0).getCreatedDate();
		Long createDateBy = questions.get(0).getCreatedBy();
		// No pattern
		if (!isEmbedded.booleanValue()) {
			questions.forEach(item -> {
				item.setModifiedBy(UserContext.getId());
				item.setModifiedDate(LocalDateTime.now());
				this.questionRepository.save(item);
			});
			return;
		}
		int sizeQuestion = questions.size();
		int sizeDTO = dtos.size();
		if (sizeQuestion == sizeDTO) {
			updateCaseSizeListEqual(questions, dtos, questionGroup, createDate, createDateBy);
		} else {
			Long questionGroupId = questionGroup.getId();
			this.deleteByQuestionGroupId(questionGroupId);// Delete question by question group
			// Save List Question
			saveListQuestion(dtos, questionGroup, createDate, createDateBy);
		}

	}
	public void updateList(Long idQuestionGroup, List<QuestionDTO> dtos, QuestionGroup questionGroup, Boolean isEmbedded) 
	{
		List<Question> questions = this.questionRepository.findQuestionByQuestionGroupId(idQuestionGroup);
		LocalDateTime createDate = questions.get(0).getCreatedDate();
		Long createDateBy = questions.get(0).getCreatedBy();
		// No pattern
		if (!isEmbedded.booleanValue()) {
			questions.forEach(item -> {
				item.setModifiedBy(UserContext.getId());
				item.setModifiedDate(LocalDateTime.now());
				this.questionRepository.save(item);
			});
			return;
		}
		int sizeQuestion = questions.size();
		int sizeDTO = dtos.size();
		if (sizeQuestion == sizeDTO) {
			updateCaseSizeListEqual(questions, dtos, questionGroup, createDate, createDateBy);
		} else {
			if(sizeDTO > sizeQuestion)// add question
			{
				for (int i = 0; i < sizeQuestion; i++) {
					update(dtos.get(i), questions.get(i).getId());
				}
			}
			Long questionGroupId = questionGroup.getId();
			this.deleteByQuestionGroupId(questionGroupId);// Delete question by question group
			// Save List Question
			saveListQuestion(dtos, questionGroup, createDate, createDateBy);
		}

	}
	private void saveListQuestion(List<QuestionDTO> dtos, QuestionGroup questionGroup,
			LocalDateTime createDate, Long createDateBy) {
		dtos.forEach(item -> {
			Question questionSaved = update(item, questionGroup, createDate, createDateBy);
			// get List Question Option DTO from Question DTO
			List<QuestionOptionDTO> optionDTOs = item.getOptions();
			// Save options
			optionDTOs.forEach(optionDto -> {
				this.questionOptionService.update(optionDto, questionSaved, createDate, createDateBy);
			});
			// Save Solution
			List<QuestionSolutionDTO> solutionDTOs = item.getSolutions();
			for (QuestionSolutionDTO solutionDTO : solutionDTOs) {
				this.questionSolutionService.update(solutionDTO, questionSaved, createDate, createDateBy);
			}
		});
	}

	private void updateCaseSizeListEqual(List<Question> questions, List<QuestionDTO> dtos, QuestionGroup questionGroup, LocalDateTime createDate, Long createDateBy) {
		// Set Id for update
		for (int i = 0; i < questions.size(); i++) {
			Question question = questions.get(i);
			dtos.get(i).setId(question.getId());
		}
		// update question, solution, option
		dtos.forEach(item -> {
			Question questionSaved = update(item, questionGroup, createDate, createDateBy);
			updateOptionAndSolution(item, questionSaved, createDate, createDateBy);
		});
	}

	@Override
	public void update(QuestionDTO dto, long id) {
		Question question = this.questionRepository.findById(id).orElseThrow(() -> new ErrorMessageException(
				String.format(messageSource.getMessage("NotFound", null, null), "Question", "Id", String.valueOf(id)),
				TypeError.NotFound));
		//set question Type
		QuestionType questionType = this.questionTypeService.findById(dto.getQuestionType().getId());
		dto.setQuestionType(questionType);
		this.questionMapper.mapToUpdate(question, dto);
		question.setQuestionType(questionType);
		this.questionRepository.save(question);
	}

	@Override
	public QuestionViewDTO getById(Long id) {
		QuestionViewDTO questionViewDTO = questionMapper.mapToViewDTO(findById(id));
		return questionViewDTO;

	}

	@Override
	public void setQuestionSolutionInQuestionDTO(QuestionDTO questionDTO) {
		Long idQuestion = questionDTO.getId();
		List<QuestionSolutionDTO> questionSolutionDTOs = this.questionSolutionService.findByQuestion(idQuestion);
		questionDTO.setSolutions(questionSolutionDTOs);
	}

}
