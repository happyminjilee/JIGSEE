import { axiosApi, axiosAuthApi } from "@/utils/instance";

// 담아놓은 지그들의 s/n 들을 담아서 요청 보낸 후
// boolean 값 반환
export const releaseRequest = async (serialNos: string[]) => {
  const http = axiosAuthApi();
  return await http
    .post("http://k10s105.p.ssafy.io/api/v1/request/jig", {
      serialNos: serialNos,
    })
    .then((response) => {
      console.log(response, "불출요청 성공");
      return true;
    })
    .catch((error) => {
      console.log(error.message);
      console.log(error);
      return false;
    });
};

// 승인 혹은 반려 버튼 클릭시 요청에 관련된 정보 반환
// isAccept 로 구분 승인시 serialNos, 반려시 memo
// boolean 값 반환
export const releaseResponse = async (
  requestId: string,
  isAccept: boolean,
  memo: string,
  serialNos: string[]
) => {
  const http = axiosAuthApi();

  return await http
    .put("http://k10s105.p.ssafy.io/api/v1/jig-item/accept", {
      requestId: requestId,
      isAccept: isAccept,
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

//불출정보 조회 (전체 조회에서 페이지, 필터 기능)
export const releaseGet = async (
  // status => ALL, PUBLISH, FINISH, REJECT
  status: string,
  page: number,
  size: number
) => {
  const http = axiosAuthApi();
  return await http
    .get("http://k10s105.p.ssafy.io/api/v1/request/jig/all", {
      params: {
        filter: status,
        page: page,
        size: size,
      },
    })
    .then((response) => {
      return response.data.result;
    })
    .catch((error) => {
      console.log(error.message);
      return false;
    });
};

export const releaseDetailGet = async (
  // status => ALL, PUBLISH, FINISH, REJECT
  id: string
) => {
  const http = axiosAuthApi();
  return await http
    .get("http://k10s105.p.ssafy.io/api/v1/request/jig/detail", {
      params: {
        "request-jig-id": id,
      },
    })
    .then((response) => {
      return response.data.result;
    })
    .catch((error) => {
      console.log(error.message);
      return false;
    });
};
