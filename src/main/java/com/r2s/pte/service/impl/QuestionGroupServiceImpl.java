package com.r2s.pte.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.r2s.pte.common.TypeError;
import com.r2s.pte.dto.QuestionGroupViewDTO;
import com.r2s.pte.dto.UserContext;
import com.r2s.pte.entity.Lesson;
import com.r2s.pte.entity.QuestionGroup;
import com.r2s.pte.exception.ErrorMessageException;
import com.r2s.pte.mapper.QuestionGroupMapper;
import com.r2s.pte.repository.QuestionGroupRepository;
import com.r2s.pte.service.QuestionGroupService;
import com.r2s.pte.service.QuestionService;

@Service
public class QuestionGroupServiceImpl implements QuestionGroupService {
	@Autowired
	private QuestionGroupRepository questionGroupRepository;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private QuestionGroupMapper questionGroupMapper;
	@Autowired
	private QuestionService questionService;


	@Override
	public QuestionGroup updateByLesson(long idLesson, QuestionGroup dto) {
		LocalDateTime localDateTime = LocalDateTime.now();
		QuestionGroup questionGroup = findByLessonId(idLesson);
		questionGroup.setModifiedDate(localDateTime);
		questionGroup.setModifiedBy(UserContext.getId());
		questionGroup.setDescription(dto.getDescription());
		questionGroup.setTitle(dto.getTitle());
		questionGroup.setIsEmbedded(dto.getIsEmbedded());
		questionGroup.setIsShuffle(dto.getIsShuffle());
		questionGroup.setMediaSegmentFrom(dto.getMediaSegmentFrom());
		return this.questionGroupRepository.save(questionGroup);
	}
	@Override
	public QuestionGroup save(Lesson lesson, QuestionGroup questionGroup)
	{
		LocalDateTime localDateTime = LocalDateTime.now();
		questionGroup.setCreatedBy(UserContext.getId());
		questionGroup.setModifiedBy(UserContext.getId());
		questionGroup.setCreatedDate(localDateTime);
		questionGroup.setModifiedDate(localDateTime);
		questionGroup.setLesson(lesson);
		return this.questionGroupRepository.save(questionGroup);
		
	}


	@Override
	public QuestionGroup findByLessonId(long id) {
		QuestionGroup questionGroup = this.questionGroupRepository.findByLessonId(id).orElseThrow(
				() -> new ErrorMessageException(String.format(messageSource.getMessage("NotFound", null, null),
						"Lesson", "Id", String.valueOf(id)), TypeError.NotFound));
		return questionGroup;
	}
	
	@Override
	public QuestionGroupViewDTO getById(long id) {
		QuestionGroupViewDTO questionGroupViewDTO = questionGroupMapper.mapToViewDTO(findById(id));
		return questionGroupViewDTO;
	}

	@Override
	public QuestionGroup findById(long id) {
		QuestionGroup entity = this.questionGroupRepository.findById(id).orElseThrow(() -> new ErrorMessageException(
				String.format(messageSource.getMessage("NotFound", null, null), "QuestionGroup", "Id", String.valueOf(id)),
				TypeError.NotFound));
		return entity;
	}
	@Override
	public void deleteByLessonId(Long lessonId)
	{
		//delete Question
		questionService.deleteByQuestionGroupId(lessonId);
		QuestionGroup questionGroup = findByLessonId(lessonId);
		questionGroup.setLesson(null);
		this.questionGroupRepository.save(questionGroup);
		this.questionGroupRepository.deleteById(questionGroup.getId());
	}

	
}
