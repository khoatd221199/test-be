package com.r2s.pte.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LessonCreationDTO {
	private LessonAudioCreateDTO lesson;
	private MultipartFile image;
	private MultipartFile shadowing;
}
