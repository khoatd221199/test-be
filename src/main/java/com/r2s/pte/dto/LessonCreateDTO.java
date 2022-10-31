package com.r2s.pte.dto;

import java.util.List;

import com.r2s.pte.entity.QuestionGroup;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LessonCreateDTO {
	private List<CategoryDTO> categoryDTO;
	private long id;
	private int languageId;
	private String title;
	private String content;
	private String description;
	private String internalNotes;
	private int preparationTime;
	private int duration;
	private boolean shared;
	private Long zorder;
	private QuestionGroup questionGroup;
	private String explanation;
	private String sourceMediaLinkShadow;
	private String sourceMediaLinkVideo;
	private String sourceMediaLinkImage;
	private List<QuestionDTO> questionDTO;
}
