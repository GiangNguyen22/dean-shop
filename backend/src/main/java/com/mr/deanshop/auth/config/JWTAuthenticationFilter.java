package com.mr.deanshop.auth.config;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final JWTTokenHelper jwtTokenHelper;
    private final UserDetailsService userDetailsService;

    public JWTAuthenticationFilter(JWTTokenHelper jwtTokenHelper, UserDetailsService userDetailsService) {
        this.jwtTokenHelper = jwtTokenHelper;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }

        try{
            String authToken = jwtTokenHelper.getToken(request);
            if(authToken != null) {
                String email = jwtTokenHelper.getEmailFromToken(authToken);
                if(email != null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                    if(jwtTokenHelper.validateToken(authToken, userDetails)) {
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        // Thêm thông tin request vào AuthenticationToken (IP, session,...)
                        authenticationToken.setDetails(new WebAuthenticationDetails(request));
                        // Đặt đối tượng Authentication vào SecurityContext (phiên làm việc bảo mật hiện tại)
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }
            }
            filterChain.doFilter(request, response);

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
