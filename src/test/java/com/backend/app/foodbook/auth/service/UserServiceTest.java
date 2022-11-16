package com.backend.app.foodbook.auth.service;

import com.backend.app.foodbook.auth.repository.UserRepository;
import com.backend.app.foodbook.role.repository.RoleRepository;
import com.backend.app.foodbook.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    private JwtService jwtService;

    private JwtUtil jwtUtil;
    private UserService userServiceTest;


    @BeforeEach
    void setUp() {
        userServiceTest = new UserService(userRepository, passwordEncoder, jwtService, jwtUtil, roleRepository);
    }

    @Test
    @Disabled
    void itShouldTestUserRegister() {

    }

    @Test
    void itShouldTestGetAllUsers() {
       // when
       userServiceTest.getAllUsers();
       // then
        verify
    }
}