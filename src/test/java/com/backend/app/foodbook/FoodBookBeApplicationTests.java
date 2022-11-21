package com.backend.app.foodbook;

import com.backend.app.foodbook.auth.controller.AuthController;
import com.backend.app.foodbook.auth.dto.AuthDto;
import com.backend.app.foodbook.auth.dto.LoginDto;
import com.backend.app.foodbook.auth.dto.RegisterDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FoodBookBeApplicationTests {

    private static RestTemplate restTemplate;
    private String baseUrl = "http://localhost:";
    @LocalServerPort
    private int port;

    @Autowired
    private AuthController authController;

    @BeforeAll
    static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    void setUp() {
        baseUrl = baseUrl.concat(port + "");
    }

    @Test
    void contextLoads() {
    }

    @Test
    void getAllUser() {
        var users = restTemplate.getForObject(baseUrl.concat("/api/auth"), List.class);
        assert users != null;
        assertThat(users.size()).isEqualTo(3); // tested on seed data
    }

    @Test
    void loginUser() {
        LoginDto loginDto = LoginDto.builder()
                .email("jane@gmail.com")
                .password("#Password123")
                .build();

        var login = restTemplate.postForObject(baseUrl.concat("/api/auth/login"), loginDto, AuthDto.class);
        System.out.println(login);
        assert login != null;
        assertThat(login.getMessage()).isEqualTo("User logged in successfully");
    }

    @Test
    void registerUser() {
        RegisterDto registerDto = RegisterDto.builder()
                .firstName("ishimwe")
                .lastName("gabin")
                .email("gabin@gmail.com")
                .password("#Password123")
                .contactNumber("078783470123")
                .build();

        var register = restTemplate.postForObject(baseUrl.concat("/api/auth/register"), registerDto, AuthDto.class);
        assert register != null;
        assertThat(register.getMessage()).isEqualTo("User successfully registered");

    }

}
