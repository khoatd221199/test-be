package com.r2s.pte.service;

import java.util.List;

import com.r2s.pte.dto.LessonTestedDTO;

public interface LessonTestedService {

    LessonTestedDTO save(LessonTestedDTO dto);

	List<LessonTestedDTO> getTestedByLessonId(Long id);

	Long countByLesson(Long id);

	List<LessonTestedDTO> getLessonAndUser(Long idLesson, Long idUser);

	void deleteById(Long id);

	LessonTestedDTO findById(Long id);

	boolean existsById(Long id);

	void deleteByLessonId(Long lessonId);
}
