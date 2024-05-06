package com.sdi.jig.entity.rdb;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "jigs")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class JigRDBEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String model;

    @Column(name = "expect_life", length = 50)
    private String expectLife;

    @OneToMany(mappedBy = "jig")
    private List<FacilityJigMappingRDBEntity> mapping;
}
