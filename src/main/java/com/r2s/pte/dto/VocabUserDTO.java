package com.r2s.pte.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class VocabUserDTO {
	private long id;
	private Long userId;
	private String definitionUser;
	private Integer priority;
	private String exampleUser;
	private Integer revisedCount;
	private Boolean status;
	private VocabDTO vocab;
	private CategoryDTO category;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime modifiedDate;
	private Long modifiedBy;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdDate;
	private Long createdBy;

}
