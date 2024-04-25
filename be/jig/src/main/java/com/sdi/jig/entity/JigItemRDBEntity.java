package com.sdi.jig.entity;

import com.sdi.jig.util.JigStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "jig_items")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class JigItemRDBEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "serial_no", length = 50)
    private String serialNo;

    @Column
    @Enumerated(EnumType.STRING)
    private JigStatus status;

    @Column(name = "use_accumulate_time")
    private Long useAccumulateTime;

    @ManyToOne
    private JigRDBEntity jig;

    @ManyToOne
    private FacilityItemRDBEntity facilityItem;

    @OneToMany(mappedBy = "jigItem")
    private List<JigItemIOHistoryRDBEntity> ioHistory;

    @OneToMany(mappedBy = "jigItem")
    private List<JigItemRepairHistoryRDBEntity> repairHistory;

    public static JigItemRDBEntity from(String serialNo, JigRDBEntity jigRDBEntity) {
        return new JigItemRDBEntity(null, serialNo, JigStatus.WAREHOUSE, 0L, jigRDBEntity,
                null, null, null);
    }
}
