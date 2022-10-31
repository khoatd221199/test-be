package com.r2s.pte.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class QuestionViewDTO {
	private long id;
	private String code;
	private String name;
	private String description;
	private QuestionTypeDTO questionType;
	private Integer order;
	private String explanation;
	private List<QuestionOptionDTO> options = new ArrayList<QuestionOptionDTO>();
	private List<QuestionSolutionDTO> solutions = new ArrayList<QuestionSolutionDTO>();

}
