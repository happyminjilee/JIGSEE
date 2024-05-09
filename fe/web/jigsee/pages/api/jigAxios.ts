import { axiosAuthApi } from "@/utils/instance";
interface RowData {
  content: string;
  standard: string;
}
//지그 점검 항목 조회-
export async function getjigMethod(modelID: string) {
  const params = { model: modelID };
  return axiosAuthApi().get("http://k10s105.p.ssafy.io:80/api/v1/jig", { params });
}

// 지그 점검 항목 수정-관리자
export async function updatejigMethod(jigmodel: string, checkList: RowData[]) {
  const requestBody = {
    model: jigmodel,
    checkList: checkList,
  };
  return axiosAuthApi().put("http://k10s105.p.ssafy.io:80/api/v1/jig", requestBody);
}

//지그 상태 수정 - 기술팀
export async function updateJigStatus(serialNo: string, status: string) {
  const requestBody = {
    serialNo: serialNo,
    status: status,
  };
  return axiosAuthApi().put("http://k10s105.p.ssafy.io/api/v1/jig-item/status", requestBody);
}
// 지그 재고 현황 리스트 불러오기
export const getStockList = async () => {
  const response = await axiosAuthApi().get("http://k10s105.p.ssafy.io/api/v1/jig-item/inventory");
  return response.data.result;
};
