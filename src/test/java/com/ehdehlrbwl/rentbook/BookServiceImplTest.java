package com.ehdehlrbwl.rentbook;
import com.ehdehlrbwl.rentbook.dto.BookRequestDto;
import com.ehdehlrbwl.rentbook.dto.BookResponseDto;
import com.ehdehlrbwl.rentbook.entity.Book;
import com.ehdehlrbwl.rentbook.exception.NotFoundException;
import com.ehdehlrbwl.rentbook.repository.BookRepository;
import com.ehdehlrbwl.rentbook.service.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class BookServiceImplTest {

    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBook_Success() {
        BookRequestDto request = new BookRequestDto("Spring Boot 입문", "홍길동");
        Book savedBook = Book.builder().id(1L).title("Spring Boot 입문").author("홍길동").available(true).build();

        when(bookRepository.save(any(Book.class))).thenReturn(savedBook);

        BookResponseDto response = bookService.createBook(request);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getTitle()).isEqualTo("Spring Boot 입문");
        assertThat(response.getAuthor()).isEqualTo("홍길동");
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void testGetBook_Success() {
        Book book = Book.builder().id(1L).title("JPA 완전 정복").author("김철수").available(true).build();
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        BookResponseDto response = bookService.getBook(1L);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getTitle()).isEqualTo("JPA 완전 정복");
    }

    @Test
    void testGetBook_NotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> bookService.getBook(1L));
    }

    @Test
    void testDeleteBook_Success() {
        Book book = Book.builder().id(1L).title("Java 21 핵심").author("이영희").available(true).build();
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        bookService.deleteBook(1L);

        verify(bookRepository, times(1)).delete(book);
    }

    @Test
    void testDeleteBook_NotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> bookService.deleteBook(1L));
    }
}
