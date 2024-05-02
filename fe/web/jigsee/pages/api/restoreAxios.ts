import { axiosApi, axiosAuthApi } from "@/utils/instance";

export async function getJigrestorerList(page: number) {
  const params = { page: page, size: 5 };
  return axiosAuthApi().get("http://k10s105.p.ssafy.io:80/api/v1/request/repair", { params });
}
