package com.r2s.pte.service;

import java.util.List;

import com.r2s.pte.dto.CategoryDTO;
import com.r2s.pte.entity.Category;

public interface CategoryService {
	CategoryDTO findById(long id);
	List<CategoryDTO> getAll();
	List<CategoryDTO> getByParentId(Long id);
	void checkCategoryIsParent(Category category);
	void save(Category category);

    CategoryDTO getByLessonId(Long id);
	void checkCodeCategory(String code);
	Category findByCode(String code);
}
