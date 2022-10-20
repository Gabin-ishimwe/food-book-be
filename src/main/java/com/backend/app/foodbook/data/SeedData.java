package com.backend.app.foodbook.data;

import com.backend.app.foodbook.auth.entity.User;
import com.backend.app.foodbook.auth.repository.UserRepository;
import com.backend.app.foodbook.role.entity.Role;
import com.backend.app.foodbook.role.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SeedData implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        seedUsersAndRole();
    }

    public void seedUsersAndRole() {
        Role user = new Role(null, "USER");
        Role vendor = new Role(null, "VENDOR");
        Role admin = new Role(null, "ADMIN");

        Role roleUser = roleRepository.save(user);
        Role roleVendor = roleRepository.save(vendor);
        Role roleAdmin = roleRepository.save(admin);

        User user1 = new User(
                null,
                "John",
                "Doe",
                "john@gmail.com",
                passwordEncoder.encode("#Password123"),
                "0787857036",
                List.of(roleUser),
                null
        );

        User user2 = new User(
                null,
                "Sam",
                "Patrick",
                "patrick@gmail.com",
                passwordEncoder.encode("#Password123"),
                "0787857036",
                List.of(roleUser, roleVendor),
                null
        );

        User user3 = new User(
                null,
                "Jane",
                "Angel",
                "jane@gmail.com",
                passwordEncoder.encode("#Password123"),
                "0787857036",
                List.of(roleUser, roleAdmin),
                null
        );

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
    }
}

