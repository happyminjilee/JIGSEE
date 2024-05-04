import React, {useEffect, useState, useRef, ForwardedRef} from "react";
import { Link, Button } from "@nextui-org/react";
import styled from "@/styles/repairrequest.module.css";
import { list } from "postcss";
import {updateWoList, createWo} from "@/pages/api/workorderAxios";
import {useCompoStore} from "@/store/workorderstore";
import Box from "@mui/material/Box";
import WoModal from "@/components/workorder/CreateWoModal";
import Report from "@/components/workorder/template";
import Modal from "@mui/material/Modal";
import ClearIcon from "@mui/icons-material/Clear";
import {useDrop, useDrag} from "react-dnd";
import {useCartStore, useItemStore} from "@/store/repairrequeststore";
import {DnDWrapper} from "@/components/workorder/DndWrapper";


interface lst {
    id: number;
    createdAt: string;
    model: string;
    serialNo: string;
    status: string;
}

interface cardProps {
    id: number;
    createdAt: string;
    model: string;
    serialNo: string;
    status: string;
}

interface card {
    isDragging: boolean;
    dragData: cardProps;
}


export default function RequestList() {
    const lst = [
        {
            createdAt: "2024.04.26",
            id: 123123,
            model: "ModelName",
            serialNo: "S0000001",
            status: "PUBLISH"
        },
        {
            createdAt: "2024.04.26",
            id: 123123,
            model: "ModelName",
            serialNo: "S0000001",
            status: "PUBLISH"
        },
        {
            createdAt: "2024.04.26",
            id: 123123,
            model: "ModelName",
            serialNo: "S0000001",
            status: "PUBLISH"
        },
        {
            createdAt: "2024.04.26",
            id: 123124,
            model: "ModelName",
            serialNo: "S0000002",
            status: "PROGRESS"
        },
        {
            createdAt: "2024.04.26",
            id: 123125,
            model: "ModelName",
            serialNo: "S0000003",
            status: "PROGRESS"
        },
        {
            createdAt: "2024.04.26",
            id: 123125,
            model: "ModelName",
            serialNo: "S0000003",
            status: "PROGRESS"
        },
        {
            createdAt: "2024.04.26",
            id: 123125,
            model: "ModelName",
            serialNo: "S0000003",
            status: "PROGRESS"
        },
        {
            createdAt: "2024.04.26",
            id: 123125,
            model: "ModelName",
            serialNo: "S0000003",
            status: "PROGRESS"
        },
        {
            createdAt: "2024.04.26",
            id: 123125,
            model: "ModelName",
            serialNo: "S0000003",
            status: "PROGRESS"
        },
    ];
    const {setRightCompo} = useCompoStore()
    const cardClick = (requestId: number) => () => {
        console.log("clicked", requestId)
    }

    const openModal = () => {
    //     모달 열어서 wo 생성 마무리
        setModal(true)
    }

    const requestPost = () => {
        // 리스트로 담아서 상태 변화

        // store 설정, 담아있는 리스트를 반환
        updateWoList([{id: 0, status: "PUBLISH"}])
    }
    const {modalName, setModalName, modal, setModal} = useCompoStore()
    const createWo = () => {
        openModal()
        setModalName("CREATEWO")
    }

    // 수락할 아이템 설정
    const {item, setItem} = useItemStore()
    const dropRef = useRef(null);
    const dragRef = useRef(null);
    const {cartList, addToCart, clearCartList, removeFromCart} = useCartStore()
    const whenDragging = () => {
        console.log("드래그중!")
    }
    const wheDragEnd = () => {
        console.log("드래그 끝낫을때!")
    }
    const [selected, setSelected ]= useState([])

    const Card = React.forwardRef(
        (
            {dragData, isDragging}:card,
            ref: ForwardedRef<HTMLElement>
        ) => (
            <div
                key={dragData.id}
                className={styled.card}
                onClick={() => {
                    cardClick(dragData.id)
                }}>
                <div className={styled.division1}>
                    <div className={styled.date}>
                        {dragData.createdAt[0]}.{dragData.createdAt[1]}.{dragData.createdAt[2]}
                    </div>
                    <div className={styled.title}>
                        {dragData.serialNo} | {dragData.model}
                    </div>
                </div>

                <div className={styled.division2}>{dragData.status}</div>
            </div>
        )
    )

    return (
        <>
            <div className={styled.box}>
                <div style={{display: "flex", justifyContent: "space-between", marginBottom: "15px"}}>
                    <div style={{fontWeight: "bold", fontSize: "15px"}}>재고 불출 요청 내역</div>
                    <div
                        className = {styled.clear}
                        onClick={() => {setRightCompo("")}}
                    >
                        <ClearIcon/>
                    </div>
                </div>
                <div
                    ref={dropRef}
                    className={styled.contents}
                >
                    <DnDWrapper dragList={selected} onDragEnd={wheDragEnd} onDragging={whenDragging} dragSectionName={"cart"}>
                        {(item, ref, isDragging) => (
                            <Card
                                dragData={item}
                                isDragging={isDragging}
                                ref={ref}
                            />
                        )}
                    </DnDWrapper>
                    {/* card */}
                    {lst.map((info, index) => (
                        <div
                            key={index}
                            className={styled.card}
                            onClick={cardClick(info.id)}
                        >
                            <div
                                className={styled.division1}
                            >
                                <div
                                    className={styled.date}
                                >
                                    {info.createdAt}
                                </div>
                                <div
                                    className={styled.title}
                                >
                                    {info.serialNo} | {info.model}
                                </div>
                            </div>

                            <div
                                className={styled.division2}
                            >
                                {info.status}
                            </div>
                        </div>
                    ))}
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
                    <Button
                        size="lg"
                        style={{
                            fontWeight: "bold",
                            marginBottom: "5px",
                            margin: "10px, 0px, 0px, 0px",
                            width: "190px",
                        }}
                        onPress={() => {createWo()}}
                    >
                        WO 생성
                    </Button>
                </div>
            </div>
            <Modal
                open={modal} // Corrected from 'open'
                onClose={()=> {setModal(false)}} // Added onClose handler
                aria-labelledby="modal-modal-title"
                aria-describedby="modal-modal-description"
                sx={{
                    display: "flex",
                    alignItems: "center",
                    justifyContent: "center",
                    '& .MuiBox-root': {  // Assuming the box is causing issues
                        outline: 'none',
                        border: 'none',
                        boxShadow: 'none'
                    }
                }}
            >

                <Box
                    sx={{
                        width: "100%",
                        height: "80%",
                        display: "flex",
                        alignItems: "center",
                        justifyContent: "center",
                    }}
                >
                    <WoModal/>
                </Box>
            </Modal>
        </>
    );
}
