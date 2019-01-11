package com.training.sportbetting.service;

import com.training.sportbetting.repository.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class SBAUserDetailService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public SBAUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        var user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with email %s not found.", email)));

        return new org.springframework.security.core.userdetails.User
                (user.getEmail(), user.getPassword(), Collections.emptyList());
    }
}
