package com.backend.app.foodbook.role.service;

import com.backend.app.foodbook.auth.entity.User;
import com.backend.app.foodbook.auth.repository.UserRepository;
import com.backend.app.foodbook.exception.NotFoundException;
import com.backend.app.foodbook.role.dto.ResponseDto;
import com.backend.app.foodbook.role.entity.Role;
import com.backend.app.foodbook.role.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class RoleService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    RoleService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public User addRolePermission(User user, Long id) throws NotFoundException {
        Role findRole = roleRepository.findById(id).orElseThrow(() -> new NotFoundException("Role Not found"));
        List<Role> roles = user.getRoles();
        if (roles == null) {
            user.setRoles(List.of(findRole));
            userRepository.save(user);
            return user;
        }
        List<Role> addingRole = new ArrayList<>(roles);
        addingRole.add(findRole);
        user.setRoles(addingRole);
        userRepository.save(user);
        System.out.println(user);
        return user;
    }

    public ResponseDto addRolePermissions(Long userId, Long id) throws NotFoundException {
        Role findRole = roleRepository.findById(id).orElseThrow(() -> new NotFoundException("Role Not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User Not found"));
        List<Role> roles = user.getRoles();
        if (roles == null) {
            user.setRoles(List.of(findRole));
            userRepository.save(user);
            return new ResponseDto("User role added", HttpStatus.OK);
        }
        for (Role role : roles) {
            if (Objects.equals(role.getName(), findRole.getName())) {
                return new ResponseDto("User arleady have role " + role.getName(), HttpStatus.OK);
            }
        }
        List<Role> addingRole = new ArrayList<>(roles);
        addingRole.add(findRole);
        user.setRoles(addingRole);
        userRepository.save(user);
        return new ResponseDto("User role added", HttpStatus.OK);
    }

    public ResponseDto removeRolePermission(Long userId, Long roleId) throws NotFoundException {
        Role findRole = roleRepository.findById(roleId).orElseThrow(() -> new NotFoundException("Role Not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User Not found"));
        List<Role> roles = user.getRoles();
        for (Role role : roles) {
            if (Objects.equals(role.getName(), findRole.getName())) {
                user.getRoles().remove(findRole);
                userRepository.save(user);
                return new ResponseDto("User role removed " + findRole.getName(), HttpStatus.OK);
            }
            return new ResponseDto("User doesn't have role " + findRole.getName(), HttpStatus.OK);
        }

        return null;
    }

}
