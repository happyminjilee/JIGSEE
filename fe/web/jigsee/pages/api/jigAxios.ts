import { axiosAuthApi } from "@/utils/instance";

//지그 점검 항목 조회- 동작확인 완료
export async function getjigMethod(modelID: string) {
  const params = { model: modelID };
  try {
    const response = await axiosAuthApi().get("http://k10s105.p.ssafy.io/api/v1/jig", { params });
    console.log("data", response.data);
    return response;
  } catch (error) {
    console.error("Error fetching Jig data:", error);
    throw error; // 에러를 더 상위로 전파하여 필요한 조치를 취할 수 있도록 합니다.
  }
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

//지그 폐기 취소 - 기술팀
export async function jigStatusrecovery() {
  const requestBody = {
    serialNo: "str",
    status: "enum(WAREHOUSE, READY, IN, OUT, REPAIR, DELETE)",
  };
  return axiosAuthApi().put("http://k10s105.p.ssafy.io/api/v1/jig-item/recovery", requestBody);
}
