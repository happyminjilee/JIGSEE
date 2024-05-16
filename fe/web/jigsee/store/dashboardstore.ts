import { create } from "zustand";
import { getMonthJig, getJigcount, updateChecked } from "@/pages/api/dashboard";

interface Jiglocation {
  model: string;
  countReady: number;
  countWarehouse: number;
}
interface CheckItem {
  content: string;
  standard: string;
}
interface Updated {
  model: string;
  checkItems: CheckItem[];
}
interface dashboardstore {
  // 지그 수리, 폐기 수
  deleted: number;
  change: number;
  request: number;
  finish: number;
  isLoading: boolean;
  setIsLoading: (n:boolean) => void;
  getJignumbers: () => Promise<void>;
  // 지그 창고, 현장대기 수
  maxcount: number;
  modelscount: Jiglocation[];
  getJigcounts: () => Promise<void>;
  //점검항목 수정된 지그 리스트
  getJigupdated: () => Promise<void>;
  updatedList: Updated[];
  // Jig model
  jigmodel: string;
  setJigmodel: (jigmodel: string) => void;
}

export const useDashboardstore = create<dashboardstore>((set) => ({
  // 지그 수리, 폐기 수
  deleted: 0,
  change: 0,
  request: 0,
  finish: 0,
  isLoading: true,
  setIsLoading: (n:boolean) => {
    set({isLoading: n})
  },
  getJignumbers: async () => {
    const data = await getMonthJig();
    console.log("jig nummmmm", data);
    set({
      change: data.countChange,
      deleted: data.countDelete,
      request: data.countRepairRequest,
      finish: data.countRepairFinish,
    });
    return data
  },
  // 지그 창고, 현장대기 수
  maxcount: 0,
  modelscount: [],
  getJigcounts: async () => {
    const data = await getJigcount();
    console.log("jig locaaaa", data);
    set({ modelscount: data.jigModelCountList, maxcount: data.maxCount });
  },
  updatedList: [],
  getJigupdated: async () => {
    const data = await updateChecked();
    set({ updatedList: data.updatedJigList });
  },
  //jig model 아이디 변수 변경
  jigmodel: "",
  setJigmodel: (newjig: string) => {
    set({ jigmodel: newjig });
  },
}));
