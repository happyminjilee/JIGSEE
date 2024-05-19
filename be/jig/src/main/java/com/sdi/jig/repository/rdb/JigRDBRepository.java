package com.sdi.jig.repository.rdb;

import com.sdi.jig.entity.rdb.JigRDBEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JigRDBRepository extends JpaRepository<JigRDBEntity, Long> {

    Optional<JigRDBEntity> findByModel(String model);
}
