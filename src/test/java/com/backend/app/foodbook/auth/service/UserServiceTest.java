package com.backend.app.foodbook.auth.service;

import com.backend.app.foodbook.auth.dto.AuthDto;
import com.backend.app.foodbook.auth.dto.LoginDto;
import com.backend.app.foodbook.auth.dto.RegisterDto;
import com.backend.app.foodbook.auth.entity.User;
import com.backend.app.foodbook.auth.exception.UserAuthException;
import com.backend.app.foodbook.auth.exception.UserExistsException;
import com.backend.app.foodbook.auth.repository.UserRepository;
import com.backend.app.foodbook.exception.NotFoundException;
import com.backend.app.foodbook.role.entity.Role;
import com.backend.app.foodbook.role.repository.RoleRepository;
import com.backend.app.foodbook.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    RegisterDto registerDto;
    User registeredUser;
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

    @Autowired
    @InjectMocks
    private UserService userServiceTest;

    private LoginDto userLogin;

    @BeforeEach
    void setUp() {
        registerDto = RegisterDto.builder()
                .firstName("ishimwe")
                .lastName("gabin")
                .email("gabin@gmail.com")
                .password("#Password123")
                .contactNumber("078783470123")
                .build();

        registeredUser = User.builder()
                .firstName(registerDto.getFirstName())
                .lastName(registerDto.getLastName())
                .email(registerDto.getEmail())
                .password(registerDto.getPassword())
                .contactNumber(registerDto.getContactNumber())
                .build();

        userLogin = LoginDto.builder()
                .email("gabin@gmail.com")
                .password("#Password")
                .build();
    }

    @Test
    @DisplayName("Unit test for user registration")
    void itShouldTestUserRegister() throws UserExistsException, NotFoundException {

        Role roleUser = Role.builder()
                .name("USER")
                .build();

        // arrange
        when(userRepository.findByEmail(anyString())).thenReturn(null);

        when(roleRepository.findByName(anyString())).thenReturn(roleUser);

        when(userRepository.save(any(User.class))).thenReturn(null);

        // act
        AuthDto registerResponse = userServiceTest.userRegister(registerDto);

        // assert
        assertThat("User successfully registered").isEqualTo(registerResponse.getMessage());
    }

    @Test
    @DisplayName("Unit test to catch user error")
    void itShouldNotRegisterWhoExists() throws UserExistsException, NotFoundException {
        // arrange

        when(userRepository.findByEmail(anyString())).thenReturn(registeredUser);

        // act
        // assert
        assertThatThrownBy(() -> userServiceTest.userRegister(registerDto))
                .isInstanceOf(UserExistsException.class)
                .hasMessage("User already exists");
    }

    @Test
    @DisplayName("Unit test to catch role error")
    void itShouldNotRegisterwithoutRole() throws UserExistsException, NotFoundException {
        // arrange
        when(userRepository.findByEmail(anyString())).thenReturn(null);
        when(roleRepository.findByName(anyString())).thenReturn(null);

        // act
        // assert
        assertThatThrownBy(() -> userServiceTest.userRegister(registerDto))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("User role not found");
    }

    @Test
    @DisplayName("Unit test to get all users")
    void itShouldTestGetAllUsers() {
        // when
        userServiceTest.getAllUsers();
        // then
        verify(userRepository).findAll();
    }

    @Test
    @DisplayName("Unit test for user login")
    void itShouldTestUserLogin() throws NotFoundException, UserAuthException {
        // arrange
        when(userRepository.findByEmail(anyString())).thenReturn(registeredUser);
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        // act
        AuthDto successLogin = userServiceTest.userLogin(userLogin);
        // assert
        assertThat(successLogin).isInstanceOf(AuthDto.class);
    }

    @Test
    @DisplayName("Unit test for invalid login error")
    void itShouldTestLoginError() throws NotFoundException, UserAuthException {
        // arrange
        when(userRepository.findByEmail(anyString())).thenReturn(registeredUser);
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        // act
        // assert
        assertThatThrownBy(() -> userServiceTest.userLogin(userLogin)).isInstanceOf(UserAuthException.class).hasMessage("Invalid Credential, Try again");
    }

    @Test
    @DisplayName("Unit test for invalid login error")
    void itShouldTestAuthException() throws NotFoundException, UserAuthException {
        // arrange
        when(userRepository.findByEmail(anyString())).thenReturn(null);
//        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        // act
        // assert
        assertThatThrownBy(() -> userServiceTest.userLogin(userLogin)).isInstanceOf(UserAuthException.class).hasMessage("Invalid Credential, Try again");
    }
}