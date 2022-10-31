package com.r2s.pte.service.impl;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.r2s.pte.common.Logger;
import com.r2s.pte.common.TypeError;
import com.r2s.pte.dto.DiscussionCreateDTO;
import com.r2s.pte.dto.DiscussionDTO;
import com.r2s.pte.dto.DiscussionSubDTO;
import com.r2s.pte.dto.DiscussionViewDTO;
import com.r2s.pte.dto.LessonCreateDTO;
import com.r2s.pte.dto.PaginationDTO;
import com.r2s.pte.dto.ScoreResponseDTO;
import com.r2s.pte.dto.UserContext;
import com.r2s.pte.entity.Discussion;
import com.r2s.pte.exception.ErrorMessageException;
import com.r2s.pte.mapper.DiscussionMapper;
import com.r2s.pte.repository.DiscussionRepository;
import com.r2s.pte.repository.LessonRepository;
import com.r2s.pte.service.DiscussionReactionsService;
import com.r2s.pte.service.DiscussionService;
import com.r2s.pte.service.UserScoreService;

@Service
public class DiscussionServiceImpl implements DiscussionService {

	@Autowired
	private DiscussionRepository discussionRepository;
	@Autowired
	private DiscussionMapper discussionMapper;
	@Autowired
	private LessonRepository lessonRepository;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private DiscussionReactionsService discussionReactionsService;
	@Autowired
	private UserScoreService userScoreService;

	@Override
	public PaginationDTO findAllDiscussionByLessonId(long lessonId, int page, int limit) {
		Pageable pageable = PageRequest.of(page, limit);
		List<DiscussionViewDTO> discussions = this.discussionRepository
				.findAllByLessonIdAndParentIdAndIsIncludeAnswer(lessonId, null, pageable,false).stream()
				.map(item -> this.discussionMapper.mapToViewDTO(item)).collect(Collectors.toList());
		Page<Discussion> pageDiscussion = this.discussionRepository.findAllByLessonIdAndParentIdAndIsIncludeAnswer(lessonId, null,
				pageable,false);
		PaginationDTO pageDTO = new PaginationDTO(discussions, pageDiscussion.isFirst(), pageDiscussion.isLast(),
				pageDiscussion.getTotalPages(), pageDiscussion.getTotalElements(), pageDiscussion.getSize(),
				pageDiscussion.getNumber());
		Logger.logInfo.info("Get all discussion by lesson id =" + lessonId + "", "Discussion");
		return pageDTO;
	}
	
	@Override
	public PaginationDTO findAllDiscussionIncludeAnswerByLessonId(long lessonId,Long userId, int page, int limit) {
		List<ScoreResponseDTO> discussionIncludeAnswerDTOs = userScoreService.getByLessoAndUser(lessonId, userId);
		for(ScoreResponseDTO responseDTO : discussionIncludeAnswerDTOs) {
			Discussion discussion = discussionRepository.findByCreatedDateAndCreatedByAndLessonIdAndIsIncludeAnswer(responseDTO.getCreateDate(),responseDTO.getUserId(), lessonId, true);
			if(discussion!=null) {
				responseDTO.setId(discussion.getId());
				Set<DiscussionSubDTO> discussionSubs = new HashSet<>();
				for (Discussion discussionSub :  discussionRepository.findAllByParentId(discussion.getId())) {
					discussionSubs.add(discussionMapper.mapToSubDTO(discussionSub));
				}
				responseDTO.setDiscussionSubs(discussionSubs);
			}
		}
		PaginationDTO pageDTO = new PaginationDTO().customPagination(discussionIncludeAnswerDTOs, discussionIncludeAnswerDTOs.size(), page, limit);
		Logger.logInfo.info("Get all discussion include answer by lesson id =" + lessonId + "", "Discussion");
		return pageDTO;
	}

	@Override
	public boolean existsById(Long id) {
		return discussionRepository.existsById(id);
	}

