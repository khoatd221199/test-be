package com.r2s.pte.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LessonDetailViewDTO extends LessonViewDTO{
	private QuestionGroupViewDTO questionGroup; 
	private  List<LessonMediaDTO> lessonMediaDTOs;
}
