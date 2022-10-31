package com.r2s.pte.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VocabViewDTO {
	private long id;
	private String vocab;
	private Long countryId;
	private String ipa;
	private Boolean isPhrasalWord;
	
}
