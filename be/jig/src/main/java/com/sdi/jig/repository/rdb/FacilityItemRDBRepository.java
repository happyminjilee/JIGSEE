package com.sdi.jig.repository.rdb;

import com.sdi.jig.entity.rdb.FacilityItemRDBEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FacilityItemRDBRepository extends JpaRepository<FacilityItemRDBEntity, Long> {

    Optional<FacilityItemRDBEntity> findBySerialNo(String serialNo);
}
