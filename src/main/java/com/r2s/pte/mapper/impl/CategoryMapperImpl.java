package com.r2s.pte.mapper.impl;

import org.springframework.stereotype.Component;

import com.r2s.pte.dto.CategoryDTO;
import com.r2s.pte.entity.Category;
import com.r2s.pte.mapper.CategoryMapper;
@Component
public class CategoryMapperImpl implements CategoryMapper {
	
	@Override
	public CategoryDTO mapToDto(Category category)
	{
		if(category == null)
			return null;
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setId(category.getId());
		categoryDTO.setName(category.getName());
		categoryDTO.setCode(category.getCode());
		categoryDTO.setParentCode(category.getParentCode());
		categoryDTO.setDescription(category.getDescription());
		return categoryDTO;
	}

	@Override
	public Category map(CategoryDTO categoryDTO) {
		if(categoryDTO == null)
			return null;
		Category category = new Category();
		category.setId(categoryDTO.getId());
		category.setName(categoryDTO.getName());
		category.setCode(categoryDTO.getCode());
		category.setParentCode(categoryDTO.getParentCode());
		category.setDescription(categoryDTO.getDescription());
		return category;
	}
}
