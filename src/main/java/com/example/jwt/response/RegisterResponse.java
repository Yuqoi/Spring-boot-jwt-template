package com.example.jwt.response;

import java.util.Map;

public record RegisterResponse (
    String error,
    String message,
    JwtDetailsResponse details
){}

