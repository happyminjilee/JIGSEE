import { axiosAuthApi } from "@/utils/instance";
export const getMonthJig = async () => {
  const today = new Date();
  const year = today.getFullYear();
  const month = today.getMonth() + 1;

  const params = {
    year: year.toString(),
    month: month.toString().padStart(2, "0"),
  };
  const response = await axiosAuthApi().get("/jig/status", { params });
  return response.data.result;
};
export const getJigcount = async () => {
  const response = await axiosAuthApi().get("/jig/count");
  return response.data.result;
};
export const updateChecked = async () => {
  const today = new Date();
  const year = today.getFullYear();
  const month = today.getMonth() + 1;

  const params = {
    year: year.toString(),
    month: month.toString().padStart(2, "0"),
  };

  const response = await axiosAuthApi().get("/jig/update-check-list", { params });
  return response.data.result;
};
// GET OPTIMAL
export const getOptimal = async (Jigmodel: string) => {
  const params = { model: Jigmodel };
  const response = await axiosAuthApi().get("/jig/optimal-interval", { params });
  return response.data.result;
};
