package com.r2s.pte.mapper;

import org.mapstruct.Mapper;

import com.r2s.pte.dto.LessonViewDTO;
import com.r2s.pte.dto.LessonCreateDTO;
import com.r2s.pte.dto.LessonDetailViewDTO;
import com.r2s.pte.entity.Lesson;

@Mapper(componentModel = "spring")
public interface LessonMapper {

	Lesson mapToEntity(LessonCreateDTO lessonCreateDTO);
	
	LessonViewDTO mapToViewDTO(Lesson lesson);
	
	LessonDetailViewDTO mapToViewDetailDTO(Lesson lessonDTO);
	
	LessonCreateDTO mapToDTO(Lesson lessonDTO);

	void mapToDtoUpdate(Lesson lesson, LessonCreateDTO lessonCreateDTO);

}
