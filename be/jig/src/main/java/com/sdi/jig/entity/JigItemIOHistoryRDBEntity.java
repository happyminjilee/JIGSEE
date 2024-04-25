package com.sdi.jig.entity;

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

    @Column(name = "input_time")
    private LocalDateTime inputTime;

    @Column(name = "output_time")
    private LocalDateTime outputTime;

    @ManyToOne
    private JigItemRDBEntity jigItem;
}