	@Override
	public void save(DiscussionCreateDTO discussionDTO) {
		Discussion discussion = new Discussion();
		try {
			if(discussionDTO.getIsIncludeAnswer()==null) {
				discussionDTO.setIsIncludeAnswer(false);
			}
			if(discussionDTO.getCreatedDate()==null) {
				discussionDTO.setCreatedDate(LocalDateTime.now());
			}
			if(discussionDTO.getModifiedDate()==null) {
				discussionDTO.setModifiedDate(LocalDateTime.now());
			}
			LessonCreateDTO lessonCreateDTO = discussionDTO.getLesson();
			lessonRepository.findById(lessonCreateDTO.getId()).orElseThrow(
					() -> new ErrorMessageException(String.format(messageSource.getMessage("NotFound", null, null),
							"Lesson", "Id", String.valueOf(lessonCreateDTO.getId())), TypeError.NotFound));
			discussionDTO.setLesson(lessonCreateDTO);
			discussion = discussionMapper.map(discussionDTO);
			discussion.setCreatedBy(UserContext.getId());
			discussion.setModifiedBy(UserContext.getId());
			} catch (Exception e) {
			e.printStackTrace();
		}
		this.discussionRepository.save(discussion);
		Logger.logInfo.info("Add discussion with id =" + discussion.getId() + "", "Discussion");

	}
	
	@Override
	public void update(DiscussionCreateDTO discussionUpdateDTO, long id) {
		DiscussionDTO discussionDTO = findById(id);
		if (UserContext.getId() != discussionDTO.getCreatedBy()) {
			throw new ErrorMessageException(String.format(messageSource.getMessage("Forbidden", null, null)),
					TypeError.Forbidden);
		}
		if (discussionUpdateDTO.getDiscussionContent() != null) {
			discussionDTO.setDiscussionContent(discussionUpdateDTO.getDiscussionContent());
		}
		discussionDTO.setModifiedDate(LocalDateTime.now());
		Discussion discussion = discussionMapper.map(discussionDTO);
		discussion.setModifiedBy(UserContext.getId());
		this.discussionRepository.save(discussion);
		Logger.logInfo.info("Update discussion by id =" + id + "", "Discussion");
	}

	@Override
	public void delete(long id) {
		if (existsById(id) == false) {
			throw new ErrorMessageException(String.format(messageSource.getMessage("NotFound", null, null),
					"Discussion", "Id", String.valueOf(id)), TypeError.NotFound);
		} else {
			DiscussionDTO discussionDTO = findById(id);
			if (UserContext.getId() != discussionDTO.getCreatedBy()) {
				throw new ErrorMessageException(String.format(messageSource.getMessage("Forbidden", null, null)),
						TypeError.Forbidden);
			}
			List<Discussion> discussions = discussionRepository.findAllByParentId(id);
			for (Discussion discussion : discussions) {
				discussionRepository.deleteById(discussion.getId());
			}
			discussionRepository.deleteById(id);
		}
	}

	@Override
	public DiscussionDTO findById(long id) {
		Discussion discussion = discussionRepository.findById(id).orElseThrow(() -> new ErrorMessageException(
				String.format(messageSource.getMessage("NotFound", null, null), "Discussion", "Id", String.valueOf(id)),
				TypeError.NotFound));
		Logger.logInfo.info("Get lesson by id {}", "Lesson");
		return discussionMapper.map(discussion);
	}

	@Override
	public void deleteByLesson(Long lessonId) {
		// delete discussion reaction
		List<Discussion> discussions = this.discussionRepository.findByLessonId(lessonId);
		discussions.forEach(discussion -> {
			this.discussionReactionsService.deleteByDiscussionId(discussion.getId());
			discussion.setLesson(null);
			this.discussionRepository.save(discussion);
			this.discussionRepository.deleteById(discussion.getId());
		});
	}

	
}
