import { create } from "zustand";
import { getStockList } from "@/pages/api/jigAxios";
interface StockItem {
  model: string;
  count: number;
}
interface jigStore {
  stockList: StockItem[];
  setStockList: () => Promise<void>;
}
export const usejigStore = create<jigStore>((set) => ({
  stockList: [],
  setStockList: async () => {
    const data = await getStockList();
    console.log("재고현황", data);
    set({ stockList: data.list });
    // console.log("stokkk", data.list);
  },
}));
