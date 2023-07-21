package team7.module.sercive;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import team7.module.domain.user.User;


import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public boolean sendSimpleText(User userMail) {

        List<String> receivers = new ArrayList<>();

        receivers.add(userMail.getEmail());

        String[] arrReceiver = (String[])receivers.toArray(new String[receivers.size()]);


        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setTo(arrReceiver);
        simpleMailMessage.setSubject("회원 등급 변경 알림");
        simpleMailMessage.setText(userMail.getName() + "님의 사용자 권한이 변경되었습니다." );

        mailSender.send(simpleMailMessage);

        return true;
    }
}
