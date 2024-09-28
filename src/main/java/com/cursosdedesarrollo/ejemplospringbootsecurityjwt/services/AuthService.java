package com.cursosdedesarrollo.ejemplospringbootsecurityjwtv2.services;

import com.cursosdedesarrollo.ejemplospringbootsecurityjwtv2.dtos.LoginDto;

public interface AuthService {
    String login(LoginDto loginDto);
}
