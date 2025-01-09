package com.store.litflix.service.impl;

import com.store.litflix.dto.user.UserRegistrationRequestDto;
import com.store.litflix.dto.user.UserResponseDto;
import com.store.litflix.exception.RegistrationException;
import com.store.litflix.mapper.UserMapper;
import com.store.litflix.model.User;
import com.store.litflix.repository.user.UserRepository;
import com.store.litflix.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto registerUser(
            UserRegistrationRequestDto requestDto) throws RegistrationException {
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new RegistrationException(
                    "User with email "
                    + requestDto.getEmail()
                    + " already exists");
        }
        User user = userMapper.toEntity(requestDto);

        User savedUser = userRepository.save(user);
        return userMapper.toUserResponse(savedUser);
    }
}
