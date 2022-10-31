package com.r2s.pte.mapper.impl;

import org.springframework.stereotype.Component;

import com.r2s.pte.dto.QuestionOptionDTO;
import com.r2s.pte.entity.QuestionOption;
import com.r2s.pte.mapper.QuestionOptionMapper;
@Component
public class QuestionOptionMapperImpl implements QuestionOptionMapper {
	@Override
	public QuestionOptionDTO mapToDTO(QuestionOption questionOption)
	{
		if (questionOption == null)
			return null;
		QuestionOptionDTO questionOptionDTO = new QuestionOptionDTO();
		questionOptionDTO.setId(questionOption.getId());
		questionOptionDTO.setName(questionOption.getName());
		questionOptionDTO.setDescription(questionOption.getDescription());
		questionOptionDTO.setOrder(questionOption.getOrder());
		questionOptionDTO.setCode(questionOption.getCode());
		return questionOptionDTO;
	}
	@Override
	public QuestionOption mapToEntity(QuestionOptionDTO dto)
	{
		if (dto == null)
			return null;
		QuestionOption entity = new QuestionOption();
		entity.setId(dto.getId());
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setOrder(dto.getOrder());
		entity.setCode(dto.getCode());
		return entity;
	}
}
