package com.r2s.pte.service;

import java.util.List;

import com.r2s.pte.dto.QuestionTypeDTO;
import com.r2s.pte.entity.QuestionType;

public interface QuestionTypeService {

	QuestionType findById(Long id);

	List<QuestionTypeDTO> findAll();

}
