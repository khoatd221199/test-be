package com.r2s.pte.mapper.impl;

import com.r2s.pte.dto.LessonTestedDTO;
import com.r2s.pte.entity.Lesson;
import com.r2s.pte.entity.LessonTested;
import com.r2s.pte.mapper.LessonTestedMapper;
import com.r2s.pte.service.LessonService;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LessonTestedMapperImpl implements LessonTestedMapper {
    @Autowired
    private LessonService lessonService;
    @Override
    public LessonTested mapToEntity(LessonTestedDTO dto)
    {
        if(dto == null)
            return null;
        LessonTested entity = new LessonTested();
        entity.setUserId(dto.getUserId());
        entity.setExamDate(dto.getExamDate());
        entity.setDescription(dto.getDescription());
        Lesson lesson= this.lessonService.findById(dto.getLessonId());
        entity.setLesson(lesson);
        entity.setUserId(dto.getUserId());
        return entity;
    }

    @Override
    public LessonTestedDTO mapToDTO(LessonTested entity) {
        if(entity == null)
            return null;
        LessonTestedDTO lessonTested = new LessonTestedDTO();
        lessonTested.setId(entity.getId());
        lessonTested.setExamDate(entity.getExamDate());
        lessonTested.setDescription(entity.getDescription());
        lessonTested.setLessonId(entity.getLesson().getId());
        lessonTested.setUserId(entity.getUserId());
        LocalDate createDate = entity.getCreatedDate().toLocalDate();
        lessonTested.setRecordTime(createDate);
        return lessonTested;
    }

}
