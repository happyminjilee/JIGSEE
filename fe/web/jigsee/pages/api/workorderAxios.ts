import { axiosAuthApi } from "@/utils/instance";

// 임시저장에 필요한 checklist 형싱
interface checklist {
  uuid: string; // 점검항목 구분을 위한 id
  content: string; // 점검항목
  standard: string; // 기준 값
  measure: string; // 측정값
  memo: string; // 비고
  passOrNot: boolean; // 통과 유무
}
//work order id로 WO조회 -기술팀
export async function getWoInfo(WOId: number) {
  const params = {
    "work-order-id": WOId,
  };
  return axiosAuthApi().get("/work-order/detail", { params });
}

//WO전체 조회
export async function getAllWo(state: string, page: number, size: number) {
  const params = {
    status: state,
    page: page,
    size: size,
  };
  return axiosAuthApi().get("/work-order/all", { params });
}

// Wo grouping 조회
export async function getWogroup() {
  return axiosAuthApi().get("/work-order/grouping");
}

// wo 생성
export async function createWo(serialNo: string) {
  // 지그 시리얼 넘버 입력 - 생성
  const requestBody = {
    serialNo: serialNo,
  };
  return axiosAuthApi().post("/work-order", requestBody, {});
}

// WO 임시저장
export async function saveWotmp(id: number, checklist: checklist[]) {
  const requestBody = {
    id: id, // wo의 id
    checkList: checklist,
  };
  return axiosAuthApi().put("/work-order/tmp", requestBody);
}

// Wo 완료
export async function doneWo(id: number, checklist: checklist[]) {
  const requestBody = {
    id: id, // wo의 id
    checkList: checklist,
  };
  return axiosAuthApi().put("/work-order/done", requestBody);
}

// Wo 상태 변경(리스트)
export async function updateWoList(
  list: {
    id: number; // wo의 id
    status: string; // 변경될 상태
  }[]
) {
  const requestBody = {
    list: list,
  };
  return axiosAuthApi().put("/work-order/status", requestBody);
}

export async function getUserWoList(
  employeeNo: string,
  name: string,
  page: number,
  size: number
) {
  const params = {
    "employee-no": employeeNo,
    name: name,
    page: page,
    size: size,
  };
  return axiosAuthApi().get("/work-order", { params });
}
