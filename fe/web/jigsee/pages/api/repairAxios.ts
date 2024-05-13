import { axiosApi, axiosAuthApi } from "@/utils/instance";

export const repairRequest = async (memo: string, serialNos: string[]) => {
  const http = axiosAuthApi();
  return await http
    .post("/request/repair", {
      memo: memo,
      serialNos: serialNos,
    }, {})
    .then((response) => {
      return true;
    })
    .catch((error) => {
      console.log(error.message);
      return false;
    });
};
