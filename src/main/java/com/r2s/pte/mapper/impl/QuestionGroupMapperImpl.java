package com.r2s.pte.mapper.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.r2s.pte.dto.QuestionGroupViewDTO;
import com.r2s.pte.dto.QuestionViewDTO;
import com.r2s.pte.entity.Question;
import com.r2s.pte.entity.QuestionGroup;
import com.r2s.pte.mapper.QuestionGroupMapper;
import com.r2s.pte.mapper.QuestionMapper;


@Component
public class QuestionGroupMapperImpl implements QuestionGroupMapper{
	@Autowired
	private QuestionMapper questionMapper;
	@Override
	public QuestionGroup mapToEntity(QuestionGroupViewDTO questionDto) {
		return null;
	}

	@Override
	public QuestionGroupViewDTO mapToViewDTO(QuestionGroup entity) {
		if(entity == null)
			return null;
		QuestionGroupViewDTO dto = new QuestionGroupViewDTO();
		List<QuestionViewDTO> questionDTOs = new ArrayList<>() ;
		dto.setId(entity.getId());
		dto.setTitle(entity.getTitle());
		dto.setDescription(entity.getDescription());
		dto.setMediaSegmentFrom(entity.getMediaSegmentFrom());
		dto.setIsEmbedded(entity.getIsEmbedded());
		dto.setIsShuffle(entity.getIsShuffle());
		for (Question question : entity.getQuestions()) {
			questionDTOs.add(questionMapper.mapToViewDTO(question));
		}			
		questionDTOs.sort(new Comparator<QuestionViewDTO>() {
			@Override
			public int compare(QuestionViewDTO o1, QuestionViewDTO o2) {
				return o1.getCode().equals(o2.getCode()) ? -1 : 1;
			}
        });
		dto.setQuestions(questionDTOs);
		dto.setCreatedBy(entity.getCreatedBy());
		dto.setCreatedDate(entity.getCreatedDate());
		dto.setModifiedBy(entity.getModifiedBy());
		dto.setModifiedDate(entity.getModifiedDate());
	
		return dto;
	}

}
