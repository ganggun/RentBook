package com.ehdehlrbwl.rentbook.service;

import com.ehdehlrbwl.rentbook.dto.RentalResponseDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface RentalService {
    RentalResponseDto rentBook(Long bookId, Authentication authentication);
    RentalResponseDto returnBook(Long bookId, Authentication authentication);
    List<RentalResponseDto> getAllOngoingRentalsByUser(Authentication authentication);
}

