package com.r2s.pte.dto;

import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class VocabDTO {
	private long id;
	private String vocab;
	private Long countryId;
	private String ipa;
	private String definition;
	private Integer priority;
	private Boolean isPhrasalWord;
	private String example;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime modifiedDate;
	private Long modifiedBy;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdDate;
	private Long createdBy;
	private Set<VocabUserDTO> vocabUsers ;

}
