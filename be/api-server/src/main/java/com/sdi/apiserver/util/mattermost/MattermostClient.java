package com.sdi.apiserver.util.mattermost;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "mattermostClient", url = "${apis.mattermost-exception-webhook-url}")
public interface MattermostClient {
    @PostMapping("")
    void sendExceptionNotification(@RequestBody MatterMostMessageDto.Attachments payload);
}
