package com.mr.deanshop.auth.service;

import com.mr.deanshop.auth.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String sender;

    public String sendEmail(User user){
        String subject = "Verify your email";
        String senderName = "DeanShop";
        String mailContent = "Hi "+ user.getName() + "\n";
        mailContent += "Your verification code is " + user.getVerificationCode() + "\n";
        mailContent += "Please enter this code to verify your email";
        mailContent += "\n";
        mailContent += senderName;
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject(subject);
            message.setFrom(sender);
            message.setTo(user.getEmail());
            message.setText(mailContent);
            mailSender.send(message);
        }catch(Exception e){
            return "Error while sending email";
        }
        return "Email sent";

    }
}
