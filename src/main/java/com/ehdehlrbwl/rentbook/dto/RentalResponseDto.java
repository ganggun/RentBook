package com.ehdehlrbwl.rentbook.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RentalResponseDto {

    @NotNull
    private Long id;

    @NotNull
    private String username;

    @NotNull
    private LocalDateTime rentalDate;

    private LocalDateTime returnDate;

    @NotNull
    private Long bookId;
}
