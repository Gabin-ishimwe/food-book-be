package com.backend.app.foodbook.auth.controller;

import com.backend.app.foodbook.auth.dto.AuthDto;
import com.backend.app.foodbook.auth.dto.LoginDto;
import com.backend.app.foodbook.auth.dto.RegisterDto;
import com.backend.app.foodbook.auth.exception.UserExistsException;
import com.backend.app.foodbook.auth.service.UserService;
import com.backend.app.foodbook.exception.NotFoundException;
import com.backend.app.foodbook.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/auth")
public class AuthController {

    private final UserService userService;

    private final JwtService jwtService;

    @Autowired
    AuthController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @GetMapping()
    public String get() {
        return "testing route";
    }

    @PostMapping(path = "/register")
    public AuthDto authRegister(@RequestBody @Valid RegisterDto registerDto) throws UserExistsException, NotFoundException {
        return userService.userRegister(registerDto);
    }

    @PostMapping(path = "/login")
    public AuthDto authLogin(@RequestBody @Valid LoginDto loginDto) {
        return null;
    }
}



