package com.backend.app.foodbook.role.controller;

import com.backend.app.foodbook.auth.entity.User;
import com.backend.app.foodbook.exception.NotFoundException;
import com.backend.app.foodbook.role.entity.Role;
import com.backend.app.foodbook.role.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/role")
public class RoleController {
    private RoleService roleService;
    @Autowired
    RoleController(RoleService roleService) {
        this.roleService = roleService;
    }
    @GetMapping
//    @PreAuthorize("hasRole('ADMIN')")
    public String getRoles() {
        return "Users, admin, vendor";
    }

    @PostMapping()
    public User assignRole(@RequestParam("userId") Long userId, @RequestParam("roleId") Long roleId) throws NotFoundException {
        return roleService.addRolePermissions(userId, roleId);
    }


}
