package com.backend.app.foodbook.business.service;

import com.backend.app.foodbook.auth.repository.UserRepository;
import com.backend.app.foodbook.business.repository.BusinessRepository;
import com.backend.app.foodbook.utils.JwtUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BussinessServiceTest {

    @Mock
    private BusinessRepository businessRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private BussinessService bussinessService;

    @Test
    @DisplayName("Test creating business")
    void createBusiness() {
    }
}