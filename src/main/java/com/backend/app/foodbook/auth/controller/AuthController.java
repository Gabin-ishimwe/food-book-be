package com.backend.app.foodbook.auth.controller;

import com.backend.app.foodbook.auth.dto.RegisterDto;
import com.backend.app.foodbook.auth.exception.UserExistsException;
import com.backend.app.foodbook.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/auth")
public class AuthController {

    private final UserService userService;

    @Autowired
    AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "")
    public String get() {
        return "testing route";
    }

    @PostMapping(path = "/register")
    public String authRegister(@RequestBody @Valid RegisterDto registerDto) throws UserExistsException {
        userService.userRegister(registerDto);
        return "User created";
    }
}



