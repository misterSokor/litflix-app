package com.store.litflix.service;

import com.store.litflix.dto.user.UserLoginResponseDto;
import com.store.litflix.dto.user.UserRegistrationRequestDto;
import com.store.litflix.exception.RegistrationException;

public interface UserService {
    UserLoginResponseDto registerUser(UserRegistrationRequestDto requestDto)
            throws RegistrationException;
}
