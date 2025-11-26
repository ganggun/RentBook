package com.ehdehlrbwl.rentbook.controller;

import com.ehdehlrbwl.rentbook.dto.RentalResponseDto;
import com.ehdehlrbwl.rentbook.service.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final RentalService rentalService;



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
