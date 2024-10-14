package com.scb.demo.ecommerce.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    @BCrypt
    private String password; // Store hashed passwords

    @Column(nullable = false)
    private String role; // ROLE_ADMIN, ROLE_USER

    public UserDetails(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}

