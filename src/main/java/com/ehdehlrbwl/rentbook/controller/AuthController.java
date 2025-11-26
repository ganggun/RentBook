package com.ehdehlrbwl.rentbook.controller;

import com.ehdehlrbwl.rentbook.dto.UserRequestDto;
import com.ehdehlrbwl.rentbook.dto.UserResponseDto;
import com.ehdehlrbwl.rentbook.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @Operation(summary = "회원가입", description = "새로운 유저를 등록합니다.")
    @ApiResponse(responseCode = "200", description = "회원가입 성공",
            content = @Content(schema = @Schema(implementation = UserResponseDto.class)))
    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(
            @Parameter(description = "회원가입 요청 DTO", required = true)
            @Valid @RequestBody UserRequestDto request) {
        return ResponseEntity.ok(userService.register(request));
    }

    @Operation(summary = "로그인", description = "로그인 합니다.")
    @ApiResponse(responseCode = "200", description = "로그인 성공",
            content = @Content(schema = @Schema(implementation = String.class)))
    @PostMapping("/login")
    public ResponseEntity<String> login(
            @Parameter(description = "로그인 요청 DTO", required = true)
            @Valid @RequestBody UserRequestDto request
            , HttpServletRequest httpRequest) {
        return ResponseEntity.ok(userService.login(request,httpRequest));
    }

    @PostMapping("/test")
    public String test(Authentication authentication) {
        return authentication == null ? "null" : authentication.getName();
    }

}