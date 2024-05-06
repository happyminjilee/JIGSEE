package com.sdi.jig.repository.nosql;

import com.sdi.jig.entity.nosql.JigNosqlEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JigNosqlRepository extends MongoRepository<JigNosqlEntity, String> {
}
