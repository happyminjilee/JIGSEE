import { EventSourcePolyfill } from "event-source-polyfill";
import { axiosAuthApi } from "@/utils/instance";

export const fetchSSE = () => {
  const accessToken = localStorage.getItem("access_token");
  if (accessToken) {
    const EventSource = EventSourcePolyfill;
    const url = "http://k10s105.p.ssafy.io:80/api/v1/notification/sse/subscribe";
    const eventSource = new EventSource(url, {
      headers: { Authorization: accessToken },
      heartbeatTimeout: 90000, // 밀리초 단위로 타임아웃을 90초로 설정
    });
    // sse 연결 시작
    eventSource.onmessage = (e) => {
      const data = JSON.parse(e.data);
      console.log("Received SSE data:", data);
      alert(`New Notification: ${data.message}`);
    };

    eventSource.onerror = (e) => {
      console.error(e, "SSE 에러 발생");
      // 자동 재연결 로직 추가
      if (e.target.readyState === EventSource.CLOSED) {
        console.log("SSE 연결 재시도 중...");
        setTimeout(fetchSSE, 5000); // 5초 후 재시도
      }
    };
  } else {
    console.error("Access token not available. Please login.");
  }
};
export const finishSSE = async () => {
  const url = "http://k10s105.p.ssafy.io:80/api/v1/notification/sse/disconnect";
  const response = await axiosAuthApi().delete(url);
  console.log("알람 통신 끊기", response);
};
// 미확인 알림 조회
export const getUnchecked = async () => {
  const url = "http://k10s105.p.ssafy.io:80/api/v1/notification/search/unchecked";
  const response = await axiosAuthApi().get(url);
  return response.data.result;
};
// 전체 알림 리스트 조회
export const getAllalarms = async (page: number) => {
  const url = "http://k10s105.p.ssafy.io:80/api/v1/notification/search/all";
  const params = { page: page, size: 5 };
  const response = await axiosAuthApi().get(url, { params });
  return response.data.result;
};
// 알림 체크
export const checkAlarm = async (ID: number) => {
  const url = "http://k10s105.p.ssafy.io:80/api/v1/notification/check";
  const params = { "notification-id": ID };
  const response = await axiosAuthApi().put(url, null, { params });
  return response;
};
