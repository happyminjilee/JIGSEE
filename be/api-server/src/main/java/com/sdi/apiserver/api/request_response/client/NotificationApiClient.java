package com.sdi.apiserver.api.request_response.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "notificationApiClient", url = "${apis.notification-api-base-url}")
public interface NotificationApiClient {

}
