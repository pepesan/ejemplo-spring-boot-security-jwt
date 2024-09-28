package com.cursosdedesarrollo.ejemplospringbootsecurityjwt.repositories;

import com.cursosdedesarrollo.ejemplospringbootsecurityjwt.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsernameOrEmail(String username, String email);
}
