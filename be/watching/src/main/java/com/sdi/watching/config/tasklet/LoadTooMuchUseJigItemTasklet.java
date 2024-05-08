package com.sdi.watching.config.tasklet;

import com.sdi.watching.entity.JigItemIOHistoryRDBEntity;
import com.sdi.watching.repository.JigItemIOHistoryRDBRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoadTooMuchUseJigItemTasklet implements Tasklet {

    private final JigItemIOHistoryRDBRepository jigItemIOHistoryRDBRepository;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info(">>> loadTooMuchUseJigItemTasklet 실행");
        List<JigItemIOHistoryRDBEntity> runningJigItems = jigItemIOHistoryRDBRepository.findRunningJigItems();
        LocalDateTime now = LocalDateTime.now();
        List<Long> inConditionIds = new ArrayList<>();

        // 조건 판별
        for (JigItemIOHistoryRDBEntity runningJigItem : runningJigItems) {
            Duration between = Duration.between(runningJigItem.getInOutTime(), now);
            if (between.toHours() >= 1) { // 1시간 이상동안 사용했을 경우
                inConditionIds.add(runningJigItem.getJigItemId());
            }
        }

        // 다른 step에서 사용할 수 있도록 데이터 추가
        contribution.getStepExecution()
                .getJobExecution()
                .getExecutionContext()
                .put("jigItemIds", inConditionIds);

        log.info(">>> 점검 대상 jigItemIds : {}", inConditionIds);
        return RepeatStatus.FINISHED;
    }
}
