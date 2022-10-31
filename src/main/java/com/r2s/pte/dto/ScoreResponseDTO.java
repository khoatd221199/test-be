package com.r2s.pte.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ScoreResponseDTO {
	private Long id;
	private Integer maxScore;
	private Integer score;
	private List<String> valueResponses;
	private String media;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createDate;
	private Long userId;
	private Set<DiscussionSubDTO> discussionSubs;

}
