package com.r2s.pte.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.r2s.pte.common.TypeError;
import com.r2s.pte.dto.QuestionSolutionDTO;
import com.r2s.pte.entity.Question;
import com.r2s.pte.entity.QuestionSolution;
import com.r2s.pte.entity.UserScore;
import com.r2s.pte.exception.ErrorMessageException;
import com.r2s.pte.mapper.QuestionSolutionMapper;
import com.r2s.pte.repository.QuestionSolutionRepository;
import com.r2s.pte.repository.UserScoreRepository;
import com.r2s.pte.service.QuestionSolutionService;

@Service
public class QuestionSolutionServiceImpl implements QuestionSolutionService {
	@Autowired
	private QuestionSolutionRepository questionSolutionRepository;
	@Autowired
	private QuestionSolutionMapper questionSolutionMapper;
	@Autowired
	private UserScoreRepository userScoreRepository;
	@Autowired
	private MessageSource messageSource;

	@Override
	public void save(QuestionSolutionDTO questionSolutionDTO, Question questionSaved) {
		QuestionSolution questionSolution = this.questionSolutionMapper.mapToEntity(questionSolutionDTO);
		questionSolution.setQuestion(questionSaved);
		questionSolution.setCreatedDate(LocalDateTime.now());
		questionSolution.setCreatedBy(questionSaved.getCreatedBy());
		questionSolution.setModifiedDate(LocalDateTime.now());
		questionSolution.setModifiedBy(questionSaved.getModifiedBy());
		this.questionSolutionRepository.save(questionSolution);
	}

	@Override
	public void update(QuestionSolutionDTO questionSolutionDTO, Question questionSaved, LocalDateTime creatDateTime,
			Long createDateBy) {
		QuestionSolution questionSolution = this.questionSolutionMapper.mapToEntity(questionSolutionDTO);
		questionSolution.setQuestion(questionSaved);
		questionSolution.setCreatedDate(creatDateTime);
		questionSolution.setCreatedBy(createDateBy);
		questionSolution.setModifiedDate(LocalDateTime.now());
		questionSolution.setModifiedBy(1);
		this.questionSolutionRepository.save(questionSolution);
	}

	@Override
	public void update(List<QuestionSolutionDTO> questionSolutionDTOs, Question questionSaved,
			LocalDateTime creatDateTime, Long createDateBy) {
		List<QuestionSolution> questionSolutions = this.questionSolutionRepository
				.getQuestionSolutionByQuestionId(questionSaved.getId());
		if (questionSolutions.size() == questionSolutionDTOs.size()) {
			for (int i = 0; i < questionSolutions.size(); i++) {
				QuestionSolution questionSolution = questionSolutions.get(i);
				QuestionSolutionDTO questionSolutionDTO = questionSolutionDTOs.get(i);
				questionSolutionDTO.setId(questionSolution.getId());
				update(questionSolutionDTO, questionSaved, creatDateTime, createDateBy);
			}
		} else {
			List<UserScore> userScores = this.userScoreRepository.getByQuestionId(questionSaved.getId());
			if (userScores == null || userScores.size() == 0) {
				// set null question
				questionSolutions.forEach(item -> {
					item.setQuestion(null);
					this.questionSolutionRepository.deleteById(item.getId());
				});
				// save new Option
				for (QuestionSolutionDTO questionOptionDTO : questionSolutionDTOs) {
					update(questionOptionDTO, questionSaved, creatDateTime, createDateBy);
				}
			}

		}
	}

	@Override
	public QuestionSolution findById(Long id) {
		QuestionSolution questionSolution = this.questionSolutionRepository.findById(id).orElseThrow(
				() -> new ErrorMessageException(String.format(this.messageSource.getMessage("NotFound", null, null),
						"Solution", "id", String.valueOf(id)), TypeError.NotFound));
		return questionSolution;
	}

	@Override
	public List<QuestionSolutionDTO> findByQuestion(Long id) {
		List<QuestionSolution> questionSolutions = this.questionSolutionRepository.getQuestionSolutionByQuestionId(id);
		List<QuestionSolutionDTO> dtos = new ArrayList<QuestionSolutionDTO>();
		for (QuestionSolution questionSolution : questionSolutions) {
			QuestionSolutionDTO dto = this.questionSolutionMapper.mapToDTO(questionSolution);
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public List<QuestionSolutionDTO> findByQuestionDTO(Long id) {
		List<QuestionSolution> questionSolutions = this.questionSolutionRepository.getQuestionSolutionByQuestionId(id);
		List<QuestionSolutionDTO> dtos = new ArrayList<QuestionSolutionDTO>();
		for (QuestionSolution questionSolution : questionSolutions) {
			QuestionSolutionDTO dto = this.questionSolutionMapper.mapToDTO(questionSolution);
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public int countByLesson(Long id) {
		return this.questionSolutionRepository.countQuestionSoltutionByLessonId(id);
	}
	@Override
	public void deleteByQuestion(Long questionId)
	{
		List<QuestionSolution> questionSolutions = questionSolutionRepository.getQuestionSolutionByQuestionId(questionId);
		questionSolutions.forEach(solution ->{
			solution.setQuestion(null);
			this.questionSolutionRepository.save(solution);
			this.questionSolutionRepository.deleteById(solution.getId());
		});
	}

}
