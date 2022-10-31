package com.r2s.pte.mapper;

import com.r2s.pte.dto.LessonMediaDTO;
import com.r2s.pte.entity.LessonMedia;

public interface LessonMediaMapper {

	LessonMedia mapToEntity(LessonMediaDTO dto);

	LessonMediaDTO mapToDTO(LessonMedia entity);

}
