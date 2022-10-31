package com.r2s.pte.service;

import java.util.List;
import com.r2s.pte.entity.UserTarget;

public interface UserTargetService {
	
	UserTarget findById(Long id);

	List<UserTarget> getAll();

	
}
