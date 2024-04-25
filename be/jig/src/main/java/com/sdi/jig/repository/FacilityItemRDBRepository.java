package com.sdi.jig.repository;

import com.sdi.jig.entity.FacilityItemRDBEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FacilityItemRDBRepository extends JpaRepository<FacilityItemRDBEntity, Long> {

    Optional<FacilityItemRDBEntity> findBySerialNo(String serialNo);
}
