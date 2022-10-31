package com.r2s.pte.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestLessonDTO {
	private Long userId;
	private Integer categoryId;
	private Integer sort;
	private Integer  mark;
	private Boolean practiceStatus;
	private Boolean weekly;
	private Integer month;
	private Boolean shadowing;
	private Boolean shared;
	private Boolean explanation;
	private Boolean status;
	private String searchKeyWord;
	private Integer pageNumber;
	private Integer limit ;
}
