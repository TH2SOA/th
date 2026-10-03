package com.example.th2soa.middleware;

import java.io.IOException;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.th2soa.entity.User;
import com.example.th2soa.service.AuthService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/** Middleware: xac thuc token (Authorization: Bearer <token>) truoc khi vao router. */
@Component
public class AuthFilter extends OncePerRequestFilter {

    private final AuthService authService;

    public AuthFilter(AuthService authService) {
        this.authService = authService;
    }

    /** Dang nhap (POST / hoac /login) va /error khong can token. */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        boolean isLogin = "POST".equals(request.getMethod())
                && (path.equals("/") || path.equals("/login"));
        return isLogin || path.equals("/error");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            reject(response, "Chua co token");
            return;
        }

        Optional<User> user = authService.authenticate(header.substring(7));
        if (user.isEmpty()) {
            reject(response, "Token khong hop le hoac da het han");
            return;
        }

        request.setAttribute("userName", user.get().getUserName());
        chain.doFilter(request, response);
    }

    private void reject(HttpServletResponse response, String message) throws IOException {
        response.setStatus(401);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"message\":\"" + message + "\"}");
    }
}