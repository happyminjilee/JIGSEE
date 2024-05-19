import { create } from "zustand";
import { getfacilityitems, getfacilitylist } from "@/pages/api/facilityAxios";

interface facilities {
  facilitySerialNo: string;
  id: number;
  jigItemSerialNos: string[];
  jigModels: string[];
  model: string;
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
  jigmodels: string[];
  getJigSN: (facilityModel: string) => Promise<void>;
  // 점검 항목 수정을 위한 지그 모델 리스트
  editStandardJigs: string[];
  // 점검항목 수정을 위한 지그 모델 리스트를 바꿔주는 함수
  setEditJigs: (facilityID: number) => Promise<void>;
}
export const useFacilityStore = create<FacilityStore>((set, get) => ({
  facilities: [],
  facilityID: 0,
  loadFacilities: async () => {
    const data = await getfacilitylist();

    set({
      facilities: data.list,
    });
  },
  setfacilityID: async (ID) => {
    // 수정: async 추가
    set({ facilityID: ID }); // 수정: id → ID
  },
  jigmodels: [],
  getJigSN: async (facilityModel: string) => {
    const data = await getfacilityitems(facilityModel);

    set({ jigmodels: data.list });
  },
  editStandardJigs: [],
  setEditJigs: async (facilityID: number) => {
    const facilities = get().facilities;
    const foundModel = facilities.find((model) => model.id === facilityID);
    if (foundModel && foundModel.jigModels) {
      set({ editStandardJigs: foundModel.jigModels });
    } else {
      // 선택한 facilityID로 모델을 찾을 수 없거나 jigModels 데이터가 없는 경우
      set({ editStandardJigs: [] }); // editStandardJigs를 비움
    }
  },
}));
