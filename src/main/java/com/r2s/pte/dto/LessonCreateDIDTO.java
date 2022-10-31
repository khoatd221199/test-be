package com.r2s.pte.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LessonCreateDIDTO  {
	private	LessonCreateDTO lessonCreateDTO;
	private MultipartFile image;
}
