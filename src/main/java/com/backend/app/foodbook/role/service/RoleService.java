package com.backend.app.foodbook.role.service;

import com.backend.app.foodbook.auth.entity.User;
import com.backend.app.foodbook.auth.repository.UserRepository;
import com.backend.app.foodbook.exception.NotFoundException;
import com.backend.app.foodbook.role.entity.Role;
import com.backend.app.foodbook.role.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    RoleService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public User addRolePermission(User user, Long id) throws NotFoundException {
        Role findRole = roleRepository.findById(id).orElseThrow(() -> new NotFoundException("Role Not found"));
        List<Role> roles = user.getRoles();
        if(roles == null) {
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

    public User addRolePermissions(Long userId, Long id) throws NotFoundException {
        Role findRole = roleRepository.findById(id).orElseThrow(() -> new NotFoundException("Role Not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User Not found"));
        System.out.println(findRole);
        List<Role> roles = user.getRoles();
        if(roles == null) {
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

}
