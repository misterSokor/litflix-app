package com.store.litflix.security;

import com.store.litflix.dto.user.UserLoginRequestDto;
import com.store.litflix.dto.user.UserLoginResponseDto;
import com.store.litflix.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationService {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public UserLoginResponseDto authenticate(UserLoginRequestDto userLoginRequestDto) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginRequestDto.email(),
                        userLoginRequestDto.password()));

        User user = (User) authentication.getPrincipal();
        String token = jwtUtil.generateToken(user.getId());
        return new UserLoginResponseDto(token);
    }
}
