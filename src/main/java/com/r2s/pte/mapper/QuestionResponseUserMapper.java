package com.r2s.pte.mapper;

import com.r2s.pte.dto.QuestionResponseUserDTO;
import com.r2s.pte.entity.QuestionResponseUser;

public interface QuestionResponseUserMapper {
    QuestionResponseUser mapToEntity(QuestionResponseUserDTO dto);

    QuestionResponseUserDTO mapToDTO(QuestionResponseUser entity);
}
