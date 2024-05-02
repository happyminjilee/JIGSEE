import { create } from "zustand";
import { userStore } from "@/store/memberstore";
import { releaseGet, releaseDetailGet } from "@/pages/api/releaseAxios";

interface lst {
  id: string; // 요청 uuid
  status: string;
  from: string; // 요청자
  to: string; // 승인자
  createAt: string; // 요청시간
  updatedAt: string; // 처리 시간
}

interface release {
  currentPage: number;
  endPage: number;
  list: lst[];
  fetchRelease: (status: string, page: number, size: number) => Promise<void>;
}

interface releaseDetail {
  isManager: boolean;
  id: string; // 요청 id
  status: string;
  from: string; // 요청자
  to: string; // 승인자
  memo: string; // 사유
  createAt: string; // 요청시간
  updatedAt: string;
  serialNos: string[]; // 요청 지그 리스트
  fetchReleaseDetail: (id: string) => Promise<void>;
}

const useReleaseStore = create<release>((set) => ({
  currentPage: 1,
  endPage: 1,
  list: [],
  fetchRelease: async (status: string, page: number, size: number) => {
    const data = await releaseGet(status, page, size);
    set({
      isManager: data.result.isManager,
      currentPage: data.result.currentPage,
      endPage: data.result.endPage,
      list: data.result.list,
    });
  },
}));

const useReleaseDetailStore = create<releaseDetail>((set) => ({
  isManager: checkManager,
  id: "", // 요청 id
  status: "",
  from: "", // 요청자
  to: "", // 승인자
  memo: "", // 사유
  createAt: "", // 요청시간
  updatedAt: "",
  serialNos: [], // 요청 지그 리스트
  fetchReleaseDetail: async (id: string) => {
    const data = await releaseDetailGet(id);
    set({
      isManager: data.result.isManager,
      id: data.result.id, // 요청 id
      status: data.result.status,
      from: data.result.from, // 요청자
      to: data.result.to, // 승인자
      memo: data.result.memo, // 사유
      createAt: data.result.createAt, // 요청시간
      updatedAt: data.result.updatedAt,
      serialNos: data.result.serialNos, // 요청 지그 리스트
    });
  },
}));

export default { useReleaseStore, useReleaseDetailStore };
