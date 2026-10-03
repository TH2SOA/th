package com.example.th2soa.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.th2soa.entity.User;
import com.example.th2soa.repository.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final TokenService tokenService;

    public AuthService(UserRepository userRepository, TokenService tokenService) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    /** Password nhan vao la Base64. Dung -> sinh JWT, luu vao Token, tra ve token. */
    public Optional<String> login(String userName, String passwordBase64) {
        return userRepository.findByUserName(userName)
                .filter(u -> u.getPassword().equals(passwordBase64))
                .map(u -> {
                    String token = tokenService.createToken(u.getUserName());
                    u.setToken(token);
                    userRepository.save(u);
                    return token;
                });
    }

    /** Token hop le khi chu ky/han dung VA trung voi Token dang luu. */
    public Optional<User> authenticate(String token) {
        String userName = tokenService.verifyToken(token);
        if (userName == null) {
            return Optional.empty();
        }
        return userRepository.findByUserName(userName)
                .filter(u -> token.equals(u.getToken()));
    }
}