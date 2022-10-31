package com.r2s.pte.mapper;

import org.mapstruct.Mapper;

import com.r2s.pte.dto.CategoryDTO;
import com.r2s.pte.entity.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

	CategoryDTO mapToDto(Category category);
	
	Category map(CategoryDTO categoryDTO);

}
