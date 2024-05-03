import { axiosAuthApi } from "@/utils/instance";

//work order id로 WO조회 -기술팀
export async function getWoInfo(WOId: number) {
  const params = {
    "work-order-id": WOId,
  };
  return axiosAuthApi().get("http://k10s105.p.ssafy.io/api/v1/work-order/detail", { params });
}

//WO전체 조회
export async function getAllWo(state: string, page: number, size: number) {
  const params = {
    status: state,
    page: page,
    size: size,
  };
  return axiosAuthApi().get("http://k10s105.p.ssafy.io/api/v1/work-order/all", { params });
}

// Wo grouping 조회
export async function getWogroup() {
  return axiosAuthApi().get("http://k10s105.p.ssafy.io/api/v1/work-order/grouping");
}

// wo 생성
export async function createWo(serialNo: string) {
  const requestBody = {
    serialNo: serialNo,
  };
  return axiosAuthApi().post("http://k10s105.p.ssafy.io/api/v1/work-order", requestBody);
}

// WO 임시저장
export async function saveWotmp(
    id:number,
    checklist: {uuid: string, measure: string, memo:string, passOrNot: boolean}[]
) {
  const requestBody = {
    id: id, // wo의 id
    checkList: checklist
  };
  return axiosAuthApi().put("http://k10s105.p.ssafy.io/api/v1/work-order/tmp", requestBody);
}


// Wo 완료
export async function doneWo(
    id:number,
    checklist: {uuid: string, measure: string, memo:string, passOrNot: boolean}[]
) {
  const requestBody = {
    id: id, // wo의 id
    checkList: checklist
  };
  return axiosAuthApi().put("http://k10s105.p.ssafy.io/api/v1/work-order/done", requestBody);
}


// Wo 상태 변경(리스트)
export async function updateWoList(
    list: [
      {
        id: number, // wo의 id
        status: string, // 변경될 상태
      },
    ],
) {
  const requestBody = {
    list: list
  };
  return axiosAuthApi().put("http://k10s105.p.ssafy.io/api/v1/work-order/status", requestBody);
}
