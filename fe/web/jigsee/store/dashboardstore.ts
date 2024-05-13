import { create } from "zustand";
import { getMonthJig } from "@/pages/api/dashboard";

interface dashboardstore {
  deleted: number;
  change: number;
  request: number;
  finish: number;
  getJignumbers: () => Promise<void>;
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
}));
