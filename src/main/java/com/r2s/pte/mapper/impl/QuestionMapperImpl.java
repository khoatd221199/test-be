package com.r2s.pte.mapper.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.r2s.pte.dto.QuestionDTO;
import com.r2s.pte.dto.QuestionOptionDTO;
import com.r2s.pte.dto.QuestionSolutionDTO;
import com.r2s.pte.dto.QuestionViewDTO;
import com.r2s.pte.dto.UserContext;
import com.r2s.pte.entity.Question;
import com.r2s.pte.entity.QuestionOption;
import com.r2s.pte.entity.QuestionSolution;
import com.r2s.pte.mapper.QuestionMapper;
import com.r2s.pte.mapper.QuestionOptionMapper;
import com.r2s.pte.mapper.QuestionSolutionMapper;
import com.r2s.pte.mapper.QuestionTypeMapper;
import com.r2s.pte.service.QuestionSolutionService;
@Component
public class QuestionMapperImpl  implements QuestionMapper{
	@Autowired
	private QuestionOptionMapper questionOptionMapper;
	@Autowired
	private QuestionTypeMapper questionTypeMapper;
	@Autowired
	private QuestionSolutionMapper questionSolutionMapper;
	@Autowired
	private QuestionSolutionService questionSolutionService;
	
	@Override
	public QuestionDTO mapToDTO(Question question)
	{
		if(question == null) 
			return null;
		QuestionDTO questionDTO = new QuestionDTO();
		questionDTO.setId(question.getId());
		questionDTO.setName(question.getName());
		questionDTO.setCode(question.getCode());
		questionDTO.setOrder(question.getOrder());
		questionDTO.setExplanation(question.getExplanation());
		questionDTO.setQuestionType(question.getQuestionType());
		return questionDTO;
	}
	@Override
	public Question mapToEntity(QuestionDTO question)
	{
		if(question == null) 
			return null;
		Question questionDTO = new Question();
		questionDTO.setId(question.getId());
		questionDTO.setName(question.getName());
		questionDTO.setCode(question.getCode());
		if(question.getQuestionGroup() != null)
		questionDTO.setQuestionGroup(question.getQuestionGroup());
		questionDTO.setOrder(question.getOrder());
		if(question.getQuestionType() != null)
		questionDTO.setQuestionType(question.getQuestionType());
		questionDTO.setExplanation(question.getExplanation());
		return questionDTO;
	}
	@Override
	public QuestionViewDTO mapToViewDTO(Question question) {
		if(question == null) 
			return null;

		QuestionViewDTO questionViewDTO = new QuestionViewDTO();
		List<QuestionOptionDTO> questionOptionDTOs = new ArrayList<QuestionOptionDTO>();
		List<QuestionSolutionDTO> questionSolutionDTOs = new ArrayList<QuestionSolutionDTO>();
		questionViewDTO.setId(question.getId());
		questionViewDTO.setName(question.getName());
		if(question.getCode()!=null) {
			questionViewDTO.setCode(question.getCode());
		}else {
			questionViewDTO.setCode("");
		}
		questionViewDTO.setOrder(question.getOrder());
		questionViewDTO.setDescription(question.getDescription());
		questionViewDTO.setExplanation(question.getExplanation());
		for (QuestionOption questionOption : question.getQuestionOptions() ) {
			questionOptionDTOs.add(questionOptionMapper.mapToDTO(questionOption)); 
		}
		questionViewDTO.setOptions(questionOptionDTOs);
		for (QuestionSolution questionSolution : question.getQuestionSolutions() ) {
			questionSolutionDTOs.add(questionSolutionMapper.mapToDTO( questionSolution )); 
		}
		questionViewDTO.setSolutions(questionSolutionDTOs);
		questionViewDTO.setQuestionType(questionTypeMapper.mapToDTO(question.getQuestionType()));
		return questionViewDTO;
	}
	
	@Override
	public QuestionDTO mapHaveSolution(Question question)//Map for do Score
	{
		QuestionDTO questionDTO =	mapToDTO(question);
		List<QuestionSolutionDTO> questionSolutionDTOs = this.questionSolutionService.findByQuestion(questionDTO.getId());
		questionDTO.setSolutions(questionSolutionDTOs);
		return questionDTO;
	}
	@Override
	public void mapToUpdate(Question entity, QuestionDTO dto)
	{
		entity.setName(dto.getName());
		entity.setCode(dto.getCode());
		entity.setOrder(dto.getOrder());
		entity.setExplanation(dto.getExplanation());
		entity.setModifiedBy(UserContext.getId());
		entity.setModifiedDate(LocalDateTime.now());
	}
}
