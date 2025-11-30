package com.krish.voicecatlogagent.security;

import com.krish.voicecatlogagent.model.Vendor;
import com.krish.voicecatlogagent.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final VendorRepository vendorRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // We use 'findByEmail' because the 'username' in the login form is actually the email
        Vendor vendor = vendorRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Vendor not found with email: " + username));

        // Return the Spring Security User object
        return new org.springframework.security.core.userdetails.User(
                vendor.getEmail(),
                vendor.getPassword(),
                new ArrayList<>()
        );
    }
}