package com.sdi.notificationapi.entity;

import com.sdi.notificationapi.dto.request.RequestJigRequestDto;
import com.sdi.notificationapi.util.JigRequestStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Document(collection = "want_requests")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class WantRequestEntity {
    @Id
    private String id; // 자동생성된 uuid
    @Field("create_time")
    private LocalDateTime time; // 요청 시간
    @Field("jig_list")
    private List<String> jigSerialNos; // 요청하는 지그 리스트
    private JigRequestStatus status; // 처리 여부
    private Boolean isAccept; // 승인 여부
    @Field("update_time")
    private LocalDateTime updateTime; // 처리 시간
    private String from; // 요청자
    private String to; // 처리자

    public static WantRequestEntity from(RequestJigRequestDto requestJigRequestDto, String sender) {
        String uuid = UUID.randomUUID().toString();
        LocalDateTime time = LocalDateTime.now();
        return new WantRequestEntity(
                uuid,
                time,
                requestJigRequestDto.serialNos(),
                JigRequestStatus.PUBLISH,
                false,
                null,
                sender,
                null);
    }

    public WantRequestEntity updateWantRequest(String to, boolean isAccept) {
        this.to = to;
        this.status = JigRequestStatus.FINISH;
        this.isAccept = isAccept;
        this.updateTime = LocalDateTime.now();
        return this;
    }
}
