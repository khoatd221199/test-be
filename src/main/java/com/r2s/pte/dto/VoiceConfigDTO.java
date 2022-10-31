package com.r2s.pte.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class VoiceConfigDTO {
	private long id;
	private String authCode;
	private String voiceName;
	private String voiceLanguage;
}
