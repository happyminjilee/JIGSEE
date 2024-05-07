import { axiosApi, axiosAuthApi } from "@/utils/instance";

// 설비 목록 조회 api
export const getfacilitylist = async () => {
  const http = axiosAuthApi();
  return await http
    .get("http://k10s105.p.ssafy.io/api/v1/facility-item/all")
    .then((response) => {
      console.log("tytyty", response);
      return response.data.result;
    })
    .catch((error) => {
      console.log("facilities", error.message);
    });
};

export const getfacility = async (id: string) => {
  const http = axiosAuthApi();
  return await http
    .get("http://k10s105.p.ssafy.io/api/v1/facility", {
      params: {
        "facility-id": id,
      },
    })
    .then((response) => {
      console.log("설비해당지그목록", response);
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
    .get("http://k10s105.p.ssafy.io/api/v1/jig-item/facility-available", {
      params: {
        "facility-model": id,
      },
    })
    .then((response) => {
      console.log("jigjigjig", response);
      return response.data.result;
    })
    .catch((error) => {
      console.log("facility", error.message);
    });
};
