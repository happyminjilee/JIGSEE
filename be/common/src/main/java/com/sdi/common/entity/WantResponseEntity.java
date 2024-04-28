package com.sdi.common.entity;

import com.sdi.common.dto.request.ResponseJigRequestDto;
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

@Document(collection = "want_responses")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class WantResponseEntity {
    @Id
    private String id; // uuid
    private LocalDateTime time; // 응답 시간
    @Field("is_accept")
    private Boolean isAccept; // 승인 여부
    @Field("request_id")
    private String requestId; // 원본 요청의 mongoDB 아이디
    @Field("serial_no_list")
    private List<String> jigList; // 불출 지그 일련번호 리스트
    private String memo; // 알림 내용

    public static WantResponseEntity from(ResponseJigRequestDto responseJigRequestDto) {
        String uuid = UUID.randomUUID().toString();
        LocalDateTime time = LocalDateTime.now();
        return new WantResponseEntity(
                uuid,
                time,
                responseJigRequestDto.isAccept(),
                responseJigRequestDto.requestId(),
                responseJigRequestDto.serialNos(),
                responseJigRequestDto.memo());
    }
}
