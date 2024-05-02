package com.sdi.notification.service;

import com.sdi.notification.dto.MemberResponseDto;
import com.sdi.notification.dto.NotificationDto;
import com.sdi.notification.dto.response.NotificationListResponseDto;
import com.sdi.notification.dto.response.UncheckedNotificationListResponseDto;
import com.sdi.notification.entity.NotificationEntity;
import com.sdi.notification.repository.NotificationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SearchService {
    private final NotificationRepository notificationRepository;

    public UncheckedNotificationListResponseDto getUncheckedNotifications(MemberResponseDto member) {
        List<NotificationEntity> uncheckedNotificationList = notificationRepository.findAllByReceiverAndCheckStatusIsFalseOrderByIdDesc(member.employeeNo())
                .orElseThrow(() -> new IllegalArgumentException("사번 : " + member.employeeNo() + "의 미확인 알림을 검색하는 과정에서 문제 발생"));

        return UncheckedNotificationListResponseDto.from(
                uncheckedNotificationList.stream()
                        .map(NotificationDto::from)
                        .collect(Collectors.toList()));
    }

    public NotificationListResponseDto getNotifications(MemberResponseDto member, int pageNumber, int size) {
        Pageable pageable = PageRequest.of(pageNumber - 1, size);
        Page<NotificationEntity> page = notificationRepository.findAllByReceiverOrderByIdDesc(member.employeeNo(), pageable);
        List<NotificationDto> notifications = page.getContent().stream()
                .map(NotificationDto::from)
                .toList();

        return NotificationListResponseDto.of(page.getNumber() + 1, page.getTotalPages(), notifications);
    }
}

