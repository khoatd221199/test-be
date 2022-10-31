package com.r2s.pte.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryDTO {

	private long id;
	private String parentCode;
	private String code;
	private String name;
	private String description;
}
