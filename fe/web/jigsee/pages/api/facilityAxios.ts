import { axiosApi, axiosAuthApi } from "@/utils/instance";

// 설비 목록 조회 api
export const getfacilitylist = async () => {
  const http = axiosAuthApi();
  return await http
    .get("/facility-item/all")
    .then((response) => {
      return response.data.result;
    })
    .catch((error) => {
      console.log("facilities", error.message);
    });
};

export const getfacility = async (id: string) => {
  const http = axiosAuthApi();
  return await http
    .get("/facility", {
      params: {
        "facility-id": id,
      },
    })
    .then((response) => {
      return response;
    })
    .catch((error) => {
      console.log("facility", error.message);
    });
};
// 설비에 장착가능한 jig 리스트 다 불러오기
export const getfacilityitems = async (id: string) => {
  const http = axiosAuthApi();
  return await http
    .get("/jig-item/facility-available", {
      params: {
        "facility-model": id,
      },
    })
    .then((response) => {
      return response.data.result;
    })
    .catch((error) => {
      console.log("facility", error.message);
    });
};
