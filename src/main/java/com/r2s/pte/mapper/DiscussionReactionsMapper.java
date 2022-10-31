package com.r2s.pte.mapper;

import org.mapstruct.Mapper;

import com.r2s.pte.dto.DiscussionReactionsCreateDTO;
import com.r2s.pte.dto.DiscussionReactionsDTO;
import com.r2s.pte.dto.DiscussionReactionsViewDTO;
import com.r2s.pte.entity.DiscussionReactions;

@Mapper(componentModel = "spring")
public interface DiscussionReactionsMapper {
	DiscussionReactions map(DiscussionReactionsDTO discussionReactionsDTO);
	
	DiscussionReactions map(DiscussionReactionsCreateDTO discussionReactionsCreateDTO);

	DiscussionReactionsDTO map(DiscussionReactions discussionReactions);
	
	DiscussionReactionsViewDTO mapToView(DiscussionReactions discussionReactions);

}
