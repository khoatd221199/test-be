package com.r2s.pte.service;

import java.util.List;

import com.r2s.pte.dto.BookDTO;

public interface BookService {
	
	
	BookDTO findById(Long id);

	List<BookDTO> getAll();


}
