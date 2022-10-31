package com.r2s.pte.mapper.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.r2s.pte.dto.DiscussionCreateDTO;
import com.r2s.pte.dto.DiscussionDTO;
import com.r2s.pte.dto.DiscussionReactionsViewDTO;
import com.r2s.pte.dto.DiscussionSubDTO;
import com.r2s.pte.dto.DiscussionViewDTO;
import com.r2s.pte.entity.Discussion;
import com.r2s.pte.entity.DiscussionReactions;
import com.r2s.pte.mapper.DiscussionMapper;
import com.r2s.pte.mapper.DiscussionReactionsMapper;
import com.r2s.pte.mapper.LessonMapper;
import com.r2s.pte.repository.DiscussionRepository;
@Component
public class DiscussionMapperImpl implements DiscussionMapper{
	@Autowired
	private LessonMapper lessonMapper;
	@Autowired
	private DiscussionReactionsMapper discussionReactionsMapper ;
	@Autowired
	private DiscussionMapper discussionMapper ;
	@Autowired
	private DiscussionRepository discussionRepository  ;
	@Override
	public Discussion map(DiscussionDTO discussionDTO) {
		if(discussionDTO == null)
			return null;
		Discussion discussion = new Discussion();
		discussion.setId(discussionDTO.getId());
		discussion.setParentId(discussionDTO.getParentId());
		discussion.setLesson(lessonMapper.mapToEntity(discussionDTO.getLesson()));
		discussion.setDiscussionContent(discussionDTO.getDiscussionContent());
		discussion.setCreatedBy(discussionDTO.getCreatedBy());
		discussion.setCreatedDate(discussionDTO.getCreatedDate());
		discussion.setModifiedBy(discussionDTO.getModifiedBy());
		discussion.setModifiedDate(discussionDTO.getModifiedDate());
		return discussion;
	}
	
	@Override
	public Discussion map(DiscussionCreateDTO discussionDTO) {
		if(discussionDTO == null)
			return null;
		Discussion discussion = new Discussion();
		discussion.setId(discussionDTO.getId());
		discussion.setParentId(discussionDTO.getParentId());
		discussion.setLesson(lessonMapper.mapToEntity(discussionDTO.getLesson()));
		discussion.setDiscussionContent(discussionDTO.getDiscussionContent());
		discussion.setIsIncludeAnswer(discussionDTO.getIsIncludeAnswer());
		discussion.setCreatedDate(discussionDTO.getCreatedDate());
		discussion.setModifiedDate(discussionDTO.getModifiedDate());
		return discussion;
	}
	
	@Override
	public DiscussionDTO map(Discussion discussion) {
		if(discussion == null)
			return null;
		DiscussionDTO discussionDTO = new DiscussionDTO();
		discussionDTO.setId(discussion.getId());
		discussionDTO.setParentId(discussion.getParentId());
		discussionDTO.setLesson(lessonMapper.mapToDTO(discussion.getLesson()));	
		discussionDTO.setDiscussionContent(discussion.getDiscussionContent());
		discussionDTO.setIsIncludeAnswer(discussion.getIsIncludeAnswer());
		discussionDTO.setCreatedBy(discussion.getCreatedBy());
		discussionDTO.setCreatedDate(discussion.getCreatedDate());
		discussionDTO.setModifiedBy(discussion.getModifiedBy());
		discussionDTO.setModifiedDate(discussion.getModifiedDate());
		return discussionDTO;
	}

	@Override
	public DiscussionViewDTO mapToViewDTO(Discussion discussion) {
		if(discussion == null)
			return null;
		DiscussionViewDTO discussionViewDTO = new DiscussionViewDTO();
		discussionViewDTO.setId(discussion.getId());
		Set<DiscussionReactionsViewDTO> discussionReactionsDTOs = new HashSet<>();
		for (DiscussionReactions discussionReactions : discussion.getDiscussionReactions()) {
			discussionReactionsDTOs.add(discussionReactionsMapper.mapToView(discussionReactions));
		}
		Set<DiscussionSubDTO> discussionSubs = new HashSet<>();
		for (Discussion discussionSub :  discussionRepository.findAllByParentId(discussion.getId())) {
			discussionSubs.add(discussionMapper.mapToSubDTO(discussionSub));
		}
		discussionViewDTO.setDiscussionSubs(discussionSubs);
		discussionViewDTO.setDiscussionReactions(discussionReactionsDTOs);
		discussionViewDTO.setDiscussionContent(discussion.getDiscussionContent());
		discussionViewDTO.setCreatedBy(discussion.getCreatedBy());
		discussionViewDTO.setCreatedDate(discussion.getCreatedDate());
		discussionViewDTO.setModifiedBy(discussion.getModifiedBy());
		discussionViewDTO.setModifiedDate(discussion.getModifiedDate());
		return discussionViewDTO;
	}

	@Override
	public DiscussionSubDTO mapToSubDTO(Discussion discussion) {
		if(discussion == null)
			return null;
		DiscussionSubDTO discussionSubDTO = new DiscussionSubDTO();
		discussionSubDTO.setId(discussion.getId());
		Set<DiscussionReactionsViewDTO> discussionReactionsDTOs = new HashSet<>();
		for (DiscussionReactions discussionReactions : discussion.getDiscussionReactions()) {
			discussionReactionsDTOs.add(discussionReactionsMapper.mapToView(discussionReactions));
		}
		discussionSubDTO.setDiscussionReactions(discussionReactionsDTOs);
		discussionSubDTO.setDiscussionContent(discussion.getDiscussionContent());
		discussionSubDTO.setCreatedBy(discussion.getCreatedBy());
		discussionSubDTO.setCreatedDate(discussion.getCreatedDate());
		discussionSubDTO.setModifiedBy(discussion.getModifiedBy());
		discussionSubDTO.setModifiedDate(discussion.getModifiedDate());
		return discussionSubDTO;
	}

}
