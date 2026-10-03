package com.example.th2soa.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.th2soa.dto.LoginRequest;
import com.example.th2soa.service.AuthService;

/** Router: dang nhap + xac thuc token. */
@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /** POST localhost:8081/ (hoac /login) -> kiem tra tai khoan, tra ve JWT */
    @PostMapping({"/", "/login"})
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        if (req.getUserName() == null || req.getPassword() == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Thieu userName hoac password"));
        }
        return authService.login(req.getUserName(), req.getPassword())
                .<ResponseEntity<?>>map(token -> ResponseEntity.ok(
                        Map.of("message", "Dang nhap thanh cong", "token", token)))
                .orElseGet(() -> ResponseEntity.status(401)
                        .body(Map.of("message", "Sai username hoac password")));
    }

    /** GET localhost:8081/auth -> xac thuc token (AuthFilter da kiem tra truoc do) */
    @GetMapping("/auth")
    public Map<String, Object> auth(@RequestAttribute("userName") String userName) {
        return Map.of("valid", true, "userName", userName);
    }
}