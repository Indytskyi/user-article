package com.indytskyi.userservice.services.impl;

import com.indytskyi.userservice.dtos.AuthenticationRequestDto;
import com.indytskyi.userservice.dtos.AuthenticationResponse;
import com.indytskyi.userservice.dtos.RegisterRequestDto;
import com.indytskyi.userservice.dtos.RegisterResponseDto;
import com.indytskyi.userservice.models.enums.Role;
import com.indytskyi.userservice.models.User;
import com.indytskyi.userservice.repository.UserRepository;
import com.indytskyi.userservice.security.jwt.JwtService;
import com.indytskyi.userservice.services.AuthenticationService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor

public class AuthenticationServiceImpl implements AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        var user = userRepository.findByEmail(request.email()).orElseThrow();
        var jwtToken = jwtService.generateToken(Map.of("ROLE", user.getRole()), user);

        return new AuthenticationResponse(jwtToken);
    }

    @Override
    @Transactional
    public RegisterResponseDto register(RegisterRequestDto request) {
        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .age(request.getAge())
                .role(Role.USER)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(Map.of("ROLE", user.getRole()),user);
        return new RegisterResponseDto(jwtToken);
    }
}
