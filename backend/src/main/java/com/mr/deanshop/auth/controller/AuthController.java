package com.mr.deanshop.auth.controller;

import com.mr.deanshop.auth.config.JWTTokenHelper;
import com.mr.deanshop.auth.dto.LoginRequest;
import com.mr.deanshop.auth.dto.RegistrationRequest;
import com.mr.deanshop.auth.dto.RegistrationResponse;
import com.mr.deanshop.auth.dto.UserToken;
import com.mr.deanshop.auth.entity.Authority;
import com.mr.deanshop.auth.entity.User;
import com.mr.deanshop.auth.service.AuthorityService;
import com.mr.deanshop.auth.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTTokenHelper jwtTokenHelper;
    @Autowired
    private RegistrationService registrationService;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthorityService authorityService;


    @PostMapping("/login")
    public ResponseEntity<UserToken> login(@RequestBody LoginRequest request){
        try{
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
            Authentication authenticatonResponse = this.authenticationManager.authenticate(authenticationToken);

            if(authenticatonResponse.isAuthenticated()){
                User user = (User) authenticatonResponse.getPrincipal();
                if(!user.isEnabled()){
                    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
                }
                // generate token
                String token = jwtTokenHelper.generateToken(user.getEmail());
                UserToken userToken = UserToken.builder().token(token).build();
                return new ResponseEntity<>(userToken, HttpStatus.OK);

            }
        }catch(BadCredentialsException e){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> register(@RequestBody RegistrationRequest request){
            RegistrationResponse registrationResponse = registrationService.createUser(request);
            return new ResponseEntity<>(registrationResponse, registrationResponse.getCode() == 200? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyCode(@RequestBody Map<String, String> map){
        String email = map.get("email");
        String code = map.get("code");

        User user = (User) userDetailsService.loadUserByUsername(email);
        if(user != null && user.getVerificationCode().equals(code)){
            registrationService.verifyUser(email);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/role")
    public ResponseEntity<?> createRole(@RequestBody Authority authority){
        Authority role = authorityService.createAuthority(authority.getRoleCode(), authority.getRoleDescription());
        return new ResponseEntity<>(role, HttpStatus.OK);
    }


}
