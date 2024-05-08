import React, {useEffect, useState, useRef, ForwardedRef} from "react";
import { Link, Button } from "@nextui-org/react";
import styled from "@/styles/repairrequest.module.css";
import {updateWoList, createWo} from "@/pages/api/workorderAxios";
import {useCompoStore, useWoGroupStore} from "@/store/workorderstore";
import Box from "@mui/material/Box";
import WoModal from "@/components/workorder/CreateWoModal";
import Modal from "@mui/material/Modal";
import ClearIcon from "@mui/icons-material/Clear";
import {useCartStore, useGroupFilter, useMartStore,} from "@/store/repairrequeststore";
import {DropBox} from "@/components/workorder/ListDnDbox"



export default function RequestList() {
    const {modalName, setModalName, modal, setModal, setRightCompo} = useCompoStore()
    const {cartList, clearCartList, removeFromCart, addToCart} = useCartStore()
    const {setMart} = useMartStore()
    const {fetchWoGroup, publish, progress, finish} = useWoGroupStore()
    const {addForFilter, clearForFilter, select, setSelect} = useGroupFilter()
    const forRequest = cartList.map((a) => ({id: a.id, status: "PROGRESS"}))

    const openModal = () => {
    //     모달 열어서 wo 생성 마무리
        setModal(true)
    }

    const requestPost = () => {
        // 리스트로 담아서 상태 변화
        console.log('cartList', cartList)
        console.log('forRequest', forRequest)
        // store 설정, 담아있는 리스트를 반환
        updateWoList(forRequest)
            .then((res) => {
                window.alert("요청 완료")
                console.log(res)
            })
            .catch((error) => {
                console.log(error.message)
                window.alert("요청 실패!")
            })
            .finally(() => {
                clearCartList()
                fetchWoGroup()
                    .then((res) => {
                        console.log('after request', res)
                    })
            })

    }

    const createWo = () => {
        openModal()
        setModalName("CREATEWO")
    }

    return (
        <>
            <div className={styled.box}>
                <div style={{display: "flex", justifyContent: "space-between", marginBottom: "15px"}}>
                    <div style={{fontWeight: "bold", fontSize: "15px"}}> 수리 요청 </div>
                    <div
                        className = {styled.clear}
                        onClick={() => {setRightCompo("")}}
                    >
                        <ClearIcon/>
                    </div>
                </div>
                <div
                    className={styled.contents}
                >
                    {/* ///////////////////card///////////////////////////////// */}
                <DropBox items={cartList} boxType={"Cart"}>

                </DropBox>
                    {/* ///////////////////card///////////////////////////////// */}
                </div>
                {/*버튼 칸*/}
                <div className={styled.button}>
                    <Button
                        color="primary"
                        size="lg"
                        style={{
                            fontWeight: "bold",
                            marginBottom: "5px",
                            margin: "10px, 0px, 0px, 0px",
                            width: "190px"
                        }}
                        onPress={() => {requestPost()}}
                    >
                        요 청
                    </Button>
                </div>
            </div>
        </>
    );
}
