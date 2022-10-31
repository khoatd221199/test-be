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
public class DiscussionCreateDTO {
	private Long id;
	private Long parentId;
	private Boolean isIncludeAnswer;
	private String discussionContent;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime modifiedDate;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdDate;
	private Set<DiscussionReactionsDTO> discussionReactionsDTOs ;
	private LessonCreateDTO lesson;
	private UserScoreDTO userScore;
}
