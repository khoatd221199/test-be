package com.r2s.pte.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LessonMediaDTO {
	private Long id;
    private String type;
    private String voiceName;
	private String lessonMediaLink;
    private Long order;
}
