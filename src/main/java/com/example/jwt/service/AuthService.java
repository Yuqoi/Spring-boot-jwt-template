package com.example.jwt.service;

import com.example.jwt.request.RegisterRequest;
import com.example.jwt.response.JwtDetailsResponse;
import com.example.jwt.response.RegisterResponse;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public RegisterResponse register(RegisterRequest request) {
        try {
            return new RegisterResponse(
                    null,
                    "Correctly registered",
                    new JwtDetailsResponse(
                            "abcdef",
                            "123123123"));
        } catch (Exception e) {
            throw e;
            // TODO: handle exception
        }

    }

}
