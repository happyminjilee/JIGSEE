package com.sdi.watching.config.tasklet;

import com.sdi.watching.client.WorkOrderClient;
import com.sdi.watching.dto.request.WorkOrderAutoCreateRequestDto;
import feign.FeignException;
import feign.Response;
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
public class SendToWorkOrderServerTasklet implements Tasklet {

    private final WorkOrderClient workOrderClient;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
         log.info(">>> sendToWorkOrderServerTasklet 실행");
        List<Long> serialNos = (List<Long>) contribution.getStepExecution().getJobExecution().getExecutionContext().get("ids");
        if (serialNos != null) {
            // wo 서버에 wo 생성 요청
            // workOrderClient.create(WorkOrderAutoCreateRequestDto.from(serialNos));
        }

        return RepeatStatus.FINISHED;
    }
}
