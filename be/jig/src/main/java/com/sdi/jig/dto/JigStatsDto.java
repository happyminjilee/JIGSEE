package com.sdi.jig.dto;

import com.sdi.jig.entity.rdb.JigRDBEntity;

import java.math.BigDecimal;

public record JigStatsDto(
        int repairCount,
        BigDecimal rowMeans,
        BigDecimal interval,
        BigDecimal mtbf,
        BigDecimal lambda,
        BigDecimal optimalInterval,
        JigRDBEntity jig
) {
    public static JigStatsDto of(
            int repairCount,
            BigDecimal rowMeans,
            BigDecimal interval,
            BigDecimal mtbf,
            BigDecimal lambda,
            BigDecimal optimalInterval,
            JigRDBEntity jig
    ) {
        return new JigStatsDto(repairCount, rowMeans, interval, mtbf, lambda, optimalInterval, jig);
    }

    public JigStatsDto updateJig(JigRDBEntity jigRDBEntity) {
        return new JigStatsDto(this.repairCount, this.rowMeans, this.interval, this.mtbf, this.lambda, this.optimalInterval, jigRDBEntity);
    }
}
