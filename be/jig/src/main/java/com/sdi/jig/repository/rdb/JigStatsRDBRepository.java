package com.sdi.jig.repository.rdb;

import com.sdi.jig.entity.rdb.JigRDBEntity;
import com.sdi.jig.entity.rdb.JigStatsRDBEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JigStatsRDBRepository extends JpaRepository<JigStatsRDBEntity, Long> {
    List<JigStatsRDBEntity> findAllByJigOrderByRepairCount(JigRDBEntity jig);

    JigStatsRDBEntity findByJigIdAndRepairCount(Long id, int countRepair);

    @Query("SELECT MAX(js.optimalInterval) FROM JigStatsRDBEntity js WHERE js.jig.id = :jigId")
    double findMaxOptimalIntervalByJigId(@Param("jigId") Long jigId);
}
