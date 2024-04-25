package com.sdi.jig.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "facility_items")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FacilityItemRDBEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "serial_no", length = 50)
    private String serialNo;

    @ManyToOne
    private FacilityRDBEntity facility;

    @OneToMany(mappedBy = "facilityItem", fetch = FetchType.LAZY)
    private List<JigItemRDBEntity> jigItems;
}
