import {axiosApi, axiosAuthApi} from "@/utils/instance";

// 담아놓은 지그들의 s/n 들을 담아서 요청 보낸 후
// boolean 값 반환
export const releaseRequest = async (
    serialNos:string[]
) => {
    const http = axiosAuthApi()
    return await http
        .post(
            'http://k10s105.p.ssafy.io/api/v1/request/jig',
            {
                serialNos: serialNos
            }
        )
        .then((response) => {
            return true
        })
        .catch((error) => {
            console.log(error.message)
            return false
        })
}

// 승인 혹은 반려 버튼 클릭시 요청에 관련된 정보 반환
// isAccept 로 구분 승인시 serialNos, 반려시 memo
// boolean 값 반환
export const releaseResponse = async (
    requestId: number,
    isAccept: boolean,
    memo: string,
    serialNos: string[],
) => {
    const http = axiosAuthApi()

    return await http
        .post(
            'http://k10s105.p.ssafy.io/api/v1/response/jig',
            {
                requestId: requestId,
                isAccept: isAccept,
                memo: memo,
                serialNos: serialNos,
            }
        )
        .then((response) => {
            return true
        })
        .catch((error) => {
            console.log(error.message)
            return false
        })
}