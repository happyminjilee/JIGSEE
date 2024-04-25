package com.sdi.jig.entity;

import com.sdi.jig.util.IOStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "jig_item_io_histories")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class JigItemIOHistoryRDBEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "in_out_time")
    private LocalDateTime inOutTime;

    @Column
    @Enumerated(EnumType.STRING)
    private IOStatus status;

    @ManyToOne
    private JigItemRDBEntity jigItem;
}
