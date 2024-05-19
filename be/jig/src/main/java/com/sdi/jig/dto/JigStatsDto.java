package com.sdi.jig.dto;

import com.sdi.jig.entity.rdb.JigRDBEntity;

public record JigStatsDto(
        int repairCount,
        double rowMeans,
        double interval,
        double mtbf,
        double lambda,
        double optimalInterval,
        JigRDBEntity jig
) {
    public static JigStatsDto of(
            int repairCount,
            double rowMeans,
            double interval,
            double mtbf,
            double lambda,
            double optimalInterval,
            JigRDBEntity jig
    ) {
        return new JigStatsDto(repairCount, rowMeans, interval, mtbf, lambda, optimalInterval, jig);
    }

    public JigStatsDto updateJig(JigRDBEntity jigRDBEntity) {
        return new JigStatsDto(this.repairCount, this.rowMeans, this.interval, this.mtbf, this.lambda, this.optimalInterval, jigRDBEntity);
    }
}
