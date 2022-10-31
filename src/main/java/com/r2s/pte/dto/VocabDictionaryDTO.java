package com.r2s.pte.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VocabDictionaryDTO {
	private String vocab;
	private Long countryId;
	private String ipa;
	private String definition;
	private String example;
	private Boolean isPhrasalWord;
	private Boolean isVocabUser;
	
}
