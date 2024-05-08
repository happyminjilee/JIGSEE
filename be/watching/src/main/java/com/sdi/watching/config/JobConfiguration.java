package com.sdi.watching.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.UUID;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class JobConfiguration {

    private static final String FAILED = "FAILED";

    @Bean
    public Job watchingJigJob(JobRepository jobRepository, Step loadTooMuchUseJigItemStep, Step sendToWorkOrderServerStep, Step sendToJigItemServerStep, Step sendToNotificationApiServerStep) {
        return new JobBuilder("watchingJigJobTest", jobRepository)
                .start(loadTooMuchUseJigItemStep)
                .on(FAILED).end()

                .from(loadTooMuchUseJigItemStep)
                .on("*").to(sendToJigItemServerStep)

                .from(sendToJigItemServerStep)
                .on(FAILED).end()
                .on("*").to(sendToNotificationApiServerStep)

                .end()
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
    public Step sendToWorkOrderServerStep(JobRepository jobRepository,
                                          PlatformTransactionManager platformTransactionManager,
                                          Tasklet sendToWorkOrderServerTasklet) {
        return new StepBuilder("sendToWorkOrderServer", jobRepository)
                .tasklet(sendToWorkOrderServerTasklet, platformTransactionManager)
                .build();

    }

    @Bean
    public Step sendToJigItemServerStep(JobRepository jobRepository,
                                        PlatformTransactionManager platformTransactionManager,
                                        Tasklet sendToJigItemServerTasklet) {
        return new StepBuilder("sendToJigItemServerStep", jobRepository)
                .tasklet(sendToJigItemServerTasklet, platformTransactionManager)
                .build();
    }

    @Bean
    public Step sendToNotificationApiServerStep(JobRepository jobRepository,
                                                PlatformTransactionManager platformTransactionManager,
                                                Tasklet sendToNotificationApiServerTasklet) {
        return new StepBuilder("sendToNotificationApiServerStep", jobRepository)
                .tasklet(sendToNotificationApiServerTasklet, platformTransactionManager)
                .build();

    }
}
