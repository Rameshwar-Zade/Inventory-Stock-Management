package com.gt.controller;

import com.gt.dto.BaseResponseDTO;
import com.gt.dto.LoginRequest;
import com.gt.dto.RegisterRequest;
import com.gt.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public BaseResponseDTO<Map<String, Object>> register(@RequestBody RegisterRequest request) {
        Map<String, Object> data = authService.register(request);
        return new BaseResponseDTO<>(HttpStatus.OK.value(), "User registered successfully", data);
    }

    @PostMapping("/login")
    public BaseResponseDTO<Map<String, Object>> login(@RequestBody LoginRequest request) {
        Map<String, Object> data = authService.login(request);
        return new BaseResponseDTO<>(HttpStatus.OK.value(), "Login successful", data);
    }
}
