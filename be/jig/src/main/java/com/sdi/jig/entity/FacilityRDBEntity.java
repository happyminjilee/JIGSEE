package com.sdi.jig.entity;

import com.sdi.jig.entity.FacilityJigMappingRDBEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "facilities")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FacilityRDBEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String model;

    @OneToMany(mappedBy = "facility")
    private List<FacilityJigMappingRDBEntity> mapping;
}
