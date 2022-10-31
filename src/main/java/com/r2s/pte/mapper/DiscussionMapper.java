package com.r2s.pte.mapper;

import org.mapstruct.Mapper;

import com.r2s.pte.dto.DiscussionCreateDTO;
import com.r2s.pte.dto.DiscussionDTO;
import com.r2s.pte.dto.DiscussionSubDTO;
import com.r2s.pte.dto.DiscussionViewDTO;
import com.r2s.pte.entity.Discussion;
@Mapper(componentModel = "spring")
public interface DiscussionMapper {
	Discussion map(DiscussionDTO discussionDTO);
	
	Discussion map(DiscussionCreateDTO discussionDTO);

	DiscussionDTO map(Discussion discussion);
	
	DiscussionViewDTO mapToViewDTO(Discussion discussion);
	
	DiscussionSubDTO mapToSubDTO(Discussion discussion);
	
}
