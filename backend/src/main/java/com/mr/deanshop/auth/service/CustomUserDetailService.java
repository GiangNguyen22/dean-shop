package com.mr.deanshop.auth.service;

import com.mr.deanshop.auth.entity.User;
import com.mr.deanshop.auth.repository.UserDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserDetailRepository userDetailRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDetailRepository.findByEmail(username);
        if(user == null) {
            throw new UsernameNotFoundException("No user found with username: " + username);
        }
        return user;
    }
}
