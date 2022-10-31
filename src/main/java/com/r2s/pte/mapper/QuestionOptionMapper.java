package com.r2s.pte.mapper;

import org.mapstruct.Mapper;

import com.r2s.pte.dto.QuestionOptionDTO;
import com.r2s.pte.entity.QuestionOption;

@Mapper(componentModel = "spring")
public interface QuestionOptionMapper {

	QuestionOption mapToEntity(QuestionOptionDTO questionOptionDTO);

	QuestionOptionDTO mapToDTO(QuestionOption questionOption);

}
