package com.backend.app.foodbook.data;

import com.backend.app.foodbook.auth.entity.User;
import com.backend.app.foodbook.auth.repository.UserRepository;
import com.backend.app.foodbook.business.entity.Business;
import com.backend.app.foodbook.business.repository.BusinessRepository;
import com.backend.app.foodbook.exception.NotFoundException;
import com.backend.app.foodbook.role.entity.Role;
import com.backend.app.foodbook.role.repository.RoleRepository;
import com.backend.app.foodbook.role.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SeedData implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    @Autowired
    private BusinessRepository businessRepository;

    @Override
    public void run(String... args) throws Exception {
        seedUsersAndRole();
    }

    public void seedUsersAndRole() throws NotFoundException {
//        Role user = new Role(null, "USER");
        Role user = Role.builder()
                .name("USER")
                .build();
        Role vendor = Role.builder()
                .name("VENDOR")
                .build();
//        Role vendor = new Role(null, "VENDOR");
        Role admin = Role.builder()
                .name("ADMIN")
                .build();
//        Role admin = new Role(null, "ADMIN");

        Role roleUser = roleRepository.save(user);
        Role roleVendor = roleRepository.save(vendor);
        Role roleAdmin = roleRepository.save(admin);

        Business business1 = new Business(
                null,
                "Meze Fresh",
                "Mexican Restaurant in Kigali",
                "mezefresh@gmail.com",
                "07887878787",
                null,
                null
        );

        Business business = businessRepository.save(business1);

        User user1 = new User(
                null,
                "John",
                "Doe",
                "john@gmail.com",
                passwordEncoder.encode("#Password123"),
                "0787857036",
                null,
                null
        );
        User savedUser1 = userRepository.save(user1);
        savedUser1.setRoles(List.of(roleUser));
        userRepository.save(savedUser1);

        User user2 = new User(
                null,
                "Sam",
                "Patrick",
                "patrick@gmail.com",
                passwordEncoder.encode("#Password123"),
                "0787857036",
                null,
                List.of(business)
        );
        User savedUser2 = userRepository.save(user2);
        savedUser2.setRoles(List.of(roleUser, roleVendor));
        userRepository.save(savedUser2);

        User user3 = new User(
                null,
                "Jane",
                "Angel",
                "jane@gmail.com",
                passwordEncoder.encode("#Password123"),
                "0787857036",
                null,
                null
        );

        User savedUser3 = userRepository.save(user3);
        savedUser3.setRoles(List.of(roleUser, roleVendor, roleAdmin));
        userRepository.save(savedUser3);
    }
}

