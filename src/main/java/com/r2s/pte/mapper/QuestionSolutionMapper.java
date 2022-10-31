package com.r2s.pte.mapper;

import org.mapstruct.Mapper;

import com.r2s.pte.dto.QuestionSolutionDTO;
import com.r2s.pte.entity.QuestionSolution;

@Mapper(componentModel = "spring")
public interface QuestionSolutionMapper {

	QuestionSolution mapToEntity(QuestionSolutionDTO questionSolutionDTO);

	QuestionSolutionDTO mapToDTO(QuestionSolution questionSolution);

}
