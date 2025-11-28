package com.example.cnidiomchain.service;

import com.example.cnidiomchain.dto.UserRegisterRequest;
import com.example.cnidiomchain.entity.User;

public interface UserService {

    User register(UserRegisterRequest request);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);
}