package com.sdi.notification.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.sdi.notification.dto.MemberInfoDto;
import com.sdi.notification.dto.request.NotificationFcmInspectionRequestDto;
import com.sdi.notification.dto.request.FcmTokenRequestDto;
import com.sdi.notification.entity.FcmEntity;
import com.sdi.notification.entity.NotificationEntity;
import com.sdi.notification.repository.FcmRepository;
import com.sdi.notification.repository.NotificationRepository;
import com.sdi.notification.util.RoleType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FcmService {
    private final FirebaseMessaging firebaseMessaging;
    private final FcmRepository fcmRepository;
    private final NotificationRepository notificationRepository;
    private final ApiService apiService;

    @Transactional
    public void sendNotificationTo(NotificationFcmInspectionRequestDto notificationFcmInspectionRequestDto) {
        List<String> receivers = apiService.getMembersInRole("PRODUCER")
                .stream()
                .map(MemberInfoDto::employeeNo)
                .collect(Collectors.toList());

        List<FcmEntity> fcmToken = fcmRepository.findAllByEmployeeNoIn(receivers)
                .orElseThrow(() -> new IllegalArgumentException("권한 : " + RoleType.PRODUCER + "를 검색하는 데 실패했습니다."));
        FcmEntity nowFcmEntity = null;

        try {
            for (FcmEntity fcmEntity : fcmToken) {
                nowFcmEntity = fcmEntity;
                Message message = makeMessage(fcmEntity);
                firebaseMessaging.send(message);
                saveToNotificationDB(notificationFcmInspectionRequestDto, fcmEntity.getToken());
            }
        } catch (FirebaseMessagingException e) {
            fcmRepository.delete(nowFcmEntity);
        }
    }

    private Message makeMessage(FcmEntity fcmEntity) {
        return Message.builder()
                .setNotification(Notification.builder()
                        .setTitle("지그 정기 점검 리스트")
                        .setBody("알림을 눌러 확인해주세요.")
                        .build())
                .putData("Data1", "Value1")
                .setToken(fcmEntity.getToken())
                .build();
    }

    public void saveToken(String accessToken, FcmTokenRequestDto fcmTokenRequestDto) {
        MemberInfoDto memberInfoDto = apiService.getMember(accessToken);
        FcmEntity nowMember = fcmRepository.findByEmployeeNo(memberInfoDto.employeeNo())
                .orElse(FcmEntity.from(memberInfoDto.employeeNo(), fcmTokenRequestDto));
        nowMember = nowMember.updateToken(fcmTokenRequestDto.token());
        
        fcmRepository.save(nowMember);
    }

    private NotificationEntity saveToNotificationDB(NotificationFcmInspectionRequestDto notificationFcmInspectionRequestDto, String receiverId) {
        NotificationEntity savedNotification = NotificationEntity.of(receiverId, notificationFcmInspectionRequestDto);
        notificationRepository.save(savedNotification);
        return savedNotification;
    }
}
