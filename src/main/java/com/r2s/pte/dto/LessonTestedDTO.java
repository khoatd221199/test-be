package com.r2s.pte.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LessonTestedDTO {
    private Long id;
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime examDate;
    private String description;
    private Long userId;
    private Long lessonId;
    private LocalDate recordTime;
}
