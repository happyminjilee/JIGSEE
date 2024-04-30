import {create} from "zustand"
import {getfacility, getfacilitylist} from "@/pages/api/facilityAxios";


interface facilities {
    id: number,
    model: string,
    facilitySerialNo: string,
}

interface jigs {
    id: number,
    model: string,
    facilitySerialNo: string,
    status: string,
    expectLife: string,
    checkType: string,
    repairCount: number,
    checkCount: number,
}

interface facility{
    id: number,
    model: string,
    facilitySerialNo: string,
    jigs: Array<jigs>,
}


interface FacilityStore {
    facilities: facilities[];
    loadFacilities: () => Promise<void>;
}










