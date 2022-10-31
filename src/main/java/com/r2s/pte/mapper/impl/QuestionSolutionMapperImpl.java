package com.r2s.pte.mapper.impl;

import org.springframework.stereotype.Component;

import com.r2s.pte.dto.QuestionSolutionDTO;
import com.r2s.pte.entity.QuestionSolution;
import com.r2s.pte.mapper.QuestionSolutionMapper;
@Component
public class QuestionSolutionMapperImpl implements QuestionSolutionMapper {
	@Override
	public QuestionSolutionDTO mapToDTO(QuestionSolution entity)
	{
		if(entity == null)
			return null;
		QuestionSolutionDTO dto = new QuestionSolutionDTO();
		dto.setId(entity.getId());
		dto.setValueMedia(entity.getValueMedia());
		dto.setValueText(entity.getValueText());
		dto.setExplanation(entity.getExplanation());
		return dto;
	}
	
	@Override
	public QuestionSolution mapToEntity(QuestionSolutionDTO dto)
	{
		if(dto == null)
			return null;
		QuestionSolution entity = new QuestionSolution();
		entity.setId(dto.getId());
		entity.setValueMedia(dto.getValueMedia());
		entity.setValueText(dto.getValueText());
		entity.setExplanation(dto.getExplanation());
		return entity;
	}
}
