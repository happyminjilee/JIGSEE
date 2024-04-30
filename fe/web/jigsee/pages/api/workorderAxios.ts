import { axiosAuthApi } from "@/utils/instance";
//work order id로 WO조회 -기술팀
export async function getWoInfo(WOId: string) {
  const params = {
    "wo-order-id": WOId,
  };
  return axiosAuthApi().get("http://k10s105.p.ssafy.io/api/v1/work-order/detail", { params });
}
//WO전체 조회
export async function getAllWo(WOId: string) {
  const params = {
    status: "{wo상태}",
    page: "{페이지번호}",
    size: "{반환리스트개수}",
  };
  return axiosAuthApi().get("http://k10s105.p.ssafy.io/api/v1/work-order/all", { params });
}
// Wo grouping 조회
export async function getWogroup(WOId: string) {
  return axiosAuthApi().get("http://k10s105.p.ssafy.io/api/v1/work-order/group");
}
// wo 생성
export async function createWo(serialNo: string) {
  const requestBody = {
    serialNo: "str",
  };
  return axiosAuthApi().post("http://k10s105.p.ssafy.io/api/v1/work-order/group", requestBody);
}
// WO 임시저장
export async function saveWotmp() {
  const requestBody = {
    id: "long", // wo의 id
    checkList: [
      // 점검리스트
      {
        uuid: "str", // 점검 항목을 위한 값
        measure: "str", // 측정값
        memo: "str", // 비고
        passOrNot: "boolean", // 통과유무
      },
    ],
  };
  return axiosAuthApi().put("/work-order/tmp", requestBody);
}
// Wo 완료
export async function doneWo() {
  const requestBody = {
    id: "long", // wo의 id
    checkList: [
      // 점검리스트
      {
        uuid: "str", // 점검 항목을 위한 값
        measure: "str", // 측정값
        memo: "str", // 비고
        passOrNot: "boolean", // 통과유무
      },
    ],
  };
  return axiosAuthApi().put("http://k10s105.p.ssafy.io/api/v1/work-order/done", requestBody);
}
// Wo 상태 변경(리스트)
export async function updateWoList() {
  const requestBody = {
    list: [
      {
        id: "long", // wo의 id
        status: "enum", // 변경될 상태
      },
    ],
  };
  return axiosAuthApi().put("http://k10s105.p.ssafy.io/api/v1/work-order/done", requestBody);
}
