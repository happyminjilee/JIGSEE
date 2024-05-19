package com.sdi.apiserver.util;

public enum NotificationStatus {
    REQUEST_JIG, // 지그 불출 요청(기술 -> 관리)
    RESPONSE_JIG, // 지그 불출 응답(관리 -> 기술)
    REQUEST_REPAIR, // 지그 보수 의뢰(생산 -> 기술)
    PERIODIC_INSPECTION, // 정기 점검 지그 리스트(시스템 -> 생산)
}
