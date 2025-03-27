package com.store.litflix.service.impl;

import com.store.litflix.dto.user.UserLoginResponseDto;
import com.store.litflix.dto.user.UserRegistrationRequestDto;
import com.store.litflix.exception.RegistrationException;
import com.store.litflix.mapper.UserMapper;
import com.store.litflix.model.Role;
import com.store.litflix.model.RoleName;
import com.store.litflix.model.User;
import com.store.litflix.repository.roles.RoleRepository;
import com.store.litflix.repository.user.UserRepository;
import com.store.litflix.security.JwtUtil;
import com.store.litflix.service.UserService;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JwtUtil jwtUtil;

    @Override
    public UserLoginResponseDto registerUser(
            UserRegistrationRequestDto requestDto) throws RegistrationException {
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new RegistrationException(
                    "User with email " + requestDto.getEmail() + " already exists");
        }

        User user = userMapper.toEntity(requestDto);
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));

        Role userRole = roleRepository.findByRoleName(RoleName.ROLE_USER);
        user.setRoles(Set.of(userRole));

        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getEmail());

        return new UserLoginResponseDto(token);
    }
}
