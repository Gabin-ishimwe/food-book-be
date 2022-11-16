package com.backend.app.foodbook.role.repository;

import com.backend.app.foodbook.role.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void itShouldFindByName() {
        Role role = Role.builder()
                .name("USER")
                .build();
        var createRole = roleRepository.save(role);
        var findRole = roleRepository.findByName(createRole.getName());
        assertThat(findRole.getName()).isEqualTo(createRole.getName());
    }
}