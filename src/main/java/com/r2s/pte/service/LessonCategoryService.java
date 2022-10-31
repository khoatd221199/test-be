package com.r2s.pte.service;

import java.util.List;

import com.r2s.pte.dto.CategoryDTO;
import com.r2s.pte.dto.LessonCategoryDTO;
import com.r2s.pte.entity.Category;
import com.r2s.pte.entity.Lesson;
import com.r2s.pte.entity.LessonCategory;

public interface LessonCategoryService {

	long count();

	boolean existsById(Long id);

	List<LessonCategoryDTO> findAll();

	<S extends LessonCategory> S save(S entity);

	LessonCategory saveLessonCategory(Lesson lesson, Category category);

	List<LessonCategory> findByLessonId(long id);

	void update(CategoryDTO dto, Lesson lesson);

	void updateListLessonCategory(List<CategoryDTO> dto, Lesson lesson);

	void deleteByLessonId(Long lessonId);

	void deleteByLessonIdAndCodeCategory(Long lessonId, String codeCategory);

	boolean lessonIsSingleCategory(Long lessonId);

}
