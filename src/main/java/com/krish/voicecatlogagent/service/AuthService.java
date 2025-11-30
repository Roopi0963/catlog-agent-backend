package com.krish.voicecatlogagent.service;

import com.krish.voicecatlogagent.dto.AuthResponse;
import com.krish.voicecatlogagent.dto.LoginRequest;
import com.krish.voicecatlogagent.dto.RegisterRequest;
import com.krish.voicecatlogagent.model.Vendor;
import com.krish.voicecatlogagent.repository.VendorRepository;
import com.krish.voicecatlogagent.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final VendorRepository vendorRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        if (vendorRepository.existsByEmail(request.getEmail())) {
//            throw new RuntimeException("Email already in use");
            throw new RuntimeException("Email already in use");
        }
        Vendor vendor = Vendor.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        vendorRepository.save(vendor);

        String token = jwtTokenProvider.generateToken(vendor.getEmail());
        return new AuthResponse(token, vendor.getName());
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        String token = jwtTokenProvider.generateToken(request.getEmail());
        Vendor vendor = vendorRepository.findByEmail(request.getEmail()).orElseThrow();
        return new AuthResponse(token, vendor.getName());
    }
}