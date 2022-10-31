package com.r2s.pte.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.r2s.pte.common.Logger;

@RestControllerAdvice
public class ExceptionHandler {
	@Autowired
	private MessageSource messageSource;

	@org.springframework.web.bind.annotation.ExceptionHandler(value = ErrorMessageException.class)
	public ResponseEntity<?> handlerNotFound(ErrorMessageException ex, HttpServletRequest request,
			HttpServletResponse response, final WebRequest webRequest) {

		switch (ex.getTypeError()) {
		case NotFound:
			Logger.logInfo.info(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
					new ResponeErrorMessage(HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getServletPath()));
		case InternalServerError:
			String message = messageSource.getMessage("InternalServerError", null, null);
			Logger.logWarn.warn(message);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponeErrorMessage(
					HttpStatus.INTERNAL_SERVER_ERROR.value(), message, request.getServletPath()));
		case BadRequest:
			Logger.logWarn.warn(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					new ResponeErrorMessage(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), request.getServletPath()));
		case NoContent:
			Logger.logWarn.warn(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
					new ResponeErrorMessage(HttpStatus.NO_CONTENT.value(), ex.getMessage(), request.getServletPath()));
		case AlreadyExists:
			Logger.logWarn.warn(ex.getMessage());
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(new ResponeErrorMessage(409, ex.getMessage(), request.getServletPath()));
		case Forbidden:
			Logger.logWarn.warn(ex.getMessage());
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
					new ResponeErrorMessage(HttpStatus.FORBIDDEN.value(), this.messageSource.getMessage("Forbidden", null,null), request.getServletPath()));
		default:
			return null;
		}
	}
	@org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
	public ResponseEntity<?> handleException(Exception exception, HttpServletRequest request,
			HttpServletResponse response) {
		ResponeErrorMessage responeErrorMessage = new ResponeErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				exception.getMessage(), request.getServletPath());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responeErrorMessage);
	}
}
