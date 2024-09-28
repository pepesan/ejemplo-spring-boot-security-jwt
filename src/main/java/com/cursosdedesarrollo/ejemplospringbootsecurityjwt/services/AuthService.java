package com.cursosdedesarrollo.ejemplospringbootsecurityjwt.services;

import com.cursosdedesarrollo.ejemplospringbootsecurityjwt.dtos.LoginDto;

public interface AuthService {
    String login(LoginDto loginDto);
}
