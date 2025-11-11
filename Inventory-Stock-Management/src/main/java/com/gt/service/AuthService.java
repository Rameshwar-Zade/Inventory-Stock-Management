package com.gt.service;

import com.gt.dto.LoginRequest;
import com.gt.dto.RegisterRequest;
import java.util.Map;

public interface AuthService {
    Map<String, Object> register(RegisterRequest request);
    Map<String, Object> login(LoginRequest request);
}
