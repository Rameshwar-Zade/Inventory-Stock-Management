package com.gt.service;

import com.gt.dto.LoginRequest;
import com.gt.dto.RegisterRequest;
import com.gt.entity.Role;
import com.gt.entity.User;
import com.gt.exception.AuthenticationFailureException;
import com.gt.repository.RoleRepository;
import com.gt.repository.UserRepository;
import com.gt.service.AuthService;
import com.gt.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public Map<String, Object> register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new AuthenticationFailureException("Email already exists");
        }

        Role role = roleRepository.findByName(request.getRole())
                .orElseThrow(() -> new AuthenticationFailureException("Invalid role: " + request.getRole()));

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);
        user.setMobileNo(request.getMobileNo());
        user.setAddress(request.getAddress());
        user.setStatus(request.getStatus());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        String token = jwtTokenUtil.generateToken(user.getEmail(), role.getName());

        Map<String, Object> data = new HashMap<>();
        data.put("id", user.getId());
        data.put("email", user.getEmail());
        data.put("role", role.getName());
        data.put("token", token);

        return data;
    }

    @Override
    public Map<String, Object> login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AuthenticationFailureException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new AuthenticationFailureException("Invalid email or password");
        }

        String token = jwtTokenUtil.generateToken(user.getEmail(), user.getRole().getName());

        Map<String, Object> data = new HashMap<>();
        data.put("id", user.getId());
        data.put("email", user.getEmail());
        data.put("role", user.getRole().getName());
        data.put("token", token);

        return data;
    }
}
