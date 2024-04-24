package com.sdi.jig.entity;

import com.sdi.jig.util.JigStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @ManyToOne
    private JigRDBEntity jig;

    @ManyToOne
    private FacilityItemRDBEntity facilityItem;

    public static JigItemRDBEntity from(String serialNo, JigRDBEntity jigRDBEntity){
        return new JigItemRDBEntity(null, serialNo, JigStatus.WAREHOUSE, jigRDBEntity, null);
    }
}
