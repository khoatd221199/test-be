package com.r2s.pte.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.r2s.pte.dto.QuestionOptionDTO;
import com.r2s.pte.dto.UserContext;
import com.r2s.pte.entity.Question;
import com.r2s.pte.entity.QuestionOption;
import com.r2s.pte.mapper.QuestionOptionMapper;
import com.r2s.pte.repository.QuestionOptionRepository;
import com.r2s.pte.service.QuestionOptionService;

@Service
public class QuestionOptionServiceImpl implements QuestionOptionService {
	@Autowired
	private QuestionOptionRepository questionOptionRepository;
	@Autowired
	private QuestionOptionMapper questionOptionMapper;

	@Override
	public void save(QuestionOptionDTO optionDto, Question questionSaved) {
		QuestionOption questionOption = this.questionOptionMapper.mapToEntity(optionDto);
		questionOption.setQuestion(questionSaved);
		questionOption.setCreatedDate(LocalDateTime.now());
		questionOption.setCreatedBy(UserContext.getId());
		questionOption.setModifiedDate(LocalDateTime.now());
		questionOption.setModifiedBy(UserContext.getId());
		this.questionOptionRepository.save(questionOption);
	}

	@Override
	public void update(QuestionOptionDTO optionDto, Question questionSaved, LocalDateTime createDate,
			Long createDateBy) {
		QuestionOption questionOption = this.questionOptionMapper.mapToEntity(optionDto);
		questionOption.setQuestion(questionSaved);
		questionOption.setCreatedDate(createDate);
		questionOption.setCreatedBy(createDateBy);
		questionOption.setModifiedDate(LocalDateTime.now());
		questionOption.setModifiedBy(UserContext.getId());
		this.questionOptionRepository.save(questionOption);
	}

	@Override
	public void update(List<QuestionOptionDTO> optionDtos, Question questionSaved, LocalDateTime createDate,
			Long createDateBy) {
		List<QuestionOption> questionOptions = this.questionOptionRepository
				.findQuestionOptionByQuestionId(questionSaved.getId());
		if (optionDtos.size() == questionOptions.size()) {
			for (int i = 0; i < questionOptions.size(); i++) {
				QuestionOptionDTO questionOptionDTO = optionDtos.get(i);
				QuestionOption questionOption = questionOptions.get(i);
				questionOptionDTO.setId(questionOption.getId());
				update(questionOptionDTO, questionSaved, createDate, createDateBy);
			}
		} else {
			// set null question
			questionOptions.forEach(item -> {
				item.setQuestion(null);
				this.questionOptionRepository.deleteById(item.getId());
			});
			// save new Option
			for (QuestionOptionDTO questionOptionDTO : optionDtos) {
				update(questionOptionDTO, questionSaved, createDate, createDateBy);
			}

		}
	}
	@Override
	public void deleteByQuestionId(Long questionId)
	{
		List<QuestionOption> questionOptions = this.questionOptionRepository.findQuestionOptionByQuestionId(questionId);
		questionOptions.forEach(option ->{
			option.setQuestion(null);
			this.questionOptionRepository.save(option);
			this.questionOptionRepository.deleteById(option.getId());
		});
	}

}
