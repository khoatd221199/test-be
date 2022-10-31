package com.r2s.pte.mapper;

import com.r2s.pte.dto.LessonTestedDTO;
import com.r2s.pte.entity.LessonTested;

public interface LessonTestedMapper {
    LessonTested mapToEntity(LessonTestedDTO dto);
    LessonTestedDTO mapToDTO(LessonTested entity);
}
