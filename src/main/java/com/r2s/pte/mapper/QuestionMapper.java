package com.r2s.pte.mapper;

import org.mapstruct.Mapper;

import com.r2s.pte.dto.QuestionDTO;
import com.r2s.pte.dto.QuestionViewDTO;
import com.r2s.pte.entity.Question;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

	QuestionDTO mapToDTO(Question question);
	
	QuestionViewDTO mapToViewDTO(Question question);

	Question mapToEntity(QuestionDTO questionDTO);

	QuestionDTO mapHaveSolution(Question question);

	void mapToUpdate(Question entity, QuestionDTO dto);

}
