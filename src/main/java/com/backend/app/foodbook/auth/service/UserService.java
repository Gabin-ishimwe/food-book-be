package com.backend.app.foodbook.auth.service;

import com.backend.app.foodbook.auth.dto.RegisterDto;
import com.backend.app.foodbook.auth.entity.User;
import com.backend.app.foodbook.auth.exception.UserExistsException;
import com.backend.app.foodbook.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User userRegister(RegisterDto registerDto) throws UserExistsException {
        User findUser = userRepository.findByEmail(registerDto.getEmail());

        if (findUser != null) {
            throw new UserExistsException("User arleady exists");
        }
        User user = new User(
                null,
                registerDto.getFirstName(),
                registerDto.getLastName(),
                registerDto.getEmail(),
                registerDto.getPassword(),
                registerDto.getContactNumber(),
                "ROLE_USER",
                null
        );

        return userRepository.save(user);
    }

}
