package com.r2s.pte.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class QuestionResponseUserDTO {
	private Long id;
	private String valueText;
	private String valueMedia;
	private QuestionDTO questionDTO;
	private Long userId;

}
