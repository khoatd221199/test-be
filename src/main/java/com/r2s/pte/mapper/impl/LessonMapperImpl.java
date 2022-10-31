package com.r2s.pte.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.r2s.pte.common.TypeError;
import com.r2s.pte.dto.CategoryDTO;
import com.r2s.pte.dto.LessonCreateDTO;
import com.r2s.pte.dto.LessonDetailViewDTO;
import com.r2s.pte.dto.LessonMediaDTO;
import com.r2s.pte.dto.LessonViewDTO;
import com.r2s.pte.dto.UserContext;
import com.r2s.pte.entity.Lesson;
import com.r2s.pte.entity.LessonCategory;
import com.r2s.pte.entity.LessonMedia;
import com.r2s.pte.entity.QuestionGroup;
import com.r2s.pte.exception.ErrorMessageException;
import com.r2s.pte.mapper.CategoryMapper;
import com.r2s.pte.mapper.LessonMapper;
import com.r2s.pte.mapper.LessonMediaMapper;
import com.r2s.pte.mapper.QuestionGroupMapper;

@Component
public class LessonMapperImpl implements LessonMapper {
	@Autowired
	private CategoryMapper categoryMapper;
	@Autowired
	private LessonMediaMapper lessonMediaMapper;
	@Autowired
	private QuestionGroupMapper questionGroupMapper;
	@Autowired
	private MessageSource messageSource;

	@Override
	public Lesson mapToEntity(LessonCreateDTO lessonCreateDTO) {
		if (lessonCreateDTO == null)
			return null;
		Lesson lesson = new Lesson();
		lesson.setId(lessonCreateDTO.getId());
		lesson.setContent(lessonCreateDTO.getContent());
		lesson.setDuration(lessonCreateDTO.getDuration());
		lesson.setInternalNotes(lessonCreateDTO.getInternalNotes());
		lesson.setLanguageId(lessonCreateDTO.getLanguageId());
		lesson.setDescription(lessonCreateDTO.getDescription());
		lesson.setTitle(lessonCreateDTO.getTitle());
		lesson.setPreparationTime(lessonCreateDTO.getPreparationTime());
		lesson.setShared(lessonCreateDTO.isShared());
		lesson.setExplanation(lessonCreateDTO.getExplanation());
		QuestionGroup questionGroup = lessonCreateDTO.getQuestionGroup();
		lesson.setQuestionGroup(questionGroup);
		lesson.setLessonSourceMediaLinkImage(lessonCreateDTO.getSourceMediaLinkImage());
		lesson.setLessonSourceMediaLinkShadow(lessonCreateDTO.getSourceMediaLinkShadow());
		lesson.setLessonSourceMediaLinkVideo(lessonCreateDTO.getSourceMediaLinkVideo());
		setUser(lesson, lessonCreateDTO);
		return lesson;
	}

	@Override
	public LessonCreateDTO mapToDTO(Lesson lessonDTO) {
		if (lessonDTO == null)
			return null;
		LessonCreateDTO lesson = new LessonCreateDTO();
		lesson.setId(lessonDTO.getId());
		lesson.setContent(lessonDTO.getContent());
		lesson.setDuration(lessonDTO.getDuration());
		lesson.setInternalNotes(lesson.getInternalNotes());
		lesson.setLanguageId(lessonDTO.getLanguageId());
		lesson.setDescription(lessonDTO.getDescription());
		lesson.setTitle(lessonDTO.getTitle());
		lesson.setPreparationTime(lessonDTO.getPreparationTime());
		lesson.setShared(lessonDTO.isShared());
		lesson.setExplanation(lessonDTO.getExplanation());
		lesson.setQuestionGroup(lessonDTO.getQuestionGroup());
		return lesson;
	}

	@Override
	public void mapToDtoUpdate(Lesson lesson, LessonCreateDTO lessonCreateDTO) {
		lesson.setContent(lessonCreateDTO.getContent());
		lesson.setDuration(lessonCreateDTO.getDuration());
		lesson.setInternalNotes(lessonCreateDTO.getInternalNotes());
		lesson.setLanguageId(lessonCreateDTO.getLanguageId());
		lesson.setDescription(lessonCreateDTO.getDescription());
		lesson.setTitle(lessonCreateDTO.getTitle());
		lesson.setPreparationTime(lessonCreateDTO.getPreparationTime());
		// modified if need admin
		lesson.setShared(lessonCreateDTO.isShared());
		lesson.setExplanation(lessonCreateDTO.getExplanation());
		lesson.setLessonSourceMediaLinkImage(lessonCreateDTO.getSourceMediaLinkImage());
		lesson.setLessonSourceMediaLinkShadow(lessonCreateDTO.getSourceMediaLinkShadow());
		lesson.setLessonSourceMediaLinkVideo(lessonCreateDTO.getSourceMediaLinkVideo());
		QuestionGroup questionGroup = lessonCreateDTO.getQuestionGroup();
		if (questionGroup == null)
			throw new ErrorMessageException(this.messageSource.getMessage("QuestionGroup.Required", null, null),
					TypeError.BadRequest);

	}

