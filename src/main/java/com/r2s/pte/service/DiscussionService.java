package com.r2s.pte.service;

import com.r2s.pte.dto.DiscussionCreateDTO;
import com.r2s.pte.dto.DiscussionDTO;
import com.r2s.pte.dto.PaginationDTO;

public interface DiscussionService {

	PaginationDTO findAllDiscussionByLessonId(long lessonId,int page,int limit);
	
	PaginationDTO findAllDiscussionIncludeAnswerByLessonId(long lessonId,Long userId,int page,int limit);
	
	DiscussionDTO findById(long id);
	
	boolean existsById(Long id);

	void save(DiscussionCreateDTO discussionDTO);
		
	void update(DiscussionCreateDTO discussionDTO, long id);
	
	void delete(long id);

	void deleteByLesson(Long lessonId);
}
