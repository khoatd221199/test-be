package com.r2s.pte.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.r2s.pte.entity.BookLesson;
import com.r2s.pte.repository.BookLessonRepository;
import com.r2s.pte.service.BookLessonService;
@Service
public class BookLessonServiceImpl implements BookLessonService{

	@Autowired
	private BookLessonRepository  bookLessonRepository;
	
	@Override
	public BookLesson findById(Long id) {
		Optional<BookLesson> entity = this.bookLessonRepository.findById(id);
		if(entity.isPresent())
			return entity.get();
		else 
			return null;
	}

	@Override
	public List<BookLesson> getAll() {
		return this.bookLessonRepository.findAll();

	}


}
