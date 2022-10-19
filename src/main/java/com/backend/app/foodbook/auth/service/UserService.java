package com.backend.app.foodbook.auth.service;

import com.backend.app.foodbook.auth.dto.AuthRegisterDto;
import com.backend.app.foodbook.auth.dto.RegisterDto;
import com.backend.app.foodbook.auth.entity.User;
import com.backend.app.foodbook.auth.exception.UserExistsException;
import com.backend.app.foodbook.auth.repository.UserRepository;
import com.backend.app.foodbook.configuration.SimpleAuthManager;
import com.backend.app.foodbook.role.entity.Role;
import com.backend.app.foodbook.role.repository.RoleRepository;
import com.backend.app.foodbook.service.JwtService;
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

    private JwtService jwtService;

    private JwtUtil jwtUtil;

    private RoleRepository roleRepository;

    @Autowired
    UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, JwtUtil jwtUtil, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.jwtUtil = jwtUtil;
        this.roleRepository = roleRepository;
    }

    public AuthRegisterDto userRegister(RegisterDto registerDto) throws UserExistsException {
        User findUser = userRepository.findByEmail(registerDto.getEmail());
        List<Role> roles = new ArrayList<>();
        if (findUser != null) {
            throw new UserExistsException("User arleady exists");
        }
        Role userRole = roleRepository.findByName("USER");
        if(userRole == null) {
            System.out.println("Resource not found");
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

        User registeredUser = userRepository.save(user);
        return createJwt(registeredUser);
    }

    public AuthRegisterDto createJwt(User user) {
        String userEmail = user.getEmail();
        String userPassword = user.getPassword();
        UserDetails userDetails = jwtService.loadUserByUsername(userEmail);
        SimpleAuthManager.authManager(userEmail, userPassword);

        String token = jwtUtil.generateToken(userDetails);

        return new AuthRegisterDto("User succesfull registered", token);


    }

}
