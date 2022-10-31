package com.r2s.pte.dto;

import lombok.NoArgsConstructor;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LessonAudioCreateDTO {
	private LessonCreateDTO lessonCreate;
	private List<LessonMediaDTO> lessonMedias;
}
