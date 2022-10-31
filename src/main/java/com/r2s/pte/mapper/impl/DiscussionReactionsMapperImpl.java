package com.r2s.pte.mapper.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.r2s.pte.dto.DiscussionReactionsViewDTO;
import com.r2s.pte.dto.DiscussionReactionsCreateDTO;
import com.r2s.pte.dto.DiscussionReactionsDTO;
import com.r2s.pte.entity.DiscussionReactions;
import com.r2s.pte.mapper.DiscussionMapper;
import com.r2s.pte.mapper.DiscussionReactionsMapper;

@Component
public class DiscussionReactionsMapperImpl implements DiscussionReactionsMapper{

	@Autowired
	private DiscussionMapper discussionMapper;
	
	@Override
	public DiscussionReactions map(DiscussionReactionsDTO discussionReactionsDTO ) {
		if(discussionReactionsDTO == null)
			return null;
		DiscussionReactions discussionReactions = new DiscussionReactions();
		discussionReactions.setId(discussionReactionsDTO.getId());
		discussionReactions.setReactions(discussionReactionsDTO.getReactions());
		discussionReactions.setCreatedBy(discussionReactionsDTO.getCreatedBy());
		discussionReactions.setCreatedDate(discussionReactionsDTO.getCreatedDate());
		discussionReactions.setModifiedBy(discussionReactionsDTO.getModifiedBy());
		discussionReactions.setModifiedDate(discussionReactionsDTO.getModifiedDate());
		discussionReactions.setDiscussion(discussionMapper.map(discussionReactionsDTO.getDiscussion()));
		return discussionReactions;
	}
	
	@Override
	public DiscussionReactions map(DiscussionReactionsCreateDTO discussionReactionsCreateDTO) {
		if(discussionReactionsCreateDTO == null)
			return null;
		DiscussionReactions discussionReactions = new DiscussionReactions();
		discussionReactions.setId(discussionReactionsCreateDTO.getId());
		discussionReactions.setReactions(discussionReactionsCreateDTO.getReactions());
		discussionReactions.setCreatedDate(discussionReactionsCreateDTO.getCreatedDate());
		discussionReactions.setModifiedDate(discussionReactionsCreateDTO.getModifiedDate());
		discussionReactions.setDiscussion(discussionMapper.map(discussionReactionsCreateDTO.getDiscussion()));
		return discussionReactions;
	}

	@Override
	public DiscussionReactionsDTO map(DiscussionReactions discussionReactions) {
		if(discussionReactions == null)
			return null;
		DiscussionReactionsDTO discussionReactionsDTO = new DiscussionReactionsDTO();
		discussionReactionsDTO.setId(discussionReactions.getId());
		discussionReactionsDTO.setReactions(discussionReactions.getReactions());
		discussionReactionsDTO.setCreatedBy(discussionReactions.getCreatedBy());
		discussionReactionsDTO.setCreatedDate(discussionReactions.getCreatedDate());
		discussionReactionsDTO.setModifiedBy(discussionReactions.getModifiedBy());
		discussionReactionsDTO.setModifiedDate(discussionReactions.getModifiedDate());
		discussionReactionsDTO.setDiscussion(discussionMapper.map(discussionReactions.getDiscussion()));
		return discussionReactionsDTO;
	}

	@Override
	public DiscussionReactionsViewDTO mapToView(DiscussionReactions discussionReactions) {
		if(discussionReactions == null)
			return null;
		DiscussionReactionsViewDTO discussionRactionsViewDTO = new DiscussionReactionsViewDTO();
		discussionRactionsViewDTO.setId(discussionReactions.getId());
		discussionRactionsViewDTO.setReactions(discussionReactions.getReactions());
		discussionRactionsViewDTO.setCreatedBy(discussionReactions.getCreatedBy());
		discussionRactionsViewDTO.setCreatedDate(discussionReactions.getCreatedDate());
		discussionRactionsViewDTO.setModifiedBy(discussionReactions.getModifiedBy());
		discussionRactionsViewDTO.setModifiedDate(discussionReactions.getModifiedDate());
		return discussionRactionsViewDTO;
	}

	


}
