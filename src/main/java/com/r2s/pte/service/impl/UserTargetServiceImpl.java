package com.r2s.pte.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.r2s.pte.entity.UserTarget;
import com.r2s.pte.repository.UserTargetRepository;
import com.r2s.pte.service.UserTargetService;
@Service
public class UserTargetServiceImpl implements UserTargetService {

	@Autowired
	private UserTargetRepository userTargetRepository;
	
	@Override
	public UserTarget findById(Long id) {
		Optional<UserTarget> entity = this.userTargetRepository.findById(id);
		if(entity.isPresent())
			return entity.get();
		else 
			return null;
	}

	@Override
	public List<UserTarget> getAll() {
		return this.userTargetRepository.findAll();
	}

}
