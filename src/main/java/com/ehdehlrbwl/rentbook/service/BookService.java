package com.ehdehlrbwl.rentbook.service;



import com.ehdehlrbwl.rentbook.dto.BookRequestDto;
import com.ehdehlrbwl.rentbook.dto.BookResponseDto;

import java.util.List;

public interface BookService {
    BookResponseDto createBook(BookRequestDto request);
    List<BookResponseDto> getAllBooks();
    BookResponseDto getBook(Long id);
    BookResponseDto updateBook(Long id, BookRequestDto request);
    void deleteBook(Long id);
    List<BookResponseDto> searchBooksByTitle(String keyword);
}
