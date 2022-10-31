package com.r2s.pte.service;

import java.util.List;

import com.r2s.pte.entity.BookLesson;

public interface BookLessonService {

	BookLesson findById(Long id);

	List<BookLesson> getAll();

}
