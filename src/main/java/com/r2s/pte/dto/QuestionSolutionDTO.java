package com.r2s.pte.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class QuestionSolutionDTO {
	private long id;
	private String valueText;
	private String valueMedia;
	private String explanation;
}
