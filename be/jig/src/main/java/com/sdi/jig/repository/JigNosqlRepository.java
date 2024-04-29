package com.sdi.jig.repository;

import com.sdi.jig.entity.JigNosqlEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JigNosqlRepository extends MongoRepository<JigNosqlEntity, String> {
}
