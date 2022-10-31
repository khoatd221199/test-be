package com.r2s.pte.service;

import com.r2s.pte.dto.QuestionGroupViewDTO;
import com.r2s.pte.entity.Lesson;
import com.r2s.pte.entity.QuestionGroup;

public interface QuestionGroupService {

	QuestionGroup updateByLesson(long idLesson, QuestionGroup dto);

//	QuestionGroupDTO getById(long id);

	QuestionGroup findByLessonId(long id);

	QuestionGroup findById(long id);
	
	QuestionGroupViewDTO getById(long id);

	void deleteByLessonId(Long lessonId);

	QuestionGroup save(Lesson lesson, QuestionGroup questionGroup);


}
