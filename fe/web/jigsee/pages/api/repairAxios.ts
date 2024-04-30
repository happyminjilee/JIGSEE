import {axiosApi, axiosAuthApi} from "@/utils/instance";


export const repairRequest = async (
    memo: string,
    serialNos: string[],
) => {
    const http = axiosAuthApi()
    return await http
        .post(
            'http://k10s105.p.ssafy.io/api/v1/request/repair',
            {
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