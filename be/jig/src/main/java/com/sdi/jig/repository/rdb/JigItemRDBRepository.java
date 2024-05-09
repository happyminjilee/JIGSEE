package com.sdi.jig.repository.rdb;

import com.sdi.jig.entity.rdb.JigItemRDBEntity;
import com.sdi.jig.util.JigStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JigItemRDBRepository extends JpaRepository<JigItemRDBEntity, Long> {

    List<JigItemRDBEntity> findByJigModel(String model);

    Optional<JigItemRDBEntity> findBySerialNo(String serialNo);

    Optional<JigItemRDBEntity> findBySerialNoAndIsDeleteFalse(String serialNo);

    List<JigItemRDBEntity> findByStatusAndJigIdIn(JigStatus status, List<Long> ids);

    List<JigItemRDBEntity> findBySerialNoIn(List<String> serialNos);

    List<JigItemRDBEntity> findByStatus(JigStatus status);
}
