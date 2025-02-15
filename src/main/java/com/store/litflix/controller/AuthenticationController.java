package com.store.litflix.controller;

import com.store.litflix.dto.user.UserRegistrationRequestDto;
import com.store.litflix.dto.user.UserResponseDto;
import com.store.litflix.exception.RegistrationException;
import com.store.litflix.service.UserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication", description = "Endpoints for authentication")
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@OpenAPIDefinition(info = @Info(title = "Authentication API",
        version = "1.0",
        description = "Authentication API"))
public class AuthenticationController {
    private final UserService userService;

    @Operation(summary = "Register a new user", description = "Register a new user")
    @PostMapping("/registration")
    public UserResponseDto register(
            @Valid
            @RequestBody UserRegistrationRequestDto requestDto) throws RegistrationException {
        return userService.registerUser(requestDto);
    }
}
