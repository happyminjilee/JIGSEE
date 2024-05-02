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

    public void exchangeJigItem(JigItemRDBEntity beforeJigItem, JigItemRDBEntity afterJigItem) {
        if (!this.jigItems.remove(beforeJigItem)) {
            throw new IllegalArgumentException(String.format("\'%s\' 시설은 \'%s\' 지그를 가지고 있지 않습니다.", serialNo, beforeJigItem.getSerialNo()));
        }
        this.jigItems.add(afterJigItem);
    }
}
