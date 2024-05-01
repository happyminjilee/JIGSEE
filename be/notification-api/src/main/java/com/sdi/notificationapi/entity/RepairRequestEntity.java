package com.sdi.notificationapi.entity;

import com.sdi.notificationapi.dto.request.RepairJigRequestDto;
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

@Document(collection = "repair_requests")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class RepairRequestEntity {
    @Id
    private String id; // 자동생성된 uuid
    private LocalDateTime time; // 요청 시간
    @Field("serial_no_list")
    private List<String> serialNos; // 수리 요청하는 지그 리스트
    private String memo; // 메모
    private String from; // 요청자
    
    public static RepairRequestEntity from(RepairJigRequestDto repairJigRequestDto) {
        String uuid = UUID.randomUUID().toString();
        LocalDateTime time = LocalDateTime.now();
        return new RepairRequestEntity(uuid, time, repairJigRequestDto.serialNos(), repairJigRequestDto.memo(), repairJigRequestDto.sender());
    }
}
