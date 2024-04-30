import {create} from "zustand"
import {userStore} from "@/store/memberstore";


const role = userStore((state) => state.role)
const checkManager = (role === "MANAGER")

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
    serialNos: string[] // 요청 지그 리스트
}

export const useReleaseStore = create<release>(
    (set) => ({
        isManager: checkManager,
        currentPage: 0,
        endPage: 0,
        list: [],
    })
)