	public LessonViewDTO mapToViewDTO(Lesson lesson) {
		if (lesson == null)
			return null;
		LessonViewDTO lessonDTO = new LessonViewDTO();
		List<CategoryDTO> categoryDTOs = new ArrayList<>();
		lessonDTO.setId(lesson.getId());
		lessonDTO.setContent(lesson.getContent());
		lessonDTO.setDuration(lesson.getDuration());
		lessonDTO.setInternalNotes(lesson.getInternalNotes());
		lessonDTO.setLanguageId(lesson.getLanguageId());
		lessonDTO.setDescription(lesson.getDescription());
		lessonDTO.setTitle(lesson.getTitle());
		if(lesson.getZOrder()!=null) {
			lessonDTO.setZOrder(lesson.getZOrder());
		}
		lessonDTO.setPreparationTime(lesson.getPreparationTime());
		lessonDTO.setStatus(lesson.isStatus());
		lessonDTO.setShared(lesson.isShared());
		for (LessonCategory lessonCategory : lesson.getListLesson_Category()) {
			categoryDTOs.add(categoryMapper.mapToDto(lessonCategory.getCategory()));
		}
		lessonDTO.setCategories(categoryDTOs);
		lessonDTO.setExplanation(lesson.getExplanation());
		lessonDTO.setLessonSourceMediaLinkImage(lesson.getLessonSourceMediaLinkImage());
		lessonDTO.setLessonSourceMediaLinkShadow(lesson.getLessonSourceMediaLinkShadow());
		lessonDTO.setLessonSourceMediaLinkVideo(lesson.getLessonSourceMediaLinkVideo());
		lessonDTO.setCreatedBy(lesson.getCreatedBy());
		lessonDTO.setCreatedDate(lesson.getCreatedDate());
		lessonDTO.setModifiedBy(lesson.getModifiedBy());
		lessonDTO.setModifiedDate(lesson.getModifiedDate());
		return lessonDTO;
	}

	@Override
	public LessonDetailViewDTO mapToViewDetailDTO(Lesson lesson) {
		if (lesson == null)
			return null;
		LessonDetailViewDTO lessonDetailViewDTO = new LessonDetailViewDTO();
		List<CategoryDTO> categoryDTOs = new ArrayList<>();
		List<LessonMediaDTO> lessonMediaDTOs = new ArrayList<>();
		lessonDetailViewDTO.setId(lesson.getId());
		lessonDetailViewDTO.setContent(lesson.getContent());
		lessonDetailViewDTO.setDuration(lesson.getDuration());
		lessonDetailViewDTO.setInternalNotes(lesson.getInternalNotes());
		lessonDetailViewDTO.setLanguageId(lesson.getLanguageId());
		lessonDetailViewDTO.setDescription(lesson.getDescription());
		lessonDetailViewDTO.setTitle(lesson.getTitle());
		if(lesson.getZOrder()!=null) {
			lessonDetailViewDTO.setZOrder(lesson.getZOrder());
		}
		lessonDetailViewDTO.setPreparationTime(lesson.getPreparationTime());
		lessonDetailViewDTO.setStatus(lesson.isStatus());
		lessonDetailViewDTO.setShared(lesson.isShared());
		for (LessonCategory lessonCategory : lesson.getListLesson_Category()) {
			categoryDTOs.add(categoryMapper.mapToDto(lessonCategory.getCategory()));
		}
		lessonDetailViewDTO.setQuestionGroup(questionGroupMapper.mapToViewDTO(lesson.getQuestionGroup()));
		lessonDetailViewDTO.setCategories(categoryDTOs);
		lessonDetailViewDTO.setExplanation(lesson.getExplanation());
		lessonDetailViewDTO.setLessonSourceMediaLinkImage(lesson.getLessonSourceMediaLinkImage());
		lessonDetailViewDTO.setLessonSourceMediaLinkShadow(lesson.getLessonSourceMediaLinkShadow());
		lessonDetailViewDTO.setLessonSourceMediaLinkVideo(lesson.getLessonSourceMediaLinkVideo());
		for ( LessonMedia lessonMedia : lesson.getLessonMedias()) {
			lessonMediaDTOs.add(lessonMediaMapper.mapToDTO(lessonMedia));
		}
		lessonDetailViewDTO.setLessonMediaDTOs(lessonMediaDTOs);
		lessonDetailViewDTO.setCreatedBy(lesson.getCreatedBy());
		lessonDetailViewDTO.setCreatedDate(lesson.getCreatedDate());
		lessonDetailViewDTO.setModifiedBy(lesson.getModifiedBy());
		lessonDetailViewDTO.setModifiedDate(lesson.getModifiedDate());

		return lessonDetailViewDTO;
	}

	private void setUser(Lesson lesson, LessonCreateDTO lessonCreateDTO) {
		Long user = UserContext.getId();
		if (user != null && user.longValue() != 0) {
			lesson.setCreatedBy(user);
			lesson.setModifiedBy(user);
		} else {
			throw new ErrorMessageException(this.messageSource.getMessage("Forbidden", null, null),
					TypeError.Forbidden);
		}

	}

}
