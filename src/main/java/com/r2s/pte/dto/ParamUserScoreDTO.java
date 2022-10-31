package com.r2s.pte.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ParamUserScoreDTO {
	private List<Long> questionIds ;
	private Long userId;
	private String codeCategory;
}
