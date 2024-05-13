import { axiosAuthApi } from "@/utils/instance";
export const getMonthJig = async () => {
  const params = { year: "", month: "" };
  const response = await axiosAuthApi().get("/jig/status");
  return response.data.result;
};
export const getJigcount = async () => {
  const response = await axiosAuthApi().get("/jig/count");
  return response.data.result;
};
