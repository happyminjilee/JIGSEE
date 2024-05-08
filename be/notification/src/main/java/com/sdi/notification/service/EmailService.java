package com.sdi.notification.service;

import com.sdi.notification.dto.MemberEmailDto;
import com.sdi.notification.dto.request.NotificationFcmInspectionRequestDto;
import com.sdi.notification.entity.EmailEntity;
import com.sdi.notification.repository.EmailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSenderSender;
    private final ApiService apiService;
    private final EmailRepository emailRepository;

    public void sendSimpleEmail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("메시지 전송 테스트입니다.");
        message.setTo("rladydwns129@naver.com");
        message.setText("테스트 슈우웃");
        javaMailSenderSender.send(message);
    }

    public void subscribe(String accessToken) {
        MemberEmailDto memberEmailDto = apiService.getMemberEmail(accessToken);
        emailRepository.save(EmailEntity.from(memberEmailDto));
    }

    public void unsubscribe(String accessToken) {
        MemberEmailDto memberEmailDto = apiService.getMemberEmail(accessToken);
        emailRepository.deleteByEmployeeNo(memberEmailDto.employeeNo());
    }

    public void sendInspectionEmail(NotificationFcmInspectionRequestDto dto) {
        List<EmailEntity> subscribers = emailRepository.findAll();
        String[] emails = subscribers.stream()
                .map(EmailEntity::getEmail)
                .toArray(String[]::new);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("멀티 메시지 전송 테스트입니다.");
        message.setTo(emails);
        message.setText(dto.toString());
        javaMailSenderSender.send(message);
    }


}
