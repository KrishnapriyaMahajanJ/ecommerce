package com.scb.demo.ecommerce.controller;

import com.scb.demo.ecommerce.config.JwtService;
import com.scb.demo.ecommerce.dto.AuthenticationRequest;
import com.scb.demo.ecommerce.dto.AuthenticationResponse;
import com.scb.demo.ecommerce.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @PostMapping("/v1/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            // Authenticate the user
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        // Load user details
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        // Generate JWT token
        final String jwt = jwtUtil.generateToken(userDetails);

        // Return the JWT token in the response
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}

