package com.sdi.jig.entity.rdb;

import com.sdi.jig.util.JigStatus;
import com.sdi.jig.util.TimeCalculator;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

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

    @Column(name = "is_delete")
    private Boolean isDelete;

    @ManyToOne
    private JigRDBEntity jig;

    @ManyToOne
    private FacilityItemRDBEntity facilityItem;

    @OneToMany(mappedBy = "jigItem")
    private List<JigItemIOHistoryRDBEntity> ioHistory;

    @OneToMany(mappedBy = "jigItem")
    private List<JigItemRepairHistoryRDBEntity> repairHistory;

    @OneToMany(mappedBy = "jigItem")
    private List<JigItemInspectionRDBEntity> inspection;

    @Column(name = "deleted_time")
    private LocalDateTime deletedTime;

    public static JigItemRDBEntity from(String serialNo, JigRDBEntity jigRDBEntity) {
        return new JigItemRDBEntity(null, serialNo, JigStatus.WAREHOUSE, 0L, false, jigRDBEntity,
                null, null, null, null, null);
    }

    public void delete() {
        this.isDelete = true;
        this.status = JigStatus.DELETE;
        this.deletedTime = LocalDateTime.now();
    }

    public void updateState(JigStatus status) {
        this.status = status;
        if(status != JigStatus.DELETE && this.isDelete){ // 삭제되어 있으면 취소
            this.isDelete = false;
            this.deletedTime = null;
        }
    }

    public void recovery() {
        this.isDelete = false;
        this.deletedTime = null;
    }

    public void addAccumulateTime(JigItemIOHistoryRDBEntity recentIn) {
        this.useAccumulateTime += TimeCalculator.timeDiffToMills(recentIn.getInOutTime(), LocalDateTime.now());
    }

    public void updateFacilityItemNull() {
        this.facilityItem = null;
    }

    public void updateFacilityItem(FacilityItemRDBEntity facilityItem) {
        this.facilityItem = facilityItem;
    }
}
