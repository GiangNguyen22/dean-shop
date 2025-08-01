package com.mr.deanshop.auth.service;

import com.mr.deanshop.auth.dto.RegistrationRequest;
import com.mr.deanshop.auth.dto.RegistrationResponse;
import com.mr.deanshop.auth.dto.UserDetailDto;
import com.mr.deanshop.auth.entity.Authority;
import com.mr.deanshop.auth.entity.User;
import com.mr.deanshop.auth.helper.VerificationCodeGenerator;
import com.mr.deanshop.auth.repository.UserDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerErrorException;

import java.util.List;

@Service
public class RegistrationService {
    @Autowired
    private UserDetailRepository userDetailRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthorityService authorityService;


    public RegistrationResponse createUser(RegistrationRequest request) {
        //check user tồn tại
        User existing = userDetailRepository.findByEmail(request.getEmail());
        if (existing != null) {
            return RegistrationResponse.builder()
                    .code(400)
                    .message("Email already exist!")
                    .build();
        }

        try{
            User user =  new User();
            user.setName(request.getName());
            user.setEmail(request.getEmail());
            user.setPhone(request.getPhone());
            user.setEnabled(false);
            user.setPassword(passwordEncoder.encode(request.getPassword()));

            String code = VerificationCodeGenerator.generateCode();
            user.setVerificationCode(code);
            user.setAuthorities(authorityService.getUserAuthority());

            emailService.sendEmail(user);

            userDetailRepository.save(user);

            return RegistrationResponse.builder()
                    .code(200)
                    .message("User created!")
                    .build();
        }catch (Exception e){
            System.out.println(e.getMessage());
            throw new ServerErrorException(e.getMessage(), e.getCause());
        }

    }

    public void verifyUser(String email) {
        User user = userDetailRepository.findByEmail(email);
        user.setEnabled(true);
        userDetailRepository.save(user);
    }

    public void updateRole( String email) {
        User user = userDetailRepository.findByEmail(email);
        List<Authority> authority =  authorityService.getUserAuthority();
        Authority authority1 = authorityService.getAdminAuthority();
        authority.add(authority1);
        user.setAuthorities(authority);
        userDetailRepository.save(user);

    }
}
