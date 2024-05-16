package com.sdi.watching.repository;

import com.sdi.watching.entity.JigItemIOHistoryRDBEntity;
import com.sdi.watching.entity.NeedToInspectionInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JigItemIOHistoryRDBRepository extends JpaRepository<JigItemIOHistoryRDBEntity, Long> {

    @Query(value =
        """
            select f1.jig_item_id as jigItemId, 
                f1.jig_id as jigId, 
                f1.repair_count as repairCount, 
                truncate(f2.optimal_interval, 0) as optimalIntervalDay, 
                f1.max_in_out_time as lastInOutTime
            from (
            		select t1.jig_item_id, t2.jig_id, repair_count, t1.max_in_out_time
            		from (
            			select q1.id, q1.in_out_time, q1.status, q1.jig_item_id, q2.max_in_out_time
            			from jig_item_io_histories q1
            				inner join (
            					select jig_item_id, max(g1.in_out_time) as max_in_out_time
            					from jig_item_io_histories g1
            						join (
            							select id
            							from jig_items
            							where status = 'IN'
            						) g2
            						on g1.jig_item_id = g2.id
            					where g1.status = 'IN'
            					group by g1.jig_item_id
            				) q2
            				on q1.jig_item_id = q2.jig_item_id and q1.in_out_time = q2.max_in_out_time
            			) t1
            				join(
            					select i.id as jig_item_id, j.id as jig_id, coalesce(h.count, 0) as repair_count
            					from jig_items i
            						join jigs j
            						on i.jig_id = j.id
            							left join(
            								select h.jig_item_id, count(h.jig_item_id) as count
            								from jig_item_repair_histories h
            								group by jig_item_id
            							) h
            							on i.id = h.jig_item_id
            				) t2
            				on t1.jig_item_id = t2.jig_item_id
            	) f1
            	join jig_stats f2
                on f1.jig_id = f2.jig_id
            where f1.repair_count = f2.repair_count and datediff(now(), f1.max_in_out_time) >= truncate(f2.optimal_interval, 0);
        """, nativeQuery = true)
    List<NeedToInspectionInterface> needToInspectionJigItems();
}
