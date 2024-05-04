package com.sdi.watching.config;

import com.sdi.watching.entity.JigItemIOHistoryRDBEntity;
import com.sdi.watching.repository.JigItemIOHistoryRDBRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class JobConfiguration {

    private final JigItemIOHistoryRDBRepository jigItemIOHistoryRDBRepository;

    @Bean
    public Job watchingJigJob(JobRepository jobRepository, Step loadTooMuchUseJigItemStep, Step sendToJigServerStep, Step sendToNotificationApiServerStep) {
        return new JobBuilder("watchingJigJobTest" + UUID.randomUUID().toString(), jobRepository)
                .start(loadTooMuchUseJigItemStep)
                .next(sendToJigServerStep)
                .next(sendToNotificationApiServerStep)
                .build();
    }

    @Bean
    public Step loadTooMuchUseJigItemStep(JobRepository jobRepository,
                                          PlatformTransactionManager platformTransactionManager,
                                          Tasklet loadTooMuchUseJigItemTasklet) {
        return new StepBuilder("loadTooMuchUseJigItemDataStep", jobRepository)
                .tasklet(loadTooMuchUseJigItemTasklet, platformTransactionManager)
                .build();
    }

    @Bean
    public Tasklet loadTooMuchUseJigItemTasklet() {
        return (contribution, chunkContext) -> {
            log.info(">>> loadTooMuchUseJigItemTasklet 실행");
            List<JigItemIOHistoryRDBEntity> runningJigItems = jigItemIOHistoryRDBRepository.findRunningJigItems();
            LocalDateTime now = LocalDateTime.now();
            List<Long> inConditionIds = new ArrayList<>();

            // 조건 판별
            for (JigItemIOHistoryRDBEntity runningJigItem : runningJigItems) {
                Duration between = Duration.between(runningJigItem.getInOutTime(), now);
                if (between.toHours() >= 1) { // 1시간 이상동안 사용했을 경우
                    inConditionIds.add(runningJigItem.getId());
                }
            }

            // 다른 step에서 사용할 수 있도록 데이터 추가
            contribution.getStepExecution()
                    .getJobExecution()
                    .getExecutionContext()
                    .put("ids", inConditionIds);
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public Step sendToJigServerStep(JobRepository jobRepository,
                                    PlatformTransactionManager platformTransactionManager,
                                    Tasklet sendToJigServerTasklet) {
        return new StepBuilder("eventToJigServer", jobRepository)
                .tasklet(sendToJigServerTasklet, platformTransactionManager)
                .build();

    }

    @Bean
    public Tasklet sendToJigServerTasklet() {
        return (contribution, chunkContext) -> {
            log.info(">>> sendToJigServerTasklet 실행");
            List<Long> ids = (List<Long>) contribution.getStepExecution().getJobExecution().getExecutionContext().get("ids");
            for (Long id : ids) {
                log.info(">>> id : {}", id);
            }
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public Step sendToNotificationApiServerStep(JobRepository jobRepository,
                                                PlatformTransactionManager platformTransactionManager,
                                                Tasklet sendToJigServerTasklet) {
        return new StepBuilder("sendToNotificationApiServerStep", jobRepository)
                .tasklet(sendToJigServerTasklet, platformTransactionManager)
                .build();

    }

    @Bean
    public Tasklet sendTosendToNotificationApiServerTasklet() {
        return (contribution, chunkContext) -> {
            log.info(">>> sendToJigServerTasklet 실행");
            return RepeatStatus.FINISHED;
        };
    }
}
