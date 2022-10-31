package com.r2s.pte.exception;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
@Component
public class  CustomAccessDeniedHandler implements AccessDeniedHandler {
	@Autowired
	private MessageSource messageSource;
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		response.setStatus(HttpStatus.FORBIDDEN.value());
		Map<String, String> maps = new HashMap<String, String>();
		maps.put("httpCode", String.valueOf(HttpStatus.FORBIDDEN.value()));
		maps.put("message", this.messageSource.getMessage("Forbidden", null, null));
		maps.put("path", request.getServletPath());
		new ObjectMapper().writeValue(response.getOutputStream(), maps);
		
	}

}
