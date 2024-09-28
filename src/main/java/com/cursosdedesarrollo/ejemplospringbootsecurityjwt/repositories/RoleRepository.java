package com.cursosdedesarrollo.ejemplospringbootsecurityjwt.repositories;

import com.cursosdedesarrollo.ejemplospringbootsecurityjwt.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
