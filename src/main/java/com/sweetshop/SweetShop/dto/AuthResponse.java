package com.sweetshop.SweetShop.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthResponse {
    private String message;
    private String token;
    private String role;
}