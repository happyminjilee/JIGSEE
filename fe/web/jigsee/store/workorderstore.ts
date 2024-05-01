import { create } from "zustand";

interface woStore {
  // wo 조회에 필요한 wo id 변수
  woId: string;
  setWoId: (newId: string) => void;
  //
}
export const usewoStore = create<woStore>((set) => ({
  woId: " ",
  setWoId: (newId) => set({ woId: newId }),
}));
