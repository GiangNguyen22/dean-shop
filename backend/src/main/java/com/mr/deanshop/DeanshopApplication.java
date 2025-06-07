package com.mr.deanshop;

import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

}
