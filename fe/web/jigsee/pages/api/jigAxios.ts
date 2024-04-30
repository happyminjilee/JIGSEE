import { axiosAuthApi } from "@/utils/instance";

//지그 점검 항목 조회-
export async function getjigMethod(modelID: string) {
  const params = { model: modelID };
  return axiosAuthApi().get("http://k10s105.p.ssafy.io/api/v1/jig", { params });
}

// 지그 점검 항목 수정-관리자
export async function updatejigMethod() {
  const requestBody = {
    model: "str",
    checkList: [
      // 점검항목
      {
        content: "str", // 점검 내용
        standard: "str", // 기준
      },
    ],
  };
  return axiosAuthApi().put("http://k10s105.p.ssafy.io/api/v1/jig", requestBody);
}

//지그 상태 수정 - 기술팀
export async function updatejigStatus(modelID: string) {
  const requestBody = {
    serialNo: "str",
    status: "enum(WAREHOUSE, READY, IN, OUT, REPAIR, DELETE)",
  };
  return axiosAuthApi().put("http://k10s105.p.ssafy.io/api/v1/jig-item/status", requestBody);
}

// export async function getJiginfo() {
//   let data = {};
//   return axiosAuthApi().post(`${http://k10s105.p.ssafy.io/api/v1/jig-item}+${}`, data);
// }
