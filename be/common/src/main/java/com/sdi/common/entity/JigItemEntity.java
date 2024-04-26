package com.sdi.common.entity;

import com.sdi.common.util.JigStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Jig 서버가 연결되면 삭제할 엔티티
 */
@Entity
@Table(name = "jig_items")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class JigItemEntity {
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
}