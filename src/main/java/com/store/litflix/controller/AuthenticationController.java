package com.store.litflix.controller;

import com.store.litflix.dto.user.UserLoginRequestDto;
import com.store.litflix.dto.user.UserLoginResponseDto;
import com.store.litflix.dto.user.UserRegistrationRequestDto;
import com.store.litflix.exception.RegistrationException;
import com.store.litflix.security.AuthenticationService;
import com.store.litflix.service.UserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
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
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public UserLoginResponseDto login(@RequestBody UserLoginRequestDto userLoginRequestDto) {
        return authenticationService.authenticate(userLoginRequestDto);
    }

    @PostMapping("/registration")
    public UserLoginResponseDto register(
            @Valid
            @RequestBody UserRegistrationRequestDto requestDto) throws RegistrationException {
        return userService.registerUser(requestDto);
    }
}
