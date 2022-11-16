package com.backend.app.foodbook.auth.service;

import com.backend.app.foodbook.auth.dto.AuthDto;
import com.backend.app.foodbook.auth.dto.LoginDto;
import com.backend.app.foodbook.auth.dto.RegisterDto;
import com.backend.app.foodbook.auth.entity.User;
import com.backend.app.foodbook.auth.exception.UserAuthException;
import com.backend.app.foodbook.auth.exception.UserExistsException;
import com.backend.app.foodbook.auth.repository.UserRepository;
import com.backend.app.foodbook.configuration.SimpleAuthManager;
import com.backend.app.foodbook.exception.NotFoundException;
import com.backend.app.foodbook.role.entity.Role;
import com.backend.app.foodbook.role.repository.RoleRepository;
import com.backend.app.foodbook.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final JwtUtil jwtUtil;

    private final RoleRepository roleRepository;

    @Autowired
    UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, JwtUtil jwtUtil, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.jwtUtil = jwtUtil;
        this.roleRepository = roleRepository;
    }

    public AuthDto userRegister(RegisterDto registerDto) throws UserExistsException, NotFoundException {
        User findUser = userRepository.findByEmail(registerDto.getEmail());
        List<Role> roles = new ArrayList<>();
        if (findUser != null) {
            throw new UserExistsException("User already exists");
        }
        Role userRole = roleRepository.findByName("USER");
        if (userRole == null) {
            throw new NotFoundException("User not found");
        }
        roles.add(userRole);
        User user = new User(
                null,
                registerDto.getFirstName(),
                registerDto.getLastName(),
                registerDto.getEmail(),
                passwordEncoder.encode(registerDto.getPassword()),
                registerDto.getContactNumber(),
                roles,
                null
        );

        userRepository.save(user);
        return new AuthDto("User successfully registered", null);
    }

    public AuthDto createJwt(User user) {
        String userEmail = user.getEmail();
        String userPassword = user.getPassword();
        UserDetails userDetails = jwtService.loadUserByUsername(userEmail);
        SimpleAuthManager.authManager(userEmail, userPassword);

        String token = jwtUtil.generateToken(userDetails);

        return new AuthDto("User logged in successfully", token);

    }

    public AuthDto userLogin(LoginDto loginDto) throws NotFoundException, UserAuthException {
        String userEmail = loginDto.getEmail();
        String password = loginDto.getPassword();

        User findUser = userRepository.findByEmail(userEmail);
        if (findUser != null) {
            boolean passwordVerification = passwordEncoder.matches(password, findUser.getPassword());
            if (passwordVerification) {
                return createJwt(findUser);
            } else {
                throw new UserAuthException("Invalid Credential, Try again");
            }
        } else {
            throw new UserAuthException("Invalid Credential, Try again");
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
