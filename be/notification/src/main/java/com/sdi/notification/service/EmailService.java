package com.sdi.notification.service;

import com.sdi.notification.dto.MemberEmailDto;
import com.sdi.notification.dto.request.NotificationFcmInspectionRequestDto;
import com.sdi.notification.entity.EmailEntity;
import com.sdi.notification.repository.EmailRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSenderSender;
    private final ApiService apiService;
    private final EmailRepository emailRepository;
    private final SpringTemplateEngine templateEngine;

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

    public void sendStyledInspectionEmail(NotificationFcmInspectionRequestDto dto) {
        List<EmailEntity> subscribers = emailRepository.findAll();
        List<MemberEmailDto> members = subscribers.stream().map(MemberEmailDto::from).toList();
        try {
            for (MemberEmailDto member : members) {
                MimeMessage message = javaMailSenderSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
                helper.setSubject("정기 점검 지그 리스트입니다.");
                helper.setTo(member.email());
                String html = makeContext(member, dto);
                helper.setText(html, true);
                helper.addInline("warning", new ClassPathResource("static/images/warning.png"));
                javaMailSenderSender.send(message);
            }
        } catch (MessagingException e) {
            throw new IllegalArgumentException("정기 점검 메일 발송 중 문제가 발생했습니다.");
        }
    }

    private String makeContext(MemberEmailDto member, NotificationFcmInspectionRequestDto dto) {
        Context context = new Context();
        context.setVariable("member", member);
        context.setVariable("uuid", dto.uuid());
        context.setVariable("serialNos", dto.serialNos());
        return templateEngine.process("mail", context);
    }

}
