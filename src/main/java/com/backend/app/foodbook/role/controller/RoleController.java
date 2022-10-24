package com.backend.app.foodbook.role.controller;

import com.backend.app.foodbook.auth.entity.User;
import com.backend.app.foodbook.exception.NotFoundException;
import com.backend.app.foodbook.role.dto.ResponseDto;
import com.backend.app.foodbook.role.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(
            value = "User role permission",
            notes = "Give user role to access different specified resources",
            response = User.class
    )
    public ResponseDto assignRole(@RequestParam("userId") Long userId, @RequestParam("roleId") Long roleId) throws NotFoundException {
        return roleService.addRolePermissions(userId, roleId);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(
            value = "User role permission",
            notes = "Remove user role to access different specified resources",
            response = User.class
    )
    public ResponseDto removeRolePermission(@RequestParam("userId") Long userId, @RequestParam("roleId") Long roleId) throws NotFoundException {
        return roleService.removeRolePermission(userId, roleId);
    }


}
