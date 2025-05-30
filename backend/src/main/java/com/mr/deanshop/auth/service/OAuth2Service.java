package com.mr.deanshop.auth.service;

import com.mr.deanshop.auth.entity.User;
import com.mr.deanshop.auth.repository.UserDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class OAuth2Service {

   @Autowired
   private UserDetailRepository userDetailRepository;

   @Autowired
   private AuthorityService authorityService;

    public User getUser(String userName){
        return userDetailRepository.findByEmail(userName);
    }

    public User createUser(OAuth2User oAuth2User) {
        String name = oAuth2User.getAttribute("name");
        String email = oAuth2User.getAttribute("email");
        User user = User.builder()
                .name(name)
                .email(email)
                .enabled(true)
                .authorities(authorityService.getUserAuthority())
                .build();
        return userDetailRepository.save(user);
    }


}
