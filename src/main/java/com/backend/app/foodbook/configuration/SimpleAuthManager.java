package com.backend.app.foodbook.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.parameters.P;

public class SimpleAuthManager implements AuthenticationManager {
    private String userName;
    private String userPassword;

    SimpleAuthManager(String userName, String userPassword) {
        this.userName = userName;
        this.userPassword = userPassword;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            return new UsernamePasswordAuthenticationToken(userName, userPassword);
        } catch (DisabledException disabledException) {
            throw new DisabledException("User disabled");
        } catch(BadCredentialsException badCredentialsException) {
            throw new BadCredentialsException("Bad credential from user");
        }

    }

    public static SimpleAuthManager authManager(String userName, String password) {
        return new SimpleAuthManager(userName, password);
    }
}
