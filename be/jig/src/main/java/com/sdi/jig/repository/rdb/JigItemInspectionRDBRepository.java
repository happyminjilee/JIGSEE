package com.sdi.jig.repository.rdb;

import com.sdi.jig.entity.rdb.JigItemInspectionRDBEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JigItemInspectionRDBRepository extends JpaRepository<JigItemInspectionRDBEntity, Long> {

    Optional<JigItemInspectionRDBEntity> findByJigItemSerialNo(String serialNo);

    List<JigItemInspectionRDBEntity> findByIsInspectionFalse();

    List<JigItemInspectionRDBEntity> findByIsInspectionFalseAndJigItemId(Long id);
}
