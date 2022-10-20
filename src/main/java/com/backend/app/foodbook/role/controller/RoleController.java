package com.backend.app.foodbook.role.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleController {
    @GetMapping
    public String getRoles() {
        return "Users, admin, vendor";
    }
}
