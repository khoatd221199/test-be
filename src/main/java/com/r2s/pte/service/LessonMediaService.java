package com.r2s.pte.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.r2s.pte.dto.LessonAudioCreateDTO;
import com.r2s.pte.dto.LessonMediaDTO;
import com.r2s.pte.entity.Lesson;

public interface LessonMediaService {
	LessonMediaDTO save(LessonMediaDTO lessonMediaDTO, Lesson lessonSaved);

	void save(List<LessonMediaDTO> lessonMediaDTOs, Lesson lessonSaved);

	void uploadFile(LessonAudioCreateDTO lessonAudioDTO, MultipartFile image, MultipartFile shadowing);

	void deleteByLesson(Long lessonId);

	void update(List<LessonMediaDTO> lessonMediaDTOs, Lesson lesson);

}
