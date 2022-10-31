package com.r2s.pte.mapper;

import org.mapstruct.Mapper;

import com.r2s.pte.dto.QuestionTypeDTO;
import com.r2s.pte.entity.QuestionType;

@Mapper(componentModel = "spring")
public interface QuestionTypeMapper {
	QuestionType mapToEntity(QuestionTypeDTO questionTypeDTO);

	QuestionTypeDTO mapToDTO(QuestionType questionType);
}
