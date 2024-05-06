package com.sdi.jig.repository.rdb;

import com.sdi.jig.entity.rdb.FacilityRDBEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FacilityRDBRepository extends JpaRepository<FacilityRDBEntity, String> {

    Optional<FacilityRDBEntity> findByModel(String model);
}
