package com.r2s.pte.dto;

import org.springframework.security.core.context.SecurityContextHolder;

import com.r2s.pte.common.TypeError;
import com.r2s.pte.exception.ErrorMessageException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserContext {
	private Long userType;
	private Long userId;

	public static Long getTypeUser() {
		return Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().findFirst()
				.get().getAuthority());
	}
	public static Long getId() {
		try {
			Long id = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (id == null)
				throw new ErrorMessageException("Forbidden", TypeError.Forbidden);
			return id;
		}catch (Exception ex)
		{
			throw new ErrorMessageException("Forbidden", TypeError.Forbidden);
		}



	}

}
