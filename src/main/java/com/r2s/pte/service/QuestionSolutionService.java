package com.r2s.pte.service;

import java.time.LocalDateTime;
import java.util.List;

import com.r2s.pte.dto.QuestionSolutionDTO;
import com.r2s.pte.entity.Question;
import com.r2s.pte.entity.QuestionSolution;

public interface QuestionSolutionService {

	void save(QuestionSolutionDTO questionSolutionDTO, Question questionSaved);

	void update(QuestionSolutionDTO questionSolutionDTO, Question questionSaved, LocalDateTime creatDateTime, Long createDateBy);

	QuestionSolution findById(Long id);

	List<QuestionSolutionDTO> findByQuestion(Long id);

	List<QuestionSolutionDTO> findByQuestionDTO(Long id);

	void update(List<QuestionSolutionDTO> questionSolutionDTOs, Question questionSaved, LocalDateTime creatDateTime, Long createDateBy);

	int countByLesson(Long id);

	void deleteByQuestion(Long questionId);
}
