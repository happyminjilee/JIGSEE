import { create } from "zustand";

interface woStore {
  // wo 조회에 필요한 wo id 변수
  woId: string;
  setWoId: (newId: string) => void;
  // wo test 컴포넌트를 여는 상태변수
  openWotest: boolean;
  setopenWotest: (newval: boolean) => void;
}
export const usewoStore = create<woStore>((set) => ({
  woId: " ",
  setWoId: (newId) => set({ woId: newId }),
  openWotest: false,
  setopenWotest: (newval) => set({ openWotest: newval }),
}));
