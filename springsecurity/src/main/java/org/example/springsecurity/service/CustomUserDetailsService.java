package org.example.springsecurity.service;

import org.example.springsecurity.dto.CustomUserDetails;
import org.example.springsecurity.entity.UserEntity;
import org.example.springsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userData = userRepository.findByUsername(username);

        if(username != null) {
            return new CustomUserDetails(userData);
        }

        return null;
    }
}
