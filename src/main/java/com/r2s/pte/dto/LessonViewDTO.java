package com.r2s.pte.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LessonViewDTO {
	private long id;
	private int languageId;
	private String title;
	private String content;
	private String description;
	private String internalNotes;
	private int preparationTime;
	private int duration;
	private boolean shared;
	private boolean status;
	private Long idQuestionType;
	private long zOrder;
	private String explanation;
	private String lessonSourceMediaLinkShadow;
	private String lessonSourceMediaLinkVideo;
	private String lessonSourceMediaLinkImage;
	private List<CategoryDTO> categories;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime modifiedDate;
	private long modifiedBy;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdDate;
	private long createdBy;
}

