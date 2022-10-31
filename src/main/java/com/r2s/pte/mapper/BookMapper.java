package com.r2s.pte.mapper;


import org.mapstruct.Mapper;

import com.r2s.pte.dto.BookDTO;
import com.r2s.pte.entity.Book;


@Mapper(componentModel = "spring")
public interface BookMapper {
	Book map(BookDTO bookDTO);
	BookDTO map(Book book);
}
