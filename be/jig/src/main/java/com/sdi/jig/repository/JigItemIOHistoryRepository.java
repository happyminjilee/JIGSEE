package com.sdi.jig.repository;

import com.sdi.jig.entity.JigItemIOHistoryRDBEntity;
import com.sdi.jig.util.IOStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JigItemIOHistoryRepository extends JpaRepository<JigItemIOHistoryRDBEntity, Long> {

    List<JigItemIOHistoryRDBEntity> findByJigItemIdAndStatus(Long jigItemId, IOStatus status);

    List<JigItemIOHistoryRDBEntity> findByJigItemId(Long jigItemId);
}
