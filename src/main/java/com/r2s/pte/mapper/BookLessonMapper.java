package com.r2s.pte.mapper;

import org.mapstruct.Mapper;

import com.r2s.pte.dto.BookLessonDTO;
import com.r2s.pte.entity.BookLesson;
@Mapper(componentModel = "spring")
public interface BookLessonMapper {
	BookLesson map(BookLessonDTO bookLessonDTO);
	BookLessonDTO map(BookLesson bookLesson);
}
