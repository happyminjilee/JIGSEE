package com.sdi.apiserver.api.jig.dto.response;

public record JigMonthResponseDto(
	int countDelete,
    int countChange,
    int countRepairRequest,
    int countRepairFinish
) {
}
