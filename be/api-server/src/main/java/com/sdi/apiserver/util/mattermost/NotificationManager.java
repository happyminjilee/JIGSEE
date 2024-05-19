package com.sdi.apiserver.util.mattermost;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationManager {
    private Logger log = LoggerFactory.getLogger(NotificationManager.class);
    private final MatterMostSender mmSender;

    public void sendNotification(Exception e, String uri, String params) {
        log.info("#### SEND Exception Notification to MatterMost");
        mmSender.sendMessage(e, uri, params);
    }

}