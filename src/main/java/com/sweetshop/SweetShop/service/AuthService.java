package com.sweetshop.SweetShop.service;

import com.sweetshop.SweetShop.dto.AuthResponse;
import com.sweetshop.SweetShop.dto.LoginRequest;
import com.sweetshop.SweetShop.dto.RegisterRequest;

public interface AuthService {

    AuthResponse register(RegisterRequest registerRequest);

    AuthResponse login(LoginRequest loginRequest);
}
