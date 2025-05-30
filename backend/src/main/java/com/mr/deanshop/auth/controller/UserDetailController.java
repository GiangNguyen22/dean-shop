package com.mr.deanshop.auth.controller;

import com.mr.deanshop.auth.dto.UserDetailDto;
import com.mr.deanshop.auth.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/user")
public class UserDetailController {
    private final UserDetailsService userDetailsService;

    public UserDetailController(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDetailDto> getUserProfile(Principal principal) {
        User user = (User) userDetailsService.loadUserByUsername(principal.getName());

        if(user == null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        UserDetailDto userDetailDto =  UserDetailDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .authorityList(user.getAuthorities())
                .build();

        return new ResponseEntity<>(userDetailDto, HttpStatus.OK);
    }
}
