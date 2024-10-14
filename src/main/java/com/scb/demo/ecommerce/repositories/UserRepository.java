package com.scb.demo.ecommerce.repositories;

import com.scb.demo.ecommerce.entities.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserDetails, Long> {
    Optional<UserDetails> findByUsername(String username);
}
