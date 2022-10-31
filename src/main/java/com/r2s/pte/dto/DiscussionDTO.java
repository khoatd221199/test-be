package com.r2s.pte.dto;

import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DiscussionDTO {
	private Long id;
	private Long parentId;
	private String discussionContent;
	private Boolean isIncludeAnswer;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime modifiedDate;
	private Long modifiedBy;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdDate;
	private Long createdBy;
	private Set<DiscussionReactionsDTO> discussionReactionsDTOs ;
	private LessonCreateDTO lesson;
	private UserScoreDTO userScore;
}
