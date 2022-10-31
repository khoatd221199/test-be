package com.r2s.pte.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestVocabUserDTO {
	private Long userId;
	private String vocab;
	private Long category;
	private Integer pageNumber;
	private Integer limit ;
}
