package com.ehdehlrbwl.rentbook.service;


import com.ehdehlrbwl.rentbook.dto.RentalResponseDto;
import com.ehdehlrbwl.rentbook.entity.Book;
import com.ehdehlrbwl.rentbook.entity.RentalRecord;
import com.ehdehlrbwl.rentbook.entity.User;
import com.ehdehlrbwl.rentbook.exception.NotFoundException;
import com.ehdehlrbwl.rentbook.repository.BookRepository;
import com.ehdehlrbwl.rentbook.repository.RentalRecordRepository;
import com.ehdehlrbwl.rentbook.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RentalServiceImpl implements RentalService {

    private final BookRepository bookRepository;
    private final RentalRecordRepository rentalRepository;
    private final UserRepository userRepository;

    @Override
    public RentalResponseDto rentBook(Long bookId, Authentication authentication) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException("없는 책입니다"));
        if(!book.isAvailable()) {
            throw new IllegalStateException("이미 빌린 책 입니다");
        }

        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new NotFoundException("User not found"));
        book.setAvailable(false);
        RentalRecord rental = RentalRecord.builder()
                .book(book)
                .user(user)
                .rentalDate(LocalDateTime.now())
                .build();
        RentalRecord saved = rentalRepository.save(rental);
        return mapToDto(saved);
    }

    @Override
    public RentalResponseDto returnBook(Long bookId, Authentication authentication) {

        String username = authentication.getName();

        RentalRecord rental = rentalRepository.findOngoingRental(bookId, username)
                .orElseThrow(() -> new IllegalArgumentException("대여 기록이 존재하지 않습니다."));

        rental.setReturnDate(LocalDateTime.now());
        rentalRepository.save(rental);

        Book book = rental.getBook();
        book.setAvailable(true);

        return RentalResponseDto.builder()
                .id(rental.getId())
                .bookId(book.getId())
                .username(username)
                .rentalDate(rental.getRentalDate())
                .returnDate(rental.getReturnDate())
                .build();
    }


    @Override
    public List<RentalResponseDto> getAllOngoingRentalsByUser(Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));

        return rentalRepository.findByUserIdAndReturnDateIsNull(user.getId())
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private RentalResponseDto mapToDto(RentalRecord rental) {
        return RentalResponseDto.builder()
                .id(rental.getId())
                .username(rental.getUser().getUsername())
                .rentalDate(rental.getRentalDate())
                .returnDate(rental.getReturnDate())
                .bookId(rental.getBook().getId())
                .build();
    }
}
