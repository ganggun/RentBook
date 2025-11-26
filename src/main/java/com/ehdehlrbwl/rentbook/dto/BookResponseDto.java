package com.ehdehlrbwl.rentbook.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookResponseDto {

    @NotNull
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String author;

    private boolean available;
}