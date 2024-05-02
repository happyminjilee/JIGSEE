package com.sdi.jig.repository;

import com.sdi.jig.entity.JigItemRDBEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JigItemRDBRepository extends JpaRepository<JigItemRDBEntity, Long> {

    List<JigItemRDBEntity> findByJigModel(String model);

    Optional<JigItemRDBEntity> findBySerialNo(String serialNo);
}
