package com.sdi.jig.dto.response;

public record JigMonthResponseDto(
	int countDelete,
    int countChange,
    int countRepairRequest,
    int countRepairFinish
) {
}
