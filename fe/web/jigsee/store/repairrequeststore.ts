import {create} from "zustand";
import {useState} from "react";
import {Selection} from "@nextui-org/react"


interface Package {
    id : number,
    status: string,
}

interface Cart {
    cartList: lst[],
    addToCart: (n:lst) => void,
    removeFromCart: (n:lst) => void,
    clearCartList: () => void,
}

interface Mart {
    martList: lst[],
    addToMart: (n:lst) => void,
    removeFromMart: (n:lst) => void,
    clearMart: () => void,
    setMart: (n:lst[]) => void,
}

export const useMartStore = create<Mart>(
    (set) => ({
        martList: [
            {
                createdAt: [2024, 4, 23],
                updatedAt: "",
                creator: "hi",
                terminator: "bye",
                id: 1,
                model: "ModelName",
                serialNo: "S0000001",
                status: "PUBLISH"
            },
            {
                createdAt: [2024, 4,22],
                updatedAt: "",
                creator: "hi2",
                terminator: "bye2",
                id: 2,
                model: "ModelName",
                serialNo: "S0000001",
                status: "PUBLISH"
            },
            {
                createdAt: [2024, 4,21],
                updatedAt: "",
                creator: "hi3",
                terminator: "by3e",
                id: 3,
                model: "ModelName1",
                serialNo: "S0000001",
                status: "PUBLISH"
            },
            {
                createdAt: [2024, 4,20],
                updatedAt: "",
                creator: "hi4",
                terminator: "bye4",
                id: 4,
                model: "ModelName2",
                serialNo: "S0000001",
                status: "PUBLISH"
            },
            {
                createdAt: [2024, 4, 19],
                updatedAt: "",
                creator: "hi5",
                terminator: "bye5",
                id: 5,
                model: "ModelName44",
                serialNo: "S0000001",
                status: "PUBLISH"
            },
            {
                createdAt: [2024, 4,18],
                updatedAt: "",
                creator: "hi",
                terminator: "bye",
                id: 6,
                model: "ModelName55",
                serialNo: "S0000001",
                status: "PROGRESS"
            },
            {
                createdAt: [2024, 4,17],
                updatedAt: "",
                creator: "hi",
                terminator: "bye",
                id: 7,
                model: "ModelName52",
                serialNo: "S0000003",
                status: "PROGRESS"
            },
            {
                createdAt: [2024, 4,16],
                updatedAt: "",
                creator: "hi",
                terminator: "bye",
                id: 8,
                model: "ModelName51",
                serialNo: "S0000021",
                status: "PROGRESS"
            },
            {
                createdAt: [2024, 4,21],
                updatedAt: "",
                creator: "hi",
                terminator: "bye",
                id: 9,
                model: "ModelName43",
                serialNo: "S0000022",
                status: "FINISH"
            },
            {
                createdAt: [2024, 4,21],
                updatedAt: "",
                creator: "hi123",
                terminator: "bye123",
                id: 10,
                model: "ModelName",
                serialNo: "S0000001",
                status: "FINISH"
            },
            {
                createdAt: [2024, 4,21],
                updatedAt: "",
                creator: "hi112",
                terminator: "bye112",
                id: 11,
                model: "ModelName",
                serialNo: "S0000001",
                status: "FINISH"
            },
        ],
        addToMart: (n) => {
            set((state) => ({martList: [...state.martList, n]}))
        },
        removeFromMart: (n) => {
            set((state) => ({martList: state.martList.filter(cargo => cargo.id !== n.id)}))
        },
        clearMart: () => {
            set({martList: []})
        },
        setMart: (n: lst[]) => {
            set({martList: n})
        }
    })
)

export const useCartStore = create<Cart>(
    (set) => ({
       cartList: [
       ],
       addToCart: (n) => {
           set((state) => ({cartList: [...state.cartList, n]}))
       },
       removeFromCart: (n) => {
           set((state) => ({cartList: state.cartList.filter(cargo => cargo.id !== n.id)}))
       },
       clearCartList: () => {
           set({cartList: []})
       }
    })
)

interface groupFilter {
    select: string;
    setSelect: (n:string) => void;
    forFilter: lst[];
    addForFilter: (n:lst[]) => void;
    clearForFilter: () => void;
    removeForMart: (n:lst) => void;
    addForMart: (n:lst) => void;
}

interface lst {
    id: number,
    model: string, // 지그 모델명
    serialNo: string, // 지그 일련번호
    creator: string, // 작성자
    terminator: string, // 작성 종료자
    status: string, // wo 상태
    createdAt: number[], // wo 생성시간
    updatedAt: string, // wo 수정시간
}

export const useGroupFilter = create<groupFilter>(
    (set) => ({
        select: "PUBLISH",
        setSelect: (n:string) => {
            set({select:n})
        },
        forFilter: [],
        addForFilter: (n:lst[]) => {
            set((state) => ({forFilter: [...state.forFilter, ...n]}))
        },
        clearForFilter: () => {
            set({forFilter:[]})
        },
        removeForMart: (n:lst) => {
            set((state) => ({forFilter: state.forFilter.filter(a => a.id !== n.id)}))
        },
        addForMart: (n: lst) => {
            set((state) => ({forFilter: [...state.forFilter, n]}))
        }
    })
)



//////////////////////////////for DND/////////////////////////////////

