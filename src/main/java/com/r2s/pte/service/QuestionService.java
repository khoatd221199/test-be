package com.r2s.pte.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.r2s.pte.dto.QuestionDTO;
import com.r2s.pte.dto.QuestionViewDTO;
import com.r2s.pte.entity.Question;
import com.r2s.pte.entity.QuestionGroup;
import com.r2s.pte.entity.QuestionType;

public interface QuestionService {

	void deleteByQuestionGroupId(Long id);

	long count();

	<S extends Question> Page<S> findAll(Example<S> example, Pageable pageable);

	boolean existsById(Long id);

	Question findById(Long id);

	QuestionViewDTO getById(Long id);
	
	Page<Question> findAll(Pageable pageable);

	List<Question> findAll();


	List<Question> findQuestionByQuestionGroupId(long id);

	List<Question> findQuestionByLessonId(long id);

	void saveFromLessonService(List<QuestionDTO> dto, QuestionGroup questionGroup,Boolean isEmbedded);

	void update(QuestionDTO dto, long id);

	void updateByQuestionGroup(Long idQuestionGroup, List<QuestionDTO> dto, QuestionGroup questionGroup, Boolean isEmbedded);

	Question create(QuestionDTO dto, QuestionGroup questionGroup, QuestionType questionType);

	void setQuestionSolutionInQuestionDTO(QuestionDTO questionDTO);

	Question update(QuestionDTO dto, QuestionGroup questionGroup, LocalDateTime createDate, Long createDateBy);

}
