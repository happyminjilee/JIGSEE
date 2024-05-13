import { create } from "zustand";
import { getMonthJig, getJigcount } from "@/pages/api/dashboard";

interface Jiglocation {
  model: string;
  countReady: number;
  countWarehouse: number;
}
interface dashboardstore {
  deleted: number;
  change: number;
  request: number;
  finish: number;
  getJignumbers: () => Promise<void>;
  maxcount: number;
  modelscount: Jiglocation[];
  getJigcounts: () => Promise<void>;
}

export const useDashboardstore = create<dashboardstore>((set) => ({
  deleted: 0,
  change: 0,
  request: 0,
  finish: 0,
  getJignumbers: async () => {
    const data = await getMonthJig();
    console.log("jig nummmmm", data);
    set({
      change: data.countChange,
      deleted: data.countDelete,
      request: data.countRepairRequest,
      finish: data.countRepairFinish,
    });
  },
  maxcount: 0,
  modelscount: [],
  getJigcounts: async () => {
    const data = await getJigcount();
    console.log("jig locaaaa", data);
    set({ modelscount: data.jigModelCountList, maxcount: data.maxCount });
  },
}));
