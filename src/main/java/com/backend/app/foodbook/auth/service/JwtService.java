package com.backend.app.foodbook.auth.service;

import com.backend.app.foodbook.auth.entity.User;
import com.backend.app.foodbook.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JwtService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if(user != null) {
            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    authorities(user)
            );
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    private List<SimpleGrantedAuthority> authorities(User user) {
        List<SimpleGrantedAuthority> auths = new ArrayList<>();
        System.out.println(user.getRoles() + " user roless");
        user.getRoles().forEach(role -> {
            auths.add(new SimpleGrantedAuthority( "ROLE_"+ role.getName()));
        });

        return auths;
    }
}
