package com.r2s.pte.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.r2s.pte.dto.UserContext;
import com.r2s.pte.util.JWTUtil;

public class CustomAuthorizationFilter extends OncePerRequestFilter {
	private final String GET = "GET";
	private final String POST = "POST";
	private final String DESCRIPTION_ACCESS_DENIED = "You don't have permission to access on this page!";
	private final String MESSAGE_ACCESS_DENIED = "Access denied";
	private final String BEARER = "Bearer ";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {

		String uri = request.getServletPath();
		String method = request.getMethod();
		// Anonymous
		if ((uri.startsWith("/api-dev/token") || uri.startsWith("/api-pro/token") || uri.startsWith("/api/token"))
				|| ((uri.startsWith("/api-dev/lesson") || uri.startsWith("/api-pro/lesson")
						|| uri.startsWith("/api/lesson")) && method.equals(GET))
				|| ((uri.equals("/api-dev/lesson/getall") || uri.equals("/api-pro/lesson/getall")
						|| uri.equals("/api/lesson/getall")) && method.equals(POST))
				|| (uri.startsWith("/api-dev/lesson/lessondetail")
						|| uri.startsWith("/api-pro/lesson/lessondetail") && method.equals(GET))
				|| uri.contains("category") || uri.contains("voice-configs") || uri.contains("lesson-category")
				|| uri.contains("category") || uri.contains("voice-configs") || uri.contains("discussion/lesson")
				|| uri.contains("discussion_reations") || uri.contains("lesson-category")) {
			filterChain.doFilter(request, response);
		} else // User
		{
			boolean isAccess = false;// default access is false
			String authorizationHeader = request.getHeader("Authorization");
			if (authorizationHeader != null && authorizationHeader.startsWith(BEARER)) {
				String token = authorizationHeader.substring(BEARER.length());// get token
				boolean isTrue = JWTUtil.verify(token);// verify token
				if (isTrue) {
					Long userType = UserContext.getTypeUser();
					if (userType != null) {
						if (userType.longValue() > 1)// ADMIN, SUPER ADMIN
							isAccess = true;
						else // LEARNER
						{
							if (uri.contains("user-score")
									|| (uri.contains("lesson") && method.equals(GET))
									|| uri.contains("tested")
									|| uri.contains("discussion")
									|| uri.contains("discussion_reations")
									|| uri.contains("vocab")
									|| uri.contains("vocabuser")
									|| uri.contains("token")
									
									)
								isAccess = true;
						}
					}
				}
			}
			if (isAccess)
				filterChain.doFilter(request, response);
			else
				handleAccessDenied(request, response);
		}
	}

	public void handleAccessDenied(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setStatus(HttpStatus.FORBIDDEN.value());
			Map<String, String> maps = new HashMap<String, String>();
			maps.put("code", String.valueOf(HttpStatus.FORBIDDEN.value()));
			maps.put("message", MESSAGE_ACCESS_DENIED);
			maps.put("path", request.getServletPath());
			maps.put("description", DESCRIPTION_ACCESS_DENIED);
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			new ObjectMapper().writeValue(response.getOutputStream(), maps);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
