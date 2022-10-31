package com.r2s.pte.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.r2s.pte.dto.VoiceConfigDTO;
import com.r2s.pte.entity.VoiceConfig;
import com.r2s.pte.mapper.VoiceConfigMapper;
import com.r2s.pte.repository.VoiceConfigRepository;
import com.r2s.pte.service.VoiceConfigService;
@Service
public class VoiceConfigServiceImpl implements VoiceConfigService {
	@Autowired
	private VoiceConfigRepository voiceConfigRepository;
	@Autowired
	private VoiceConfigMapper voiceConfigMapper;
	
	@Override
	public List<VoiceConfigDTO> getAll()
	{
		List<VoiceConfig> voiceConfigs = this.voiceConfigRepository.findAll();
		return voiceConfigs.stream().map(entity -> this.voiceConfigMapper.mapToDTO(entity)).collect(Collectors.toList());
	}
}
