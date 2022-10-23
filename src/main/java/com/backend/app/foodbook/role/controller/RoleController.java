package com.backend.app.foodbook.role.controller;

import com.backend.app.foodbook.auth.entity.User;
import com.backend.app.foodbook.exception.NotFoundException;
import com.backend.app.foodbook.role.service.RoleService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/role")
@Api(tags = "Role")
public class RoleController {
    private final RoleService roleService;

    @Autowired
    RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping(path = "forAdmin")
    @PreAuthorize("hasRole('ADMIN')")
    public String getRoles() {
        return "Accessible for admin only";
    }

    @GetMapping(path = "forUser")
    @PreAuthorize("hasRole('USER')" + "&& hasRole('ADMIN')")
    public String forUser() {
        return "Accessible for user only";
    }

    @GetMapping(path = "forVendor")
    @PreAuthorize("hasRole('VENDOR')")
    public String forVendor() {
        return "Accessible for vendor only";
    }


    @PostMapping()
    public User assignRole(@RequestParam("userId") Long userId, @RequestParam("roleId") Long roleId) throws NotFoundException {
        return roleService.addRolePermissions(userId, roleId);
    }


}
