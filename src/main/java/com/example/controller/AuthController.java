package com.example.controller;

import com.example.dto.extra.Auth;
import com.example.dto.extra.Registration;
import com.example.enums.AppLanguage;
import com.example.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "Authorization Api list", description = "Api list for Authorization")
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    @Operation( summary = "Api for login", description = "this api used for authorization")
    public ResponseEntity<?> login(@RequestBody Auth auth,
                                   @RequestHeader(value = "Accept-Language",defaultValue = "UZ") AppLanguage language) {
        log.info("Login {} ", auth.getEmail());
        return ResponseEntity.ok(authService.login(auth,language));
    }
    @PostMapping("/registration")
    @Operation( summary = "Api for registration", description = "this api used for authorization")
    public ResponseEntity<?> registartion( @Valid @RequestBody Registration registration,
                                           @RequestHeader(value = "Accept-Language",defaultValue = "UZ") AppLanguage language) {
        log.info("registration {}", registration.getEmail());
        return ResponseEntity.ok(authService.registration(registration,language));
    }







}
