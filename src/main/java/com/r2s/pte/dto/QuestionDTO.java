package com.r2s.pte.dto;

import java.util.ArrayList;
import java.util.List;

import com.r2s.pte.entity.QuestionGroup;
import com.r2s.pte.entity.QuestionType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class QuestionDTO {
	private long id;
	private String code;
	private String name;
	private String description;
	private QuestionGroup questionGroup;
	private QuestionType questionType;
	private Integer order;
	private String explanation;
	private List<QuestionOptionDTO> options = new ArrayList<QuestionOptionDTO>();
	private List<QuestionSolutionDTO> solutions = new ArrayList<QuestionSolutionDTO>();
}
