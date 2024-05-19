import { create } from "zustand";
import { getUnchecked, getAllalarms, checkAlarm } from "@/pages/api/sseAxios";
interface notification {
  checkStatus: boolean;
  contentId: string;
  id: number;
  notificationStatus: string;
  receiver: string;
  sender: string;
}
interface alarmtore {
  // 알람id
  alarmId: number;

  // 미확인 알림 개수
  uncheckednumber: number;
  // 미확인 알림 리스트
  uncheckedList: notification[];
  // 미확인 알림을 가져오기
  setUnchecked: () => Promise<void>;
  // 모든 알림 보기
  all_alarams: number;
  // 모든 알림 리스트
  alarmList: notification[];
  //모든 알람 가져오기
  setAllalarm: () => Promise<void>;
  //알림 확인 처리
  setAlarmCheck: (ID: number) => Promise<void>;
  // 페이지 네이션 페이지
  page: number;
  setPage: (page: number) => void;
}
export const useAlarmStore = create<alarmtore>((set, get) => ({
  // 알람id
  alarmId: 0,
  // 미확인 알림 개수
  uncheckednumber: 0,
  // 미확인 알림 리스트
  uncheckedList: [],
  // 미확인 알림을 가져오기
  setUnchecked: async () => {
    try {
      const result = await getUnchecked(); // 미확인 알림 조회
      // notification 필드가 있는지 확인하고, 없다면 빈 배열로 설정
      const notifications = result.notifications || [];
      set({
        uncheckedList: notifications,
        uncheckednumber: notifications.length,
      });
    } catch (error) {
      console.error("Failed", error);
    }
  },
  // 모든 알림 개수
  all_alarams: 0,
  // 모든 알림 리스트
  alarmList: [],
  //모든 알람 가져오기
  setAllalarm: async () => {
    const page = get().page;
    try {
      const result = await getAllalarms(page); // 모든 알림 조회

      // notification 필드가 있는지 확인하고, 없다면 빈 배열로 설정
      const notifications = result.notifications || [];
      set({ alarmList: notifications, all_alarams: notifications.length }); // 모든 알림 리스트 업데이트
    } catch (error) {
      console.error("all Failed", error);
    }
  },
  setAlarmCheck: async (ID: number) => {
    set({ alarmId: ID });
    try {
      const result = await checkAlarm(ID); // 모든 알림 조회
    } catch (error) {
      console.error("all Failed", error);
    }
  },
  page: 1,
  setPage: (page) => set({ page }),
}));
