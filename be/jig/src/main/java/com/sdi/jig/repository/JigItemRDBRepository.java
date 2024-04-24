package com.sdi.jig.repository;

import com.sdi.jig.entity.JigItemRDBEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JigItemRDBRepository extends JpaRepository<JigItemRDBEntity, Long> {

    List<JigItemRDBEntity> findByJigModel(String model);
}
