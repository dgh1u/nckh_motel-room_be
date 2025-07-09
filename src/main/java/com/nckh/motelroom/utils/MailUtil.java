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
            mimeMessage.setHeader("Content-Type", "text/html; charset=UTF-8");
            mimeMessage.setHeader("Content-Transfer-Encoding", "quoted-printable");
            MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,true, "UTF-8");
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject("VNUA Services - Xác thực OTP");
            mimeMessageHelper.setText("""
        <div style="font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto; padding: 20px;">
            <div style="text-align: center; margin-bottom: 30px;">
                <h2 style="color: #2c5aa0; margin: 0;">VNUA Services</h2>
                <p style="color: #666; margin: 5px 0 0 0;">Kênh thông tin dịch vụ khu vực Học viện Nông nghiệp Việt Nam</p>
            </div>
            
            <div style="background-color: #f8f9fa; border-radius: 8px; padding: 25px; text-align: center;">
                <h3 style="color: #333; margin-top: 0;">Xác thực tài khoản</h3>
                <p style="color: #666; margin-bottom: 20px;">Mã OTP của bạn là:</p>
                
                <div style="background-color: #fff; border: 2px solid #2c5aa0; border-radius: 6px; padding: 15px; margin: 20px 0; display: inline-block;">
                    <span style="font-size: 24px; font-weight: bold; color: #2c5aa0; letter-spacing: 3px;">%s</span>
                </div>
                
                <p style="color: #666; font-size: 14px; margin-top: 20px;">
                    Mã OTP có hiệu lực trong <strong>2 phút</strong>
                </p>
            </div>
            
            <div style="margin-top: 30px; text-align: center; color: #999; font-size: 12px;">
                <p>Nếu bạn không yêu cầu mã này, vui lòng bỏ qua email này.</p>
                <p>© 2025 VNUA Services. All rights reserved.</p>
            </div>
        </div>
        """.formatted(otp), true);
            javaMailSender.send(mimeMessage);
        }catch (Exception e){
            throw new EmailException(e.getMessage());
        }
    }
}
