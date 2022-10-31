package com.r2s.pte.mapper.impl;

import org.springframework.stereotype.Component;

import com.r2s.pte.dto.VoiceConfigDTO;
import com.r2s.pte.entity.VoiceConfig;
import com.r2s.pte.mapper.VoiceConfigMapper;
@Component
public class VoiceConfigMapperImpl implements VoiceConfigMapper {
	
	@Override
	public VoiceConfigDTO mapToDTO(VoiceConfig entity)
	{
		VoiceConfigDTO dto = new VoiceConfigDTO();
		dto.setAuthCode(entity.getAuthCode());
		dto.setId(entity.getId());
		dto.setVoiceLanguage(entity.getVoiceLanguage());
		dto.setVoiceName(entity.getVoiceName());
		return dto;
	}
}
