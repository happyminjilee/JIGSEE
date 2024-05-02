import { axiosApi, axiosAuthApi } from "@/utils/instance";

export async function getJigrestorerList(page: number) {
  const params = { page: page, size: 5 };
  return axiosAuthApi().get("http://k10s105.p.ssafy.io/api/v1/request/repair", { params });
}
export async function getrestorerDetail(id: number) {
  const params = { "repair-jig-id": id };
  return axiosAuthApi().get("http://k10s105.p.ssafy.io/api/v1//request/repair/detail", { params });
}
