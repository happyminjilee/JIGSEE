package com.sdi.jig.entity;

import com.sdi.jig.util.CheckList;
import com.sdi.jig.util.CheckList.CheckItem;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "jigs")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class JigNosqlEntity {

    @Id
    private String id; // model명이 id
    private List<CheckItem> checkList;

    public void updateCheckList(List<CheckItem> checkList){
        this.checkList = checkList;
    }
}
