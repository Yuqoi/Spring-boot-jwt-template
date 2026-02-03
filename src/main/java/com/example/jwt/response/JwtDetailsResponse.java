package com.example.jwt.response;

public record JwtDetailsResponse (
    String accessToken,
    String jwtToken
) {}
