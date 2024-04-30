import {create} from "zustand"
import {useEffect, useState} from "react";

interface user {
    name: string;
    setName: (n:string) => void;
    role: string;
    setRole: (n:string) => void;
}

export const userStore = create<user>(
    (set) => ({
        name: "",
        role: "",
        setName: (name) => set(() => ({name})),
        setRole: (role) => set(() => ({role})),
    })
)

