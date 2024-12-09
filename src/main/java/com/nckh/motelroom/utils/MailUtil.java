package com.nckh.motelroom.utils;

import com.nckh.motelroom.exception.EmailException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MailUtil {
    private final JavaMailSender javaMailSender;

    public void  sendOtpEmail(String email,String otp){
        try {
            MimeMessage mimeMessage=javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,true);
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject("Xác thực OTP");
            mimeMessageHelper.setText("""
                    Your OTP: <b>%s</b>
                    """.formatted(otp),true);
            javaMailSender.send(mimeMessage);
        }catch (Exception e){
            throw new EmailException(e.getMessage());
        }
    }
}
