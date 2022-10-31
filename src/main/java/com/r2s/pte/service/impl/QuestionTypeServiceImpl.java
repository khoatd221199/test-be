package com.r2s.pte.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.r2s.pte.common.TypeError;
import com.r2s.pte.dto.QuestionTypeDTO;
import com.r2s.pte.entity.QuestionType;
import com.r2s.pte.exception.ErrorMessageException;
import com.r2s.pte.mapper.QuestionTypeMapper;
import com.r2s.pte.repository.QuestionTypeRepository;
import com.r2s.pte.service.QuestionTypeService;

@Service
public class QuestionTypeServiceImpl implements QuestionTypeService {
	@Autowired
	private QuestionTypeRepository questionTypeRepository;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private QuestionTypeMapper questionTypeMapper;
	private final String LINEAR_SCALE = "LINEAR_SCALE";
	private final String MULTIPLE_CHOICE_GRID = "MULTIPLE_CHOICE_GRID";
	private final String DATE = "DATE";
	private final String TIME = "TIME";

	@Override
	public QuestionType findById(Long id) {
		if(id == null)
			id = Long.valueOf(0);
		long questinTypeId = id;
		return this.questionTypeRepository.findById(id).orElseThrow(
				() -> new ErrorMessageException(String.format(messageSource.getMessage("NotFound", null, null),
						"Questin Type", "Id", String.valueOf(questinTypeId)), TypeError.NotFound));
	}

	@Override
	public List<QuestionTypeDTO> findAll() {
		List<QuestionType> questionTypes = this.questionTypeRepository.findAll();
		List<QuestionTypeDTO> questionTypeDTOs = new ArrayList<QuestionTypeDTO>();
		questionTypes.forEach(type -> {
			String code = type.getCode();
			if (!code.equals(LINEAR_SCALE) && !code.equals(MULTIPLE_CHOICE_GRID) && !code.equals(DATE)
					&& !code.equals(TIME)) {
				QuestionTypeDTO questionTypeDTO = this.questionTypeMapper.mapToDTO(type);
				questionTypeDTOs.add(questionTypeDTO);
			}
		});
		return questionTypeDTOs;
	}
}
