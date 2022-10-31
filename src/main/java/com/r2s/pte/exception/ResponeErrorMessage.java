package com.r2s.pte.exception;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ResponeErrorMessage {
	private int httpCode;
	private String message;
	private String timestamp;
	private String path;
	private String description;
	public ResponeErrorMessage()
	{
		
	}
	public ResponeErrorMessage(int code, String message)
	{
		this.httpCode =  code;
		this.message =  message;
		this.timestamp = LocalDateTime.now().toString();
	}
	public ResponeErrorMessage(int code, String message, String path)
	{
		this.httpCode =  code;
		this.message =  message;
		this.path = path;
		this.timestamp = LocalDateTime.now().toString();
	}
	public ResponeErrorMessage(int code, String message, String path, String description)
	{
		this.httpCode =  code;
		this.message =  message;
		this.path = path;
		this.description = description;
		this.timestamp = LocalDateTime.now().toString();
	}

}
