package com.r2s.pte.mapper;

import org.mapstruct.Mapper;

import com.r2s.pte.dto.LessonCategoryDTO;
import com.r2s.pte.entity.LessonCategory;

@Mapper(componentModel = "spring")
public interface LessonCategoryMapper {
	LessonCategory map(LessonCategoryDTO categoryDTO);
	LessonCategoryDTO map(LessonCategory category);
}
