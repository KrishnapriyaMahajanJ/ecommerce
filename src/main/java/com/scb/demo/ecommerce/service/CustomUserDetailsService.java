package com.scb.demo.ecommerce.service;


import com.scb.demo.ecommerce.entities.UserDetails;
import com.scb.demo.ecommerce.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor_ = @Autowired)
public class CustomUserDetailsService implements UserDetailsService {

    final UserRepository userRepository;

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                userDetails.getUsername(),
                userDetails.getPassword(),
                List.of(new SimpleGrantedAuthority(userDetails.getRole()))
        );
    }
}

