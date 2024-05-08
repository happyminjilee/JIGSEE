package com.sdi.watching.config.tasklet;

import com.sdi.watching.client.NotificationApiClient;
import com.sdi.watching.dto.request.ClientJigItemIdsRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class SendToNotificationApiServerTasklet implements Tasklet {

    private final NotificationApiClient notificationApiClient;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info(">>> sendToNotificationApiServerTasklet 실행");
        List<Long> jigItemIds = (List<Long>) contribution.getStepExecution().getJobExecution().getExecutionContext().get("ids");
        if (jigItemIds != null) {
            // notificationApiClient.inspection(ClientJigItemIdsRequestDto.from(jigItemIds));
        }
        return RepeatStatus.FINISHED;
    }
}
