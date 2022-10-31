package com.r2s.pte.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class LogoutDTO {
	private long status;
	private String message;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime time;

	public LogoutDTO(long status, String message) {
		this.message = message;
		this.status = status;
		time = LocalDateTime.now();
	}
}
