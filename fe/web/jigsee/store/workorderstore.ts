import { create } from "zustand";
import { AxiosResponse } from "axios";
import { state } from "sucrase/dist/types/parser/traverser/base";
import { getAllWo, getWogroup, getWoInfo } from "@/pages/api/workorderAxios";

interface compo {
  modal: boolean;
  setModal: (n: boolean) => void;
  // wo 조회에 필요한 wo id 변수
  woId: string;
  setWoId: (newId: string) => void;
  // wo test 컴포넌트를 여는 상태변수
  rightCompo: string;
  setRightCompo: (n: string) => void;
}

export const useCompoStore = create<compo>((set) => ({
  modal: false,
  setModal: (n: boolean) => {
    set({ modal: n });
  },
  // wo 조회에 필요한 wo id 변수
  woId: "",
  setWoId: (newId: string) => {
    set({ woId: newId });
  },
  // wo test 컴포넌트를 여는 상태변수
  rightCompo: "",
  setRightCompo: (n: string) => {
    set({ woId: n });
  },
}));

interface lst {
  id: number;
  model: string; // 지그 모델명
  serialNo: string; // 지그 일련번호
  creator: string; // 작성자
  terminator: string; // 작성 종료자
  status: string; // wo 상태
  createdAt: string; // wo 생성시간
  updatedAt: string; // wo 수정시간
}

interface jigItemInfo {
  model: string; // 지그 모델명
  serialNo: string; // 지그 일련번호
  status: string; // 지그 상태
  expectLife: string; // 지그 수명
  useCount: number; // 지그 사용 횟수
  useAccumulationTime: string; // 지그 사용 누적 시간
  repairCount: number; // 지그 수리 횟수
}

interface checklist {
  uuid: string; // 점검항목 구분을 위한 id
  content: string; // 점검항목
  standard: string; // 기준 값
  measure: string; // 측정값
  memo: string; // 비고
  passOrNot: boolean; // 통과 유무
}

interface Wo {
  currentPage: number;
  endPage: number;
  list: lst[];
  fetchWo: (state: string, page: number, size: number) => Promise<AxiosResponse>;
}

interface WoGroup {
  publish: lst[];
  progress: lst[];
  finish: lst[];
  fetchWoGroup: () => Promise<AxiosResponse>;
}

interface WoDetail {
  id: number;
  status: string;
  creator: string; // 생성자
  terminator: string; // 완료자
  createdAt: string; // wo 생성일
  updatedAt: string; // wo 수정일
  jigItemInfo: jigItemInfo;
  checkList: checklist[];
  fetchWoDetail: (id: number) => Promise<AxiosResponse>;
}

export const useWoStore = create<Wo>((set) => ({
  currentPage: 1,
  endPage: 1,
  list: [],
  fetchWo: async (state: string, page: number, size: number) => {
    const data = await getAllWo(state, page, size);
    set({
      currentPage: data.data.result.currentPage,
      endPage: data.data.result.endPage,
      list: data.data.result.list,
    });
    return data.data;
  },
}));

export const useWoDetailStore = create<WoDetail>((set) => ({
  id: 0,
  status: "PUBLISH",
  creator: "", // 생성자
  terminator: "", // 완료자
  createdAt: "", // wo 생성일
  updatedAt: "", // wo 수정일
  jigItemInfo: {
    model: "", // 지그 모델명
    serialNo: "", // 지그 일련번호
    status: "", // 지그 상태
    expectLife: "", // 지그 수명
    useCount: 0, // 지그 사용 횟수
    useAccumulationTime: "", // 지그 사용 누적 시간
    repairCount: 0, // 지그 수리 횟수
  },
  checkList: [],
  fetchWoDetail: async (id: number) => {
    const data = await getWoInfo(id);
    console.log("점검항목", data);
    set({
      id: data.data.result.id,
      status: data.data.result.status,
      creator: data.data.result.creator, // 생성자
      terminator: data.data.result.terminator, // 완료자
      createdAt: data.data.result.createAt, // wo 생성일
      updatedAt: data.data.result.updatedAt, // wo 수정일
      jigItemInfo: data.data.result.jigItemInfo,
      checkList: data.data.result.checkList,
    });
    return data.data;
  },
}));

export const useWoGroupStore = create<WoGroup>((set) => ({
  publish: [],
  progress: [],
  finish: [],
  fetchWoGroup: async () => {
    const data = await getWogroup();
    set({
      publish: data.data.result.publish,
      progress: data.data.result.progress,
      finish: data.data.result.finish,
    });
    return data.data;
  },
}));

interface woPut {
  id: number;
  checklist: [
    {
      uuid: string;
      measure: string;
      memo: string;
      passOrNot: boolean;
    }
  ];
}

interface woUpdate {
  list: [
    {
      id: number; // wo의 id
      status: string;
    }
  ]; // 변경될 상태}
}
