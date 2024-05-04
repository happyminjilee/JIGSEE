package com.sdi.watching.repository;

import com.sdi.watching.entity.JigItemIOHistoryRDBEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JigItemIOHistoryRDBRepository extends JpaRepository<JigItemIOHistoryRDBEntity, Long> {

    @Query(value =
            """
            select t1.id, t1.in_out_time, t1.status, t1.jig_item_id
            from jig_item_io_histories t1
                inner join (
                    select j.jig_item_id as jig_item_id_sub, max(j.in_out_time) as max_in_out_time
                    from jig_item_io_histories j
                    where j.jig_item_id in (
                        select s.jig_item_id
                        from jig_item_io_histories s
                        group by s.jig_item_id
                        having (sum(case when s.status = 'IN' then 1 else 0 end) - sum(case when s.status = 'OUT' then 1 else 0 end) = 1)
                    )
                    group by j.jig_item_id
                ) t2
                on t1.jig_item_id = t2.jig_item_id_sub and t1.in_out_time = t2.max_in_out_time;
            """, nativeQuery = true)
    List<JigItemIOHistoryRDBEntity> findRunningJigItems();
}
