package com.example.cnidiomchain.service.impl;

import com.example.cnidiomchain.dto.UserRegisterRequest;
import com.example.cnidiomchain.entity.User;
import com.example.cnidiomchain.repository.UserRepository;
import com.example.cnidiomchain.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    @Transactional
    public User register(UserRegisterRequest request) {
        // 检查用户名是否已存在
        if (existsByUsername(request.getUsername())) {
            throw new RuntimeException("用户名已存在");
        }

        // 检查邮箱是否已存在
        if (existsByEmail(request.getEmail())) {
            throw new RuntimeException("邮箱已存在");
        }

        // 检查手机号是否已存在
        if (existsByPhone(request.getPhone())) {
            throw new RuntimeException("手机号已存在");
        }

        // 创建用户对象
        User user = new User();
        BeanUtils.copyProperties(request, user);

        // 密码加密
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // 保存用户
        return userRepository.save(user);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByPhone(String phone) {
        return userRepository.existsByPhone(phone);
    }
}