package com.ehdehlrbwl.rentbook.service;


import com.ehdehlrbwl.rentbook.dto.BookRequestDto;
import com.ehdehlrbwl.rentbook.dto.BookResponseDto;
import com.ehdehlrbwl.rentbook.entity.Book;
import com.ehdehlrbwl.rentbook.exception.NotFoundException;
import com.ehdehlrbwl.rentbook.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public BookResponseDto createBook(BookRequestDto request) {
        Book book = Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .build();

        Book saved = bookRepository.save(book);
        return mapToDto(saved);
    }

    @Override
    public List<BookResponseDto> getAllBooks() {
        return bookRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public BookResponseDto getBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존제하지 않는 책 입니다."));
        return mapToDto(book);
    }

    @Override
    public BookResponseDto updateBook(Long id, BookRequestDto request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존제하지 않는 책 입니다."));
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        return mapToDto(book);
    }

    @Override
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존제하지 않는 책 입니다."));
        bookRepository.delete(book);
    }

    @Override
    public List<BookResponseDto> searchBooksByTitle(String keyword) {
        return bookRepository.findByTitleContainingIgnoreCase(keyword)
                .stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private BookResponseDto mapToDto(Book book) {
        return BookResponseDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .available(book.isAvailable())
                .build();
    }
}
