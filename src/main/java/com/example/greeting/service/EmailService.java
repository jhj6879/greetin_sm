package com.example.greeting.service;

import com.example.greeting.dto.EmailDto;
import com.example.greeting.dto.EmployeeDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmailService {

    @Autowired
    private final JavaMailSender javaMailSender;
//    private final MemberDao dao;
    private final String FROM_MAIL_KAKAO = "guswjd6879@kakao.com";
    private final String FROM_MAIL_NAVER = "soar7942@naver.com";
    private final String FROM_MAIL_GOOGLE = "jo39382355@gmail.com";

    @Async
    public boolean sendMailReject(EmailDto mail){
        boolean msg = true;
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(mail.getTo());
            helper.setSubject(mail.getSubject());
            helper.setFrom(FROM_MAIL_GOOGLE);
            helper.setText(mail.getText(), true);

            javaMailSender.send(message);
        } catch(MessagingException e) {
            e.printStackTrace();
            msg = false;
        }
        return msg;
    }

//    public boolean makeMasgTmpPw(EmployeeDto dto) {
//        boolean result = false;
//        EmailDto mail = new EmailDto();
//        mail.setSubject("임시 비밀번호를 발행했습니다");
//        mail.setTo(dto.getUseremail());
//
//        String txt = "로그인을 위한 임시 비밀번호를 발행했습니다.<br><br>"
//                + "임시 비밀번호 :" + dto.getUserpwd() + " <br> <br>"
//                + "로그인 후 반드시 비밀번호 변경을 해주세요.<br>";
//
//        mail.setText(txt);
//
//        result = sendMailReject(mail);
//
//        return result;
//    }
}
