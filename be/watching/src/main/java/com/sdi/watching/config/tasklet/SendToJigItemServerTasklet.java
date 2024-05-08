package com.sdi.watching.config.tasklet;

import com.sdi.watching.client.JigItemClient;
import com.sdi.watching.dto.request.ClientSerialNosRequestDto;
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
public class SendToJigItemServerTasklet implements Tasklet {

    private final JigItemClient jigItemClient;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info(">>> SendToJigServerTasklet 실행");
        List<Long> serialNos = (List<Long>) contribution.getStepExecution().getJobExecution().getExecutionContext().get("ids");
        if (serialNos != null) {
            jigItemClient.inspection(ClientSerialNosRequestDto.from(serialNos));
        }
        return RepeatStatus.FINISHED;
    }
}
