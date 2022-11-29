package com.backend.app.foodbook.auth.controller;

import com.backend.app.foodbook.auth.dto.AuthDto;
import com.backend.app.foodbook.auth.dto.RegisterDto;
import com.backend.app.foodbook.auth.entity.User;
import com.backend.app.foodbook.auth.repository.UserRepository;
import com.backend.app.foodbook.auth.service.JwtService;
import com.backend.app.foodbook.auth.service.UserService;
import com.backend.app.foodbook.role.repository.RoleRepository;
import com.backend.app.foodbook.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
//@ComponentScan("com.backend.app.foodbook.utils.JwtUtil")
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private RegisterDto registerDto;

    private User registeredUser1;

    private User registeredUser2;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @Mock
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
//        userService = new UserService(userRepository, passwordEncoder, jwtService, jwtUtil, roleRepository);
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
    @DisplayName("User register controller")
    void itShouldRegisterUser() throws Exception {
        AuthDto registerResponse = AuthDto.builder().message("User registered").token("Token").build();
        when(userService.userRegister(registerDto)).thenReturn(registerResponse);
        mockMvc.perform(post("/api/auth/register")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDto)))
                .andExpect(status().isOk());
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