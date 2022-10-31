package com.r2s.pte.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.r2s.pte.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.r2s.pte.common.Logger;
import com.r2s.pte.common.TypeError;
import com.r2s.pte.dto.CategoryDTO;
import com.r2s.pte.dto.UserContext;
import com.r2s.pte.entity.Category;
import com.r2s.pte.exception.ErrorMessageException;
import com.r2s.pte.mapper.CategoryMapper;
import com.r2s.pte.repository.CategoryRepository;
import com.r2s.pte.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private CategoryMapper categoryMapper;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private LessonService lessonService;

	@Override
	public CategoryDTO findById(long id) {
		Category category = this.categoryRepository.findById(id).orElseThrow(() -> new ErrorMessageException(
				String.format(messageSource.getMessage("NotFound", null, null), "Category", "Id", String.valueOf(id)),
				TypeError.NotFound));
		return categoryMapper.mapToDto(category);
	}

	@Override
	public List<CategoryDTO> getAll() {
		List<CategoryDTO> categoryDTOs = categoryRepository.findAll().stream()
				.map(item -> this.categoryMapper.mapToDto(item)).collect(Collectors.toList());
		Logger.logInfo.info("Get all {}", "Category");
		return categoryDTOs;
	}

	@Override
	public void checkCategoryIsParent(Category category) {
		if (category.getParentCode() == null)
			throw new ErrorMessageException(
					String.format(messageSource.getMessage("ChooseAnyCategory", null, null), category.getName()),
					TypeError.BadRequest);
	}

	@Override
	public List<CategoryDTO> getByParentId(Long id) {
		String parentCode = findById(id).getCode();
		List<CategoryDTO> categoryDTOs = categoryRepository.findCategoryByParentCode(parentCode).stream()
				.map(item -> this.categoryMapper.mapToDto(item)).collect(Collectors.toList());
		if (categoryDTOs.size() == 0) {
			throw new ErrorMessageException(String.format(messageSource.getMessage("NotFound", null, null), "Category",
					" parent Id", String.valueOf(id)), TypeError.NotFound);
		}
		Logger.logInfo.info("Get category parentId {}", "Category");
		return categoryDTOs;
	}

	@Override
	public void save(Category category) {
		category.setCreatedBy(UserContext.getId());
		category.setCreatedDate(LocalDateTime.now());
		category.setModifiedBy(UserContext.getId());
		category.setModifiedDate(LocalDateTime.now());
		this.categoryRepository.save(category);
	}

	@Override
	public CategoryDTO getByLessonId(Long id) {
		boolean existsLesson = this.lessonService.existsById(id);
		if (!existsLesson)
			throw new ErrorMessageException(
					String.format(messageSource.getMessage("NotFound", null, null), "Lesson", "id", String.valueOf(id)),
					TypeError.NotFound);
		Category category = this.categoryRepository.getByLesson(id).orElseThrow(() -> new ErrorMessageException(
				String.format(messageSource.getMessage("NotFound", null, null), "Lesson", "id", String.valueOf(id)),
				TypeError.NotFound));

		return this.categoryMapper.mapToDto(category);
	}

	@Override
	public void checkCodeCategory(String code) {
		boolean isExists = this.categoryRepository.existsByCode(code);
		if (!isExists)
			throw new ErrorMessageException(
					String.format(messageSource.getMessage("NotFound", null, null), "Category", " code", code),
					TypeError.NotFound);
	}
	@Override
	public Category findByCode(String code)
	{
		return this.categoryRepository.findByCode(code).orElseThrow(()-> new ErrorMessageException(
				String.format(messageSource.getMessage("NotFound", null, null), "Category", " code", code),
				TypeError.NotFound));

	}
}
