package com.mr.deanshop;

import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
public class DeanshopApplication {

	@Value("${stripe.secret}")
	private String stripeSecret;


	public static void main(String[] args) {
		SpringApplication.run(DeanshopApplication.class, args);
	}

	@PostConstruct
	public void init(){
		Stripe.apiKey = this.stripeSecret;
	}

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();

		config.setAllowedOriginPatterns(Collections.singletonList("*"));
		config.setAllowCredentials(true); // ⚠cần thiết khi dùng session/cookie

		config.setAllowedOrigins(List.of("http://localhost:3000"));
		config.setAllowedMethods(List.of("*"));
		config.setAllowedHeaders(List.of("*"));

		config.setExposedHeaders(List.of("*"));

		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}


}
