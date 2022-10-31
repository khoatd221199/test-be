package com.r2s.pte.service.strategy.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.r2s.pte.dto.LessonCreateDTO;
import com.r2s.pte.entity.Category;
import com.r2s.pte.entity.Lesson;
import com.r2s.pte.mapper.LessonMapper;
import com.r2s.pte.repository.LessonRepository;
import com.r2s.pte.service.LessonService;
import com.r2s.pte.service.strategy.LessonStrategy;
@Component("ReadAloudStrategy")
public class ReadAloudStrategyImpl implements LessonStrategy{
	@Autowired
	private LessonService lessonService;
	@Autowired
	private LessonMapper lessonMapper;
	@Autowired
	private LessonRepository lessonRepository;
	@Override
	public void update(long id, LessonCreateDTO dto , Category category)
	{
		Lesson lesson = lessonService.findById(id);
		this.lessonMapper.mapToDtoUpdate(lesson, dto);
		lesson.setModifiedDate(LocalDateTime.now());
		// Update lesson
		this.lessonRepository.save(lesson);
	}
}
