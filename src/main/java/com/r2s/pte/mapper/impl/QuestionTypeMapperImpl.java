package com.r2s.pte.mapper.impl;

import org.springframework.stereotype.Component;

import com.r2s.pte.dto.QuestionTypeDTO;
import com.r2s.pte.entity.QuestionType;
import com.r2s.pte.mapper.QuestionTypeMapper;
@Component
public class QuestionTypeMapperImpl implements QuestionTypeMapper{

	@Override
	public QuestionType mapToEntity(QuestionTypeDTO dto) {
		
		if(dto == null) 
			return null;
		QuestionType entity = new QuestionType();
		entity.setId(dto.getId());
		entity.setName(dto.getName());
		entity.setCode(dto.getCode());
		entity.setDescription(dto.getDescription());
		entity.setCreatedBy(dto.getCreatedBy());
		entity.setCreatedDate(dto.getCreatedDate());
		entity.setModifiedBy(dto.getModifiedBy());
		entity.setModifiedDate(dto.getModifiedDate());
		return entity;
	}

	@Override
	public QuestionTypeDTO mapToDTO(QuestionType entity) {
		if(entity == null) 
			return null;
		QuestionTypeDTO dto = new QuestionTypeDTO();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setCode(entity.getCode());
		dto.setDescription(entity.getDescription());
		dto.setCreatedBy(entity.getCreatedBy());
		dto.setCreatedDate(entity.getCreatedDate());
		dto.setModifiedBy(entity.getModifiedBy());
		dto.setModifiedDate(entity.getModifiedDate());
		return dto;
	}

}
