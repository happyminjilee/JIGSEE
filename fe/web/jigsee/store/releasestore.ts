import {create} from "zustand"
import {userStore} from "@/store/memberstore";
import {releaseGet, releaseDetailGet} from "@/pages/api/releaseAxios";
import {AxiosResponse} from "axios";



interface lst {
    id: string, // 요청 uuid
    status: string,
    from: string, // 요청자
    to: string, // 승인자
    createAt: string, // 요청시간
    updatedAt: string, // 처리 시간
}

interface release {
    isManager: boolean,
    currentPage: number,
    endPage: number,
    list: lst[],
    fetchRelease: (status:string, page:number, size: number) => Promise<AxiosResponse>;
}


interface releaseDetail {
    isManager: boolean,
    id: string, // 요청 id
    status: string,
    from: string, // 요청자
    to: string, // 승인자
    memo: string, // 사유
    createAt: string, // 요청시간
    updatedAt: string,
    serialNos: string[], // 요청 지그 리스트
    fetchReleaseDetail: (id:string) => Promise<AxiosResponse>,
}

interface modalState {
    isClose: boolean,
    setClose: (n:boolean) => void;
}

export const useReleaseStore = create<release>(
    (set) => ({
        isManager: false,
        currentPage: 1,
        endPage: 1,
        list: [],
        fetchRelease: async (status: string, page: number, size: number) => {
            const data = await releaseGet(status, page, size);
            set({
                isManager: data.data.result.isManager,
                currentPage: data.data.result.currentPage,
                endPage: data.data.result.endPage,
                list: data.data.result.list,
            })
            return data
        }
    })
)

export const useReleaseDetailStore = create<releaseDetail>(
    (set) => ({
        isManager: false,
        id: "", // 요청 id
        status: "",
        from: "", // 요청자
        to: "", // 승인자
        memo: "", // 사유
        createAt: "", // 요청시간
        updatedAt: "",
        serialNos: [], // 요청 지그 리스트
        fetchReleaseDetail: async (id: string) => {
            const data = await releaseDetailGet(id);
            set({
                isManager: data.data.result.isManager,
                id: data.data.result.id, // 요청 id
                status: data.data.result.status,
                from: data.data.result.from, // 요청자
                to: data.data.result.to, // 승인자
                memo: data.data.result.memo, // 사유
                createAt: data.data.result.createAt, // 요청시간
                updatedAt: data.data.result.updatedAt,
                serialNos: data.data.result.serialNos, // 요청 지그 리스트
            })
            return data.data;
        }
    })
)


export const useReleaseModalStore = create<modalState>(
    (set) => ({
        isClose: false,
        setClose: (newClose) => {
            set({isClose: newClose})
        }
    })
)








