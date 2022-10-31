package com.r2s.pte.service;

import java.time.LocalDateTime;
import java.util.List;

import com.r2s.pte.dto.QuestionOptionDTO;
import com.r2s.pte.entity.Question;

public interface QuestionOptionService {

	void save(QuestionOptionDTO optionDto, Question questionSaved);

	void update(QuestionOptionDTO optionDto, Question questionSaved, LocalDateTime createDate, Long createDateBy);

	void update(List<QuestionOptionDTO> optionDtos, Question questionSaved, LocalDateTime createDate, Long createDateBy);

	void deleteByQuestionId(Long questionId);

}
