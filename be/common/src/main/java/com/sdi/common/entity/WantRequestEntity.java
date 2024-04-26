package com.sdi.common.entity;

import com.sdi.common.dto.JigRequestDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "want_requests")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class WantRequestEntity {
    @Id
    private String id; // 자동생성된 uuid
    private LocalDateTime time; // 요청 시간
    @Field("sender")
    private Long senderId; // Member Table에 저장된 sender의 id
    @Field("receiver")
    private Long receiverId; // Member Table에 저장된 receiver의 id
    @Field("jig_list")
    private List<JigRequestDto> jigRequestDtoList; // 요청하는 지그 리스트
    @Field("is_accept")
    private Boolean isAccept; // 승인 여부
}
