package com.r2s.pte.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookLessonDTO {
	private long id;
	private Long lessonId;
	private Long questionGroupId;
	private Long questionId;
	private Long questionOptionId;
	private Long questionSolutionId;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime modifiedDate;
	private Long modifiedBy;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdDate;
	private Long createdBy;
	private BookDTO book;
}
