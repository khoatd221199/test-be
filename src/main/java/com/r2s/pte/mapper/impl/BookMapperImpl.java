package com.r2s.pte.mapper.impl;

import org.springframework.stereotype.Component;

import com.r2s.pte.dto.BookDTO;
import com.r2s.pte.entity.Book;
import com.r2s.pte.mapper.BookMapper;

@Component
public class BookMapperImpl implements BookMapper {

	@Override
	public Book map(BookDTO dto) {
		if (dto == null)
			return null;
		Book entity = new Book();
		entity.setId(dto.getId());
		entity.setName(dto.getName());
		entity.setType(dto.getType());
		entity.setCreatedBy(dto.getCreatedBy());
		entity.setCreatedDate(dto.getCreatedDate());
		entity.setModifiedBy(dto.getModifiedBy());
		entity.setModifiedDate(dto.getModifiedDate());
		entity.setBookLessons(dto.getBookLessons());
		return entity;
	}

	@Override
	public BookDTO map(Book entity) {
		if (entity == null)
			return null;
		BookDTO dto = new BookDTO();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setType(entity.getType());
		dto.setCreatedBy(entity.getCreatedBy());
		dto.setCreatedDate(entity.getCreatedDate());
		dto.setModifiedBy(entity.getModifiedBy());
		dto.setModifiedDate(entity.getModifiedDate());
		dto.setBookLessons(entity.getBookLessons());
		return dto;
	}

}
