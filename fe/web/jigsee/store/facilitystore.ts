import { create } from "zustand";
import { getfacility, getfacilitylist } from "@/pages/api/facilityAxios";

interface facilities {
  id: number;
  model: string;
  facilitySerialNo: string;
}

interface jigs {
  id: number;
  model: string;
  facilitySerialNo: string;
  status: string;
  expectLife: string;
  checkType: string;
  repairCount: number;
  checkCount: number;
}

interface facility {
  id: number;
  model: string;
  facilitySerialNo: string;
  jigs: Array<jigs>;
}

interface FacilityStore {
  facilities: facilities[];
  facilityID: number;
  loadFacilities: () => Promise<void>;
  setfacilityID: (ID: number) => Promise<void>;
  jigmodels: jigs[];
  getJigModels: () => Promise<void>;
}
export const useFacilityStore = create<FacilityStore>((set) => ({
  facilities: [],
  facilityID: 0,
  loadFacilities: async () => {
    const data = await getfacilitylist();
    console.log("facil", data.list);
    set({
      facilities: data.list,
    });
  },
  setfacilityID: async (ID) => {
    // 수정: async 추가
    set({ facilityID: ID }); // 수정: id → ID
  },
  jigmodels: [],
  getJigModels: async () => {},
}));
