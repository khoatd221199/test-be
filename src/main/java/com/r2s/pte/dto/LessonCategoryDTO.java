package com.r2s.pte.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LessonCategoryDTO {
	
	private long id;
	private LessonCreateDTO lesson;
	private CategoryDTO category;
}
