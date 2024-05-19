package com.sdi.jig.entity.rdb;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "jig_item_repair_histories")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class JigItemRepairHistoryRDBEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "repair_time")
    private LocalDateTime repairTime;

    @ManyToOne
    private JigItemRDBEntity jigItem;

    public static JigItemRepairHistoryRDBEntity of(JigItemRDBEntity jigItem){
        return new JigItemRepairHistoryRDBEntity(null, LocalDateTime.now(), jigItem);
    }
}
