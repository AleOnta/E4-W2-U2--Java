package com.api_rest.auth.service;

import com.api_rest.auth.payload.LoginDto;
import com.api_rest.auth.payload.RegisterDto;

public interface AuthService {
    
	String login(LoginDto loginDto);
    String register(RegisterDto registerDto);
    
}
