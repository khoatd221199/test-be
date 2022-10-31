package com.r2s.pte.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.r2s.pte.dto.BookDTO;
import com.r2s.pte.entity.Book;
import com.r2s.pte.mapper.BookMapper;
import com.r2s.pte.repository.BookRepository;
import com.r2s.pte.service.BookService;
@Service
public class BookServiceImpl implements BookService{

	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private BookMapper mapperBook;
	
	@Override
	public BookDTO findById(Long id) {
		Optional<Book> entity = this.bookRepository.findById(id);
		if(entity.isPresent())
			return mapperBook.map(entity.get());
		else 
			return null;
	}

	@Override
	public List<BookDTO> getAll() {
		return this.bookRepository.findAll().stream().map(item -> this.mapperBook.map(item)).collect(Collectors.toList());
	}


}
