package com.scb.demo.ecommerce.controller;


import com.scb.demo.ecommerce.entities.UserDetails;
import com.scb.demo.ecommerce.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {


    final UserRepository userRepository;

    @PostMapping("/v1/add/user")
    public String createUser(@RequestBody UserDetails userDetails) {
        return userRepository.save(userDetails).getUsername();
    }
}

