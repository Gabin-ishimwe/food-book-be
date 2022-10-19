package com.backend.app.foodbook.role.data;

import com.backend.app.foodbook.role.entity.Role;
import com.backend.app.foodbook.role.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleSeed implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        seedRole();
    }

    public void seedRole() {
        Role user = new Role(null, "USER");
        Role vendor = new Role(null, "VENDOR");
        Role admin = new Role(null, "ADMIN");

        roleRepository.save(user);
        roleRepository.save(vendor);
        roleRepository.save(admin);
    }
}
