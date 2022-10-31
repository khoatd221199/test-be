package com.r2s.pte.service;

import java.util.List;

import com.r2s.pte.dto.DiscussionReactionsCreateDTO;
import com.r2s.pte.dto.DiscussionReactionsDTO;
import com.r2s.pte.dto.DiscussionReactionsViewDTO;

public interface DiscussionReactionsService {

	DiscussionReactionsDTO findById(Long id);

	List<DiscussionReactionsViewDTO> findAllByDisscusionId(long id);
	
	boolean existsById(Long id);

	void save(DiscussionReactionsCreateDTO discussionLikeDTO);
		
	void update(DiscussionReactionsCreateDTO discussionLikeDTO, long id);
	
	void delete(long discussionId,Long userId);

	void deleteByDiscussionId(Long discussionId);

}
