package com.ehdehlrbwl.rentbook.controller;

import com.ehdehlrbwl.rentbook.dto.BookRequestDto;
import com.ehdehlrbwl.rentbook.dto.BookResponseDto;
import com.ehdehlrbwl.rentbook.dto.RentalResponseDto;
import com.ehdehlrbwl.rentbook.service.BookService;
import com.ehdehlrbwl.rentbook.service.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final RentalService rentalService;

    // 책 CRUD
    @Operation(summary = "책 등록", description = "새로운 책을 등록합니다.")
    @ApiResponse(responseCode = "200", description = "책 등록 성공",
            content = @Content(schema = @Schema(implementation = BookResponseDto.class)))
    @PostMapping
    public ResponseEntity<BookResponseDto> createBook(
            @Parameter(description = "책 등록 요청 DTO", required = true)
            @Valid @RequestBody BookRequestDto request) {
        return ResponseEntity.ok(bookService.createBook(request));
    }

    @Operation(summary = "모든 책 조회", description = "전체 책 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<BookResponseDto>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @Operation(summary = "책 단건 조회", description = "ID로 책 정보를 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDto> getBook(
            @Parameter(description = "조회할 책 ID", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBook(id));
    }

    @Operation(summary = "책 수정", description = "ID로 책 정보를 수정합니다.")
    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDto> updateBook(
            @Parameter(description = "수정할 책 ID", required = true)
            @PathVariable Long id,
            @Parameter(description = "책 수정 요청 DTO", required = true)
            @Valid @RequestBody BookRequestDto request) {
        return ResponseEntity.ok(bookService.updateBook(id, request));
    }

    @Operation(summary = "책 삭제", description = "ID로 책을 삭제합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(
            @Parameter(description = "삭제할 책 ID", required = true)
            @PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    // 책 검색
    @Operation(summary = "책 검색", description = "제목으로 책을 검색합니다.")
    @GetMapping("/search")
    public ResponseEntity<List<BookResponseDto>> searchBooks(
            @Parameter(description = "검색할 책 제목", required = true)
            @RequestParam String title) {
        return ResponseEntity.ok(bookService.searchBooksByTitle(title));
    }

    // 대여/반납
    @Operation(summary = "책 대여", description = "ID로 책을 대여합니다.")
    @PostMapping("/{id}/rent")
    public ResponseEntity<RentalResponseDto> rentBook(
            @Parameter(description = "대여할 책 ID", required = true)
            @PathVariable Long id,
            Authentication authentication) {
        return ResponseEntity.ok(rentalService.rentBook(id, authentication));
    }

    @Operation(summary = "책 반납", description = "대여 ID로 책을 반납합니다.")
    @PostMapping("/return/{bookId}")
    public ResponseEntity<RentalResponseDto> returnBook(
            @Parameter(description = "반납할 책 ID", required = true)
            @PathVariable Long bookId,
            Authentication authentication) {
        return ResponseEntity.ok(rentalService.returnBook(bookId,authentication));
    }

    @Operation(summary = "대여 중인 책 조회", description = "현재 대여 중인 모든 책 목록을 조회합니다.")
    @GetMapping("/rentals/ongoing")
    public ResponseEntity<List<RentalResponseDto>> getOngoingRentals(
            Authentication authentication
    ) {
        return ResponseEntity.ok(rentalService.getAllOngoingRentalsByUser(authentication));
    }
}
