package com.cursosdedesarrollo.ejemplospringbootsecurityjwtv2.repositories;

import com.cursosdedesarrollo.ejemplospringbootsecurityjwtv2.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsernameOrEmail(String username, String email);
}
