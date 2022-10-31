package com.r2s.pte.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryViewDTO {
	private String name;
	private List<CategoryCustomDTO> items = new ArrayList<CategoryCustomDTO>();
}	
