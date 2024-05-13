package com.sdi.jig.repository.nosql;

import com.sdi.jig.entity.nosql.JigNosqlEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface JigNosqlRepository extends MongoRepository<JigNosqlEntity, String> {
    Optional<List<JigNosqlEntity>> findAllByUpdatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
}
