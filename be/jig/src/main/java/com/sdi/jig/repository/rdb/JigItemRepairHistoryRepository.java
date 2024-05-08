package com.sdi.jig.repository.rdb;

import com.sdi.jig.entity.rdb.JigItemRepairHistoryRDBEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JigItemRepairHistoryRepository extends JpaRepository<JigItemRepairHistoryRDBEntity, Long> {

    List<JigItemRepairHistoryRDBEntity> findByJigItemId(Long jigItemId);
}
