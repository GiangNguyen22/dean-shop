package com.mr.deanshop.auth.controller;

import com.mr.deanshop.auth.dto.UserDetailDto;
import com.mr.deanshop.auth.entity.User;
import com.mr.deanshop.auth.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserDetailController {
    private final UserDetailsService userDetailsService;
    @Autowired
    private RegistrationService registrationService;

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
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .authorityList(user.getAuthorities())
                .addressList(user.getAddressList())
                .build();

        return new ResponseEntity<>(userDetailDto, HttpStatus.OK);
    }

    @PutMapping
    public  ResponseEntity<?> updateRole(Principal principal){
        registrationService.updateRole(principal.getName());
        return new ResponseEntity<>( HttpStatus.OK);
    }
}
