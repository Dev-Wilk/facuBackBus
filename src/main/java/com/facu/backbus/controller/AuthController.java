package com.facu.backbus.controller;

import com.facu.backbus.dto.LoginRequest;
import com.facu.backbus.dto.LoginResponse;
import com.facu.backbus.model.User;
import com.facu.backbus.repository.UserRepository;
import com.facu.backbus.util.HashUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String hashedPassword = HashUtil.gerarHashSHA256(loginRequest.getPassword());
        Optional<User> optionalUser = userRepository.findByLoginAndPassword(
            loginRequest.getLogin(),
            hashedPassword
        );

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            LoginResponse response = new LoginResponse(
                user.getId(),
                user.getFullName(),
                user.getLogin(),
                user.getUserType()
            );
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body("Login ou senha inválidos");
        }
    }
}
