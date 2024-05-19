package com.sdi.jig.entity.nosql;

import com.sdi.jig.util.CheckItem;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "jigs")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class JigNosqlEntity {

    @Id
    private String id; // model명이 id
    private List<CheckItem> checkList;
    private LocalDateTime updatedAt;

    public void updateCheckList(List<CheckItem> checkList) {
        this.checkList = checkList;
        this.updatedAt = LocalDateTime.now();
    }
}
