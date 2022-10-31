package com.r2s.pte.mapper.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.r2s.pte.dto.QuestionDTO;
import com.r2s.pte.dto.QuestionResponseUserDTO;
import com.r2s.pte.entity.Question;
import com.r2s.pte.entity.QuestionResponseUser;
import com.r2s.pte.mapper.QuestionMapper;
import com.r2s.pte.mapper.QuestionResponseUserMapper;
import com.r2s.pte.service.QuestionService;

@Component
public class QuestionResponseUserMapperImpl implements QuestionResponseUserMapper {
	@Autowired
	private QuestionService questionService;
	@Autowired
	private QuestionMapper questionMapper;

	@Override
	public QuestionResponseUser mapToEntity(QuestionResponseUserDTO dto) {
		if (dto == null)
			return null;
		QuestionResponseUser entity = new QuestionResponseUser();
		if (dto.getId() != null)
			entity.setId(dto.getId());
		entity.setValueText(dto.getValueText());
		entity.setValueMedia(dto.getValueMedia());
		// set question
		QuestionDTO questionDTO = dto.getQuestionDTO();
		if (questionDTO != null) {
			setQuestion(questionDTO, entity);
		}
		return entity;
	}

	@Override
	public QuestionResponseUserDTO mapToDTO(QuestionResponseUser entity) {
		if (entity == null)
			return null;
		QuestionResponseUserDTO dto = new QuestionResponseUserDTO();
		dto.setValueMedia(entity.getValueMedia());
		dto.setValueText(entity.getValueText());
		dto.setId(entity.getId());
		Question question = entity.getQuestion();
		if (question != null)
			setQuestionDTO(question, dto);
		return dto;
	}

	private void setQuestion(QuestionDTO questionDTOdto, QuestionResponseUser questionResponseUser) {
		Long idQuestion = questionDTOdto.getId();
		Question question = this.questionService.findById(idQuestion);
		questionResponseUser.setQuestion(question);
	}

	private void setQuestionDTO(Question question, QuestionResponseUserDTO dto) {
		QuestionDTO questionDTO = this.questionMapper.mapToDTO(question);
		dto.setQuestionDTO(questionDTO);
	}

}
