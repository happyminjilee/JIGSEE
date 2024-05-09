package com.sdi.apiserver.util.mattermost;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MatterMostSender {
    private Logger log = LoggerFactory.getLogger(MatterMostSender.class);

    private final boolean MATTERMOST_ENABLED = true;

    private final MattermostProperties mmProperties;
    private final MattermostClient mattermostClient;

    public void sendMessage(Exception exception, String uri, String params) {
        if (!MATTERMOST_ENABLED)
            return;

        try {
            MatterMostMessageDto.Attachment attachment = MatterMostMessageDto.Attachment.builder()
                    .channel(mmProperties.getChannel())
                    .authorIcon(mmProperties.getAuthorIcon())
                    .authorName(mmProperties.getAuthorName())
                    .color(mmProperties.getColor())
                    .pretext(mmProperties.getPretext())
                    .title(mmProperties.getTitle())
                    .text(mmProperties.getText())
                    .footer(mmProperties.getFooter())
                    .build();

            attachment.addExceptionInfo(exception, uri, params);
            MatterMostMessageDto.Attachments attachments = new MatterMostMessageDto.Attachments(attachment);
            attachments.addProps(exception);

            mattermostClient.sendExceptionNotification(attachments);
        } catch (Exception e) {
            log.error("#### ERROR!! Notification Manager : {}", e.getMessage());
        }

    }
}