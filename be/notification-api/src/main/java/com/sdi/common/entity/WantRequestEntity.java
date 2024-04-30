package com.sdi.common.entity;

import com.sdi.common.dto.RequestJigDto;
import com.sdi.common.dto.request.RequestJigRequestDto;
import com.sdi.common.util.JigRequestStatus;
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
    private LocalDateTime time; // 요청 시간
    @Field("jig_list")
    private List<RequestJigDto> jigRequestDtoList; // 요청하는 지그 리스트
    private String memo; // 메모
    @Field("is_accept")
    private Boolean isAccept; // 승인 여부
    private JigRequestStatus status; // 처리 여부
    @Field("update_time")
    private LocalDateTime updateTime; // 처리 시간
    private String from; // 요청자
    private String to; // 처리자

    public static WantRequestEntity from(RequestJigRequestDto requestJigRequestDto) {
        String uuid = UUID.randomUUID().toString();
        LocalDateTime time = LocalDateTime.now();
        return new WantRequestEntity(
                uuid,
                time,
                requestJigRequestDto.list(),
                requestJigRequestDto.memo(),
                false,
                JigRequestStatus.PUBLISH,
                null,
                requestJigRequestDto.sender(),
                null);
    }

    public WantRequestEntity processRequest(String to, Boolean isAccept) {
        this.to = to;
        this.isAccept = isAccept;
        this.status = JigRequestStatus.FINISH;
        this.updateTime = LocalDateTime.now();
        return this;
    }
}
