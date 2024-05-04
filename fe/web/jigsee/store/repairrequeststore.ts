import {create} from "zustand";
import {useState} from "react";


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