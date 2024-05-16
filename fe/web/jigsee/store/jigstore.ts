import { create } from "zustand";
import { getStockList, getjigMethod } from "@/pages/api/jigAxios";
interface StockItem {
  model: string;
  count: number;
}
interface methodList {
  content: string;
  standard: string;
}
interface jigStore {
  stockList: StockItem[];
  setStockList: () => Promise<void>;
  methodList: methodList[];
  getJigMethodList: (model: string) => Promise<void>;
}
export const usejigStore = create<jigStore>((set) => ({
  stockList: [],
  setStockList: async () => {
    const data = await getStockList();
    set({ stockList: data.list });
    // console.log("stokkk", data.list);
  },
  methodList: [],
  getJigMethodList: async (model: string) => {
    const data = await getjigMethod(model);
    set({ methodList: data.checkList });
  },
}));
