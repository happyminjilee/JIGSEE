package com.sdi.jig.entity.rdb;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "jig_item_inspection")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class JigItemInspectionRDBEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_inspection")
    private Boolean isInspection;

    @ManyToOne
    private JigItemRDBEntity jigItem;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "notification_id")
    private String notificationId;

    public static JigItemInspectionRDBEntity of(JigItemRDBEntity jigItem, String notificationId){
        return new JigItemInspectionRDBEntity(null, false, jigItem, LocalDateTime.now(), null, notificationId);
    }

    public void updateIsInspection(){
        this.isInspection = true;
        this.updatedAt = LocalDateTime.now();
    }
}
