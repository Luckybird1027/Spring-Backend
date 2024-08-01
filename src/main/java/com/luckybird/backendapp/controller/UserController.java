package com.luckybird.backendapp.controller;

import com.luckybird.backendapp.dto.UserRegistrationDto;
import com.luckybird.backendapp.entity.User;
import com.luckybird.backendapp.exception.ExceptionMessages;
import com.luckybird.backendapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 新云鸟
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegistrationDto userRegistrationDto) {
        try {
            User createdUser = userService.register(userRegistrationDto);
            return ResponseEntity.ok().body("User " + createdUser.getUsername() + " registered successfully");
        } catch (Exception e) {
            if (e.getMessage().contains(ExceptionMessages.USERNAME_ALREADY_EXISTS)) {
                return ResponseEntity.badRequest().body(ExceptionMessages.USERNAME_ALREADY_EXISTS);
            } else {
                return ResponseEntity.internalServerError().body("Internal server error");
            }
        }
    }
}
