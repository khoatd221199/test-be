package com.r2s.pte.mapper;

import org.mapstruct.Mapper;

import com.r2s.pte.dto.QuestionGroupViewDTO;
import com.r2s.pte.entity.QuestionGroup;

@Mapper(componentModel = "spring")
public interface QuestionGroupMapper {
	QuestionGroup mapToEntity(QuestionGroupViewDTO questionGroupViewDTO);

	QuestionGroupViewDTO mapToViewDTO(QuestionGroup questionGroup);
}
