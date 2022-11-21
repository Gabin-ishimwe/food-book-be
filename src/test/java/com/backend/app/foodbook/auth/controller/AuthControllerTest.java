package com.backend.app.foodbook.auth.controller;

import com.backend.app.foodbook.auth.dto.RegisterDto;
import com.backend.app.foodbook.auth.entity.User;
import com.backend.app.foodbook.auth.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private UserService userService;

    private RegisterDto registerDto;

    private User registeredUser1;

    private User registeredUser2;

//    @Mock
//    private UserRepository userRepository;
//    @Mock
//    private RoleRepository roleRepository;
//    @Mock
//    private PasswordEncoder passwordEncoder;
//    @Mock
//    private JwtService jwtService;
//    @Mock
//    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        registerDto = RegisterDto.builder()
                .firstName("ishimwe")
                .lastName("gabin")
                .email("gabin@gmail.com")
                .password("#Password123")
                .contactNumber("078783470123")
                .build();

        registeredUser1 = User.builder()
                .firstName(registerDto.getFirstName())
                .lastName(registerDto.getLastName())
                .email(registerDto.getEmail())
                .password(registerDto.getPassword())
                .contactNumber(registerDto.getContactNumber())
                .build();

        registeredUser2 = User.builder()
                .firstName("axcel")
                .lastName("ngabo")
                .email("ngabo@gmail.com")
                .password("#Password123")
                .contactNumber("078783470123")
                .build();
    }

    @Test
    @DisplayName("Test user register controller")
    void itShouldTestUserRegister() throws Exception {
        List<User> allUsers = Arrays.asList(registeredUser1, registeredUser2);
        // arrange
        when(userService.getAllUsers()).thenReturn(allUsers);

        // act & assert
        mockMvc.perform(get("/api/auth"))
                .andExpect(jsonPath("$.size()", is(allUsers.size())))
                .andDo(print());
    }

//    @Test
//    void authLogin() {
//    }
//
//    @Test
//    void getAllUsers() {
//    }
}