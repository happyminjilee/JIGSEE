package com.sdi.jig.entity.rdb;

import com.sdi.jig.dto.JigStatsDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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

    @Column(precision = 20, scale = 13)
    private BigDecimal rowMeans;

    @Column(name = "`interval`", precision = 20, scale = 13)
    private BigDecimal interval;

    @Column(precision = 20, scale = 13)
    private BigDecimal mtbf;

    @Column(precision = 20, scale = 13)
    private BigDecimal lambda;

    @Column(precision = 20, scale = 13)
    private BigDecimal optimalInterval;

    @ManyToOne
    private JigRDBEntity jig;

    private JigStatsRDBEntity(int repairCount,
                              BigDecimal rowMeans,
                              BigDecimal interval,
                              BigDecimal mtbf,
                              BigDecimal lambda,
                              BigDecimal optimalInterval,
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
