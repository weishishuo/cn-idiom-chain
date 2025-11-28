package com.example.cnidiomchain.controller;

import com.example.cnidiomchain.dto.ResponseResult;
import com.example.cnidiomchain.dto.UserRegisterRequest;
import com.example.cnidiomchain.entity.User;
import com.example.cnidiomchain.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseResult<User> register(@Valid @RequestBody UserRegisterRequest request) {
        try {
            User user = userService.register(request);
            return ResponseResult.success("注册成功", user);
        } catch (RuntimeException e) {
            return ResponseResult.error(e.getMessage());
        }
    }
}