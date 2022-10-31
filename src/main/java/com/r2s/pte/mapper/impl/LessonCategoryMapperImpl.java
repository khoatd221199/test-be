package com.r2s.pte.mapper.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.r2s.pte.dto.LessonCategoryDTO;
import com.r2s.pte.entity.LessonCategory;
import com.r2s.pte.mapper.CategoryMapper;
import com.r2s.pte.mapper.LessonCategoryMapper;
import com.r2s.pte.mapper.LessonMapper;
@Component
public class LessonCategoryMapperImpl implements LessonCategoryMapper {

	@Autowired
	private CategoryMapper categoryMapper;
	@Autowired
	private LessonMapper lessonMapper;
	
	@Override
	public LessonCategory map(LessonCategoryDTO dto) {
		if(dto == null)
			return null;
		LessonCategory entity = new LessonCategory();
		entity.setId(dto.getId());

		return entity;
	}

	@Override
	public LessonCategoryDTO map(LessonCategory entity) {
		if(entity == null)
			return null;
		LessonCategoryDTO dto = new LessonCategoryDTO();
		dto.setId(entity.getId());
		dto.setCategory(categoryMapper.mapToDto(entity.getCategory()));
		dto.setLesson(lessonMapper.mapToDTO(entity.getLesson()));

		return dto;
	}

}
