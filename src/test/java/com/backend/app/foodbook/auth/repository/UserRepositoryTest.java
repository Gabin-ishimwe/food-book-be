package com.backend.app.foodbook.auth.repository;

import com.backend.app.foodbook.auth.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByEmail() {
        User testUser = User.builder()
                .firstName("gabin")
                .lastName("ishimwe")
                .email("gabin@gmail.com")
                .password("#Password123")
                .contactNumber("0784382342")
                .businesses(null)
                .roles(null)
                .build();
        var createdUser = userRepository.save(testUser);
        var findUser = userRepository.findByEmail(createdUser.getEmail());
        assertThat(findUser.getEmail()).isEqualTo(createdUser.getEmail());
    }
}