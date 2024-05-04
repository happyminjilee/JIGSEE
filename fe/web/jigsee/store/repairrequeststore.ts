import {create} from "zustand";
import {useState} from "react";
import {Selection} from "@nextui-org/react"


interface Package {
    id : number,
    status: string,
}

interface Cart {
    cartList: Package[],
    addToCart: (n:Package) => void,
    removeFromCart: (n:Package) => void,
    clearCartList: () => void,
}

export const useCartStore = create<Cart>(
    (set) => ({
       cartList: [],
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
        }
    })
)



//////////////////////////////for DND/////////////////////////////////

interface dragItem {
    item: string,
    setItem: (n:string) => void,
}

export const useItemStore = create<dragItem>(
    (set) => ({
        item: "repair",
        setItem: (n:string) => {
            set({item: n})
        }
    })
)