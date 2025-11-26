package com.ehdehlrbwl.rentbook.service;

import com.ehdehlrbwl.rentbook.dto.UserRequestDto;
import com.ehdehlrbwl.rentbook.dto.UserResponseDto;
import jakarta.servlet.http.HttpServletRequest;

public interface UserService {
    UserResponseDto register(UserRequestDto request);
    String login(UserRequestDto request, HttpServletRequest httpRequest );
}
