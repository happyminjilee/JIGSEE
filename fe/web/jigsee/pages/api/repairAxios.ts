import { axiosApi, axiosAuthApi } from "@/utils/instance";

export const repairRequest = async (memo: string, serialNos: string[]) => {
  const http = axiosAuthApi();
  return await http
    .post("http://k10s105.p.ssafy.io/api/v1/request/repair", {
      memo: memo,
      serialNos: serialNos,
    })
    .then((response) => {
      return true;
    })
    .catch((error) => {
      console.log(error.message);
      return false;
    });
};
export async function getJigrepairList(page: number) {
  const params = { page: page, size: 5 };
  return axiosAuthApi().get("http://k10s105.p.ssafy.io:80/api/v1/request/repair", { params });
}
