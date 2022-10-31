package com.r2s.pte.exception;

import com.r2s.pte.common.TypeError;

import lombok.Getter;

@Getter
public class ErrorMessageException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private TypeError typeError;
	private String message;

	public ErrorMessageException(String msg) {
		super(msg);
	}

	public ErrorMessageException(String message, TypeError typeError) {
		this.typeError = typeError;
		this.message = message;
	}
}
