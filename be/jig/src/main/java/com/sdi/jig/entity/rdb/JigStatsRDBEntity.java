package com.sdi.jig.entity.rdb;

import com.sdi.jig.dto.JigStatsDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "jig_stats")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class JigStatsRDBEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int repairCount;

    private double rowMeans;
    @Column(name = "`interval`")
    private double interval;
    private double mtbf;
    private double lambda;
    private double optimalInterval;

    @ManyToOne
    private JigRDBEntity jig;

    private JigStatsRDBEntity(int repairCount,
                              double rowMeans,
                              double interval,
                              double mtbf,
                              double lambda,
                              double optimalInterval,
                              JigRDBEntity jig
    ) {
        this.repairCount = repairCount;
        this.rowMeans = rowMeans;
        this.interval = interval;
        this.mtbf = mtbf;
        this.lambda = lambda;
        this.optimalInterval = optimalInterval;
        this.jig = jig;
    }

    public static JigStatsRDBEntity from(JigStatsDto jigStatsDto) {
        return new JigStatsRDBEntity(
                jigStatsDto.repairCount(),
                jigStatsDto.rowMeans(),
                jigStatsDto.interval(),
                jigStatsDto.mtbf(),
                jigStatsDto.lambda(),
                jigStatsDto.optimalInterval(),
                jigStatsDto.jig());
    }
}
