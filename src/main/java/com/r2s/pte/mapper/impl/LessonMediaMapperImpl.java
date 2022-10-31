package com.r2s.pte.mapper.impl;

import org.springframework.stereotype.Component;

import com.r2s.pte.dto.LessonMediaDTO;
import com.r2s.pte.entity.LessonMedia;
import com.r2s.pte.mapper.LessonMediaMapper;

@Component
public class LessonMediaMapperImpl implements LessonMediaMapper {
	@Override
	public LessonMedia mapToEntity(LessonMediaDTO dto)
	{
		LessonMedia lessonMedia = new LessonMedia();
		lessonMedia.setVoiceName(dto.getVoiceName());
		lessonMedia.setLessonMediaLink(dto.getLessonMediaLink());
		lessonMedia.setType(dto.getType());
		lessonMedia.setOrder(dto.getOrder());
		return lessonMedia;
	}
	@Override
	public LessonMediaDTO mapToDTO(LessonMedia entity)
	{
		LessonMediaDTO dto = new LessonMediaDTO();
		dto.setId(entity.getId());
		dto.setVoiceName(entity.getVoiceName());
		dto.setLessonMediaLink(entity.getLessonMediaLink());
		dto.setType(entity.getType());
		dto.setOrder(entity.getOrder());
		return dto;
	}
}
