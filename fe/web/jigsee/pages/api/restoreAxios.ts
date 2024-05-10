import { axiosApi, axiosAuthApi } from "@/utils/instance";

export async function getJigrestorerList(page: number) {
  const params = { page: page, size: 5 };
  return axiosAuthApi().get("/request/repair", { params });
}
export async function getrestorerDetail(id: number) {
  const params = { "repair-jig-id": id };
  return axiosAuthApi().get("/request/repair/detail", { params });
}
