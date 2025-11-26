package com.ehdehlrbwl.rentbook.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto {

    @NotNull
    private Long id;

    @NotNull
    private String username;

    @NotNull
    private String role;
}