import { axiosAuthApi } from "@/utils/instance";
//지그 점검 유형 추가 함수
export async function putjigMethod(phoneNumber: number | string) {
  return (
    axiosAuthApi()
      //   const requestBody = { model: str, checkList: list }
      .put("/jig")
      .then((response) => {
        console.log(response.data.data);
        return response.data.data;
      })
      .catch((error) => {
        console.log(error.message);
      })
  );
}
export async function updatejigStatus() {
  let data = {};
  return axiosAuthApi().post("/jig-item/status", data);
}

export async function getJiginfo() {
  let data = {};
  return axiosAuthApi().post(`${http://k10s105.p.ssafy.io/api/v1/jig-item}+${}`, data);
}
