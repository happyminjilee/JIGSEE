package com.sdi.jig.repository.rdb;

import com.sdi.jig.entity.rdb.JigRDBEntity;
import com.sdi.jig.entity.rdb.JigStatsRDBEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JigStatsRDBRepository extends JpaRepository<JigStatsRDBEntity, Long> {
    List<JigStatsRDBEntity> findAllByJigOrderByRepairCount(JigRDBEntity jig);

    JigStatsRDBEntity findByJigIdAndRepairCount(Long id, int countRepair);
}
