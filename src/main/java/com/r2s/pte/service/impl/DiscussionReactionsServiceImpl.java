package com.r2s.pte.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.r2s.pte.common.Logger;
import com.r2s.pte.common.TypeError;
import com.r2s.pte.dto.DiscussionDTO;
import com.r2s.pte.dto.DiscussionReactionsCreateDTO;
import com.r2s.pte.dto.DiscussionReactionsDTO;
import com.r2s.pte.dto.DiscussionReactionsViewDTO;
import com.r2s.pte.dto.UserContext;
import com.r2s.pte.entity.DiscussionReactions;
import com.r2s.pte.exception.ErrorMessageException;
import com.r2s.pte.mapper.DiscussionReactionsMapper;
import com.r2s.pte.repository.DiscussionReactionsRepository;
import com.r2s.pte.service.DiscussionReactionsService;
import com.r2s.pte.service.DiscussionService;

@Transactional
@Service
public class DiscussionReactionsServiceImpl implements DiscussionReactionsService {

	@Autowired
	private DiscussionReactionsRepository discussionRactionsRepository;
	@Autowired
	private DiscussionReactionsMapper discussionReactionsMapper;
	@Autowired
	private DiscussionService discussionService;
	@Autowired
	private MessageSource messageSource;

	@Override
	public DiscussionReactionsDTO findById(Long id) {
		Optional<DiscussionReactions> entity = this.discussionRactionsRepository.findById(id);
		if (entity.isPresent()) {
			Logger.logInfo.info("Get discussion_reaction by id {}", "DiscussionReactions");
			return discussionReactionsMapper.map(entity.get());
		} else
			return null;
	}

	@Override
	public List<DiscussionReactionsViewDTO> findAllByDisscusionId(long id) {
		List<DiscussionReactionsViewDTO> discussionReactionsViewDTOs = discussionRactionsRepository
				.findAllByDiscussionId(id).stream().map(item -> this.discussionReactionsMapper.mapToView(item))
				.collect(Collectors.toList());
		if (discussionReactionsViewDTOs.size() < 1) {
			throw new ErrorMessageException(String.format(messageSource.getMessage("NotFound", null, null),
					"DiscussionReactions", " Request", String.valueOf("")), TypeError.NotFound);
		}
		return discussionReactionsViewDTOs;
	}

	@Override
	public boolean existsById(Long id) {
		return discussionRactionsRepository.existsById(id);

	}

	@Override
	public void save(DiscussionReactionsCreateDTO discussionReactionsCreateDTO) {
		if( discussionRactionsRepository.findByDiscussionIdAndCreatedBy(discussionReactionsCreateDTO.getDiscussion().getId(),UserContext.getId())==null) {
			DiscussionReactions discussionReactions = new DiscussionReactions();
			try {
				DiscussionDTO discussionDTO = discussionService.findById(discussionReactionsCreateDTO.getDiscussion().getId());
				discussionReactionsCreateDTO.setDiscussion(discussionDTO);
				discussionReactions = discussionReactionsMapper.map(discussionReactionsCreateDTO);
				discussionReactions.setCreatedDate(LocalDateTime.now());
				discussionReactions.setCreatedBy(UserContext.getId());
				discussionReactions.setModifiedBy(UserContext.getId());
				discussionReactions.setModifiedDate(LocalDateTime.now());
			} catch (Exception e) {
				e.printStackTrace();
			}
			this.discussionRactionsRepository.save(discussionReactions);
			Logger.logInfo.info("Add discussion_reaction successful");
		}
		else {
			throw new ErrorMessageException(String.format(messageSource.getMessage("AlreadyExists", null, null), String.valueOf("Reaction of discussion : "+discussionReactionsCreateDTO.getDiscussion().getId())),TypeError.AlreadyExists);
		}

	}

	@Override
	public void update(DiscussionReactionsCreateDTO discussionReactionsUpdateDTO, long id) {
		DiscussionReactionsDTO discussionReactionsDTO = findById(id);
		if (UserContext.getId() != discussionReactionsDTO.getCreatedBy()) {
			throw new ErrorMessageException(String.format(messageSource.getMessage("Forbidden", null, null)),
					TypeError.Forbidden);
		}
		if (discussionReactionsUpdateDTO.getReactions() != null) {
			discussionReactionsDTO.setReactions(discussionReactionsUpdateDTO.getReactions());
		}
		discussionReactionsDTO.setModifiedBy(Long.valueOf(0));
		discussionReactionsDTO.setModifiedDate(LocalDateTime.now());
		DiscussionReactions discussionReactions = discussionReactionsMapper.map(discussionReactionsDTO);
		this.discussionRactionsRepository.save(discussionReactions);
		Logger.logInfo.info("Update discussion_reaction by id =" + id + "", "DiscussionReactions");
	}

	@Override
	public void delete(long discussionId, Long userId) {
		DiscussionReactions discussionReactions = discussionRactionsRepository.findByDiscussionIdAndCreatedBy(discussionId, userId);
		if ( discussionReactions != null ) {
			discussionRactionsRepository.delete(discussionReactions);
			Logger.logInfo.info(
					"Delete discussion_reaction of discussion :" + discussionId + "by user id =" + userId + "",
					"DiscussionReactions");
		} else {
			throw new ErrorMessageException(String.format(messageSource.getMessage("NotFound", null, null),
					"DiscussionReactions", " Request", String.valueOf("")), TypeError.NotFound);
		}
	}

	@Override
	public void deleteByDiscussionId(Long discussionId) {
		List<DiscussionReactions> discussionReactions = discussionRactionsRepository
				.findAllByDiscussionId(discussionId);
		discussionReactions.forEach(item -> {
			item.setDiscussion(null);
			this.discussionRactionsRepository.save(item);
			this.discussionRactionsRepository.deleteById(item.getId());
		});
	}

}
