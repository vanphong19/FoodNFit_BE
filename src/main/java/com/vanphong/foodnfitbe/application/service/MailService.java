package com.vanphong.foodnfitbe.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender mailSender;
    public void sendOtp(String to, String otp){
        var msg = new SimpleMailMessage();
        msg.setTo(to);
        msg.setSubject("Xác minh otp");
        msg.setText("Mã otp của bạn là: "+ otp +" hiệu lực 5 phút");
        mailSender.send(msg);
    }
}
