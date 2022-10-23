package com.backend.app.foodbook.auth.controller;

import com.backend.app.foodbook.auth.dto.AuthDto;
import com.backend.app.foodbook.auth.dto.LoginDto;
import com.backend.app.foodbook.auth.dto.RegisterDto;
import com.backend.app.foodbook.auth.entity.User;
import com.backend.app.foodbook.auth.exception.UserAuthException;
import com.backend.app.foodbook.auth.exception.UserExistsException;
import com.backend.app.foodbook.auth.service.JwtService;
import com.backend.app.foodbook.auth.service.UserService;
import com.backend.app.foodbook.exception.NotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/auth")
@Api(tags = "Authentication")
public class AuthController {

    private final UserService userService;

    private final JwtService jwtService;

    @Autowired
    AuthController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping(path = "/register")
    @ApiOperation(
            value = "User register",
            notes = "User registration in our application",
            response = AuthDto.class
    )
    public AuthDto authRegister(@RequestBody @Valid RegisterDto registerDto) throws UserExistsException, NotFoundException {
        return userService.userRegister(registerDto);
    }

    @PostMapping(path = "/login")
    @ApiOperation(
            value = "User login",
            notes = "User login authentication in our application",
            response = AuthDto.class
    )
    public AuthDto authLogin(@RequestBody @Valid LoginDto loginDto) throws NotFoundException, UserAuthException {
        return userService.userLogin(loginDto);
    }

    @GetMapping
    @ApiOperation(
            value = "Get All App Users",
            notes = "Api to get all users using our application",
            response = User.class
    )
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}



