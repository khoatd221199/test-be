package com.r2s.pte.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.r2s.pte.service.VoiceConfigService;

@RestController
@RequestMapping(value = {"api-dev/voice-configs","api-pro/voice-configs","api/voice-configs"})
public class VoiceConfigController {
	@Autowired
	private VoiceConfigService voiceConfigService;
	@GetMapping("")
	public ResponseEntity<?> getAll() {
		return ResponseEntity.ok(this.voiceConfigService.getAll());
	}
}
