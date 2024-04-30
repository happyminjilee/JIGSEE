import { create } from "zustand";

interface jigStore {
  // jig 시리얼 넘버 저장 상태 변수
  serialNum: string;
  // 지그 상태 enum(WAREHOUSE, READY, IN, OUT, REPAIR, DELETE)
  jigStatus: string;
  //Jig 모델 변수
  jigModel: string;
  // jig 점검항목 저장 리스트
  jigcheckList: any;
  yourAction: (val: any) => void;
}
export const usejigStore = create<jigStore>((set) => ({
  serialNum: "",
  jigStatus: "",
  jigModel: "",
  jigcheckList: [],
  yourAction: (val) => set((state) => ({ yourState: state.yourState })),
}));
