package com.cursosdedesarrollo.ejemplospringbootsecurityjwtv2.repositories;

import com.cursosdedesarrollo.ejemplospringbootsecurityjwtv2.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
