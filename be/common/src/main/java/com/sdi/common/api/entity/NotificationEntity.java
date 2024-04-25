package com.sdi.common.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "notifications")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "check_status")
    private boolean checkStatus;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private MemberEntity sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private MemberEntity receiver;

    public static NotificationEntity of(MemberEntity sender, MemberEntity receiver) {
        return new NotificationEntity(null, false, sender, receiver);
    }
}
