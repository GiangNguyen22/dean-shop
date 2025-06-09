package com.mr.deanshop.auth.controller;

import com.mr.deanshop.auth.config.JWTTokenHelper;
import com.mr.deanshop.auth.entity.User;
import com.mr.deanshop.auth.service.OAuth2Service;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/oauth2")
public class OAuth2Controller {
    @Autowired
    OAuth2Service oAuth2Service;

    @Autowired
    JWTTokenHelper jwtTokenHelper;

    @GetMapping("/success")
    public void callBackOAuth2(@AuthenticationPrincipal OAuth2User oauth2User, HttpServletResponse response) throws IOException {
        String userName = oauth2User.getAttribute("email");
        User user = oAuth2Service.getUser(userName);
        if(user == null) {
            user = oAuth2Service.createUser(oauth2User);
        }
        String token = jwtTokenHelper.generateToken(userName);

        response.sendRedirect("http://localhost:3000/oauth2/callback?token=" + token);

    }
}
