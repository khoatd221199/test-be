package com.r2s.pte.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.r2s.pte.common.TypeError;
import com.r2s.pte.dto.CategoryDTO;
import com.r2s.pte.dto.LessonCategoryDTO;
import com.r2s.pte.dto.UserContext;
import com.r2s.pte.entity.Category;
import com.r2s.pte.entity.Lesson;
import com.r2s.pte.entity.LessonCategory;
import com.r2s.pte.exception.ErrorMessageException;
import com.r2s.pte.mapper.CategoryMapper;
import com.r2s.pte.mapper.LessonCategoryMapper;
import com.r2s.pte.repository.LessonCategoryRepository;
import com.r2s.pte.service.CategoryService;
import com.r2s.pte.service.LessonCategoryService;

@Service
public class LessonCategoryServiceImpl implements LessonCategoryService {
	@Autowired
	private LessonCategoryRepository lessonCategoryRepository;

	@Autowired
	private LessonCategoryMapper lessonCategoryMapper;
	@Autowired
	private CategoryMapper categoryMapper;
	@Autowired
	private CategoryService categoryService;

	@Override
	public <S extends LessonCategory> S save(S entity) {
		return lessonCategoryRepository.save(entity);
	}

	@Override
	public List<LessonCategoryDTO> findAll() {
		return lessonCategoryRepository.findAll().stream().map(item -> this.lessonCategoryMapper.map(item))
				.collect(Collectors.toList());

	}

	@Override
	public boolean existsById(Long id) {
		return lessonCategoryRepository.existsById(id);
	}

	@Override
	public long count() {
		return lessonCategoryRepository.count();
	}

	@Override
	public LessonCategory saveLessonCategory(Lesson lesson, Category category) {
		LessonCategory lessonCategory = new LessonCategory();
		lessonCategory.setCategory(category);
		lessonCategory.setLesson(lesson);
		// set time
		lessonCategory.setModifiedDate(LocalDateTime.now());
		lessonCategory.setCreatedDate(LocalDateTime.now());
		lessonCategory.setCreatedBy(UserContext.getId());
		lessonCategory.setModifiedBy(UserContext.getId());
		LessonCategory lessonCategorySaved = lessonCategoryRepository.save(lessonCategory);
		return lessonCategorySaved;
	}

	@Override
	public List<LessonCategory> findByLessonId(long id) {
		return this.lessonCategoryRepository.findByLessonId(id);

	}

	@Override
	public void update(CategoryDTO dto, Lesson lesson) {
		long idLesson = lesson.getId();
		LessonCategory lessonCategory = findByLessonId(idLesson).get(0);
		Category category = this.categoryMapper.map(categoryService.findById(dto.getId()));
		lessonCategory.setCategory(category);
		lessonCategory.setLesson(lesson);
		lessonCategory.setModifiedDate(LocalDateTime.now());
		lessonCategory.setModifiedBy(UserContext.getId());
		save(lessonCategory);
	}

	@Override
	public void updateListLessonCategory(List<CategoryDTO> dto, Lesson lesson) {
		long idLesson = lesson.getId();
		List<LessonCategory> lessonCategories = findByLessonId(idLesson);

		LocalDateTime createDate = lessonCategories.get(0).getCreatedDate();
		Long createDateBy = lessonCategories.get(0).getCreatedBy();
		// check is to change
		boolean isChange = checkChange(dto, lessonCategories);
		if (!isChange) {
			this.lessonCategoryRepository.deleteByLessonId(idLesson);

			for (CategoryDTO categoryDTO : dto) {
				Category category = this.categoryMapper.map(categoryService.findById(categoryDTO.getId()));
				LessonCategory lessonCategory = new LessonCategory();
				lessonCategory.setCategory(category);
				lessonCategory.setLesson(lesson);
				// set time
				lessonCategory.setCreatedDate(createDate);
				lessonCategory.setCreatedBy(createDateBy);
				lessonCategory.setModifiedDate(LocalDateTime.now());
				lessonCategory.setModifiedBy(UserContext.getId());
				lessonCategoryRepository.save(lessonCategory);
			}
		}
	}

	@Override
	public void deleteByLessonId(Long lessonId) {
		List<LessonCategory> lessonCategories = this.lessonCategoryRepository.findByLessonId(lessonId);
		if (lessonCategories != null && lessonCategories.size() > 0) {
			lessonCategories.forEach(item -> {
				item.setCategory(null);
				item.setLesson(null);
				this.lessonCategoryRepository.save(item);
				this.lessonCategoryRepository.deleteById(item.getId());
			});
		}
	}
	
	@Override
	public boolean lessonIsSingleCategory(Long lessonId)
	{
		int count = this.lessonCategoryRepository.countByLessonId(lessonId);
		if(count == 1)
			return true;
		return false;
	}

	@Override
	public void deleteByLessonIdAndCodeCategory(Long lessonId, String codeCategory)
	{
		categoryService.checkCodeCategory(codeCategory);
		LessonCategory lessonCategory = findByLessonAndCode(lessonId, codeCategory);
		lessonCategory.setCategory(null);
		lessonCategory.setLesson(null);
		this.lessonCategoryRepository.save(lessonCategory);
		this.lessonCategoryRepository.deleteById(lessonCategory.getId());
	}

	private LessonCategory findByLessonAndCode(Long lessonId, String code) {
		return this.lessonCategoryRepository.findByLessonIdAndCodeCategory(lessonId, code)
				.orElseThrow(() -> new ErrorMessageException("Item not found", TypeError.NotFound));
	}

	private boolean checkChange(List<CategoryDTO> dto, List<LessonCategory> lessonCategories) {
		boolean flag = true;
		int sizeDto = dto.size();
		int sizeLessonCategory = lessonCategories.size();
		if (sizeDto == sizeLessonCategory) {
			for (int i = 0; i < sizeLessonCategory; i++) {
				if (dto.get(i).getId() != lessonCategories.get(i).getCategory().getId())
					flag = false;
			}
		} else
			flag = false;
		return flag;

	}
}
