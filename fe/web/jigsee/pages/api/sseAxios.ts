import { EventSourcePolyfill } from "event-source-polyfill";
import { axiosAuthApi } from "@/utils/instance";

const ALARM_API = process.env.NEXT_PUBLIC_ALARM_API_URI;
export const fetchSSE = () => {
  const accessToken = localStorage.getItem("access_token");
  if (accessToken) {
    const EventSource = EventSourcePolyfill;
    const url = `${ALARM_API}/notification/sse/subscribe`;
    const eventSource = new EventSource(url, {
      headers: { Authorization: accessToken },
      heartbeatTimeout: 90000, // 밀리초 단위로 타임아웃을 90초로 설정
    });
    // sse 연결 시작
    eventSource.onmessage = (e) => {};

    eventSource.onerror = (e) => {
      console.error(e, "SSE 에러 발생");
      // 자동 재연결 로직 추가
      if (e.target.readyState === EventSource.CLOSED) {
        setTimeout(fetchSSE, 5000); // 5초 후 재시도
      }
    };
  } else {
    console.error("Access token not available. Please login.");
  }
};
export const finishSSE = async () => {
  const url = "/notification/sse/disconnect";
  const response = await axiosAuthApi().delete(url);
};
// 미확인 알림 조회
export const getUnchecked = async () => {
  const url = "/notification/search/unchecked";
  const response = await axiosAuthApi().get(url);
  return response.data.result;
};
// 전체 알림 리스트 조회
export const getAllalarms = async (page: number) => {
  const url = "/notification/search/all";
  const params = { page: page, size: 5 };
  const response = await axiosAuthApi().get(url, { params });
  return response.data.result;
};
// 알림 체크
export const checkAlarm = async (ID: number) => {
  const url = "/notification/search/check";
  const params = { "notification-id": ID };
  const response = await axiosAuthApi().put(url, null, { params });
  return response;
};
