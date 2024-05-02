import React, { useState, useEffect } from "react";
import {Link, Button, user} from "@nextui-org/react";
import styled from "@/styles/jigdetail.module.css";
import ArrowForwardIosIcon from "@mui/icons-material/ArrowForwardIos";
import Stepper from "@/components/repair/stepper"
import ClearIcon from '@mui/icons-material/Clear';
import Box from "@mui/material/Box";
import Report from "@/components/workorder/template";
import Modal from "@mui/material/Modal";
import {useCompoStore, useWoDetailStore} from "@/store/workorderstore";
import {doneWo} from "@/pages/api/workorderAxios"

interface lst {
    id: number;
    stauts: string;
    creator: string;
    terminator: string;
    model: string;
    serialNo: string;
    createdAt: string;
}

interface APIChecklist {
    uuid: string,
    measure: string,
    memo: string,
    passOrNot: boolean,
}

export default function RequestList() {
    const { modal, setModal, rightCompo, setRightCompo } = useCompoStore();
    // 지그점검항목 입력 모달
    const openModal = () => setModal(true);
    const closeModal = () => setModal(false);

    const {id, creator, createdAt, jigItemInfo, checkList } = useWoDetailStore()


    const lst = [
        {
            id: 12222,
            status: "PUBLISH",
            creator: "주준형",
            terminator: "이민지",
            model: "razer",
            serialNo: "S10P31S105",
            createdAt: "2024.04.28"
        },
        {
            id: 12221,
            status: "PUBLISH",
            creator: "주준형",
            terminator: "이민지",
            model: "mulnir",
            serialNo: "S10P31S105",
            createdAt: "2024.04.27"
        },
        {
            id: 12220,
            status: "PUBLISH",
            creator: "주준형",
            terminator: "이민지",
            model: "ironman",
            serialNo: "S10P31S105",
            createdAt: "2024.04.26"
        },
        {
            id: 12219,
            status: "PROGRESS",
            creator: "주준형",
            terminator: "이민지",
            model: "repulse",
            serialNo: "S10P31S105",
            createdAt: "2024.04.25"
        },
        {
            id: 12218,
            status: "FINISH",
            creator: "주준형",
            terminator: "이민지",
            model: "hawk-eye",
            serialNo: "S10P31S105",
            createdAt: "2024.04.24"
        },

    ]

    const cardClick = (id: number) => {
        console.log("clicked", id)
        openModal()
    }

    const goRequest = () => {
        setRightCompo("REQUEST")
    }

    const goTest = () => {
        setRightCompo("TEST")
    }


    const [transformed, setTransformed ]= useState<APIChecklist[]>([])
    useEffect(() => {
        const transformed = checkList.map(item => ({
            uuid: item.uuid,
            measure: item.measure,
            memo: item.memo,
            passOrNot: item.passOrNot
        }));
        setTransformed(transformed);
    }, [])


    const testPut = () => {
        doneWo(id, transformed)
            .then((res) => {
                console.log(res)
            })
            .catch((error) => {
                console.log(error.message)
            })
    }

    return (
        <>
            <div className={styled.box}>
                <div
                    className={styled.head}
                >
                    <div>INFO</div>
                    <ClearIcon/>
                </div>

                {/*jig 정보*/}
                <div
                    className={styled.first}
                >
                    <div className={styled.card}>Model : {jigItemInfo.model}</div>
                    <div className={styled.card}>S/N : {jigItemInfo.serialNo}</div>
                    <div className={styled.card}>생성자 : {creator}</div>
                    <div className={styled.card}>생성일 : {createdAt}</div>
                </div>

                {/* Work order 이동 */}
                <div
                    className={styled.second}
                    onClick={() =>{cardClick(id)}}
                >
                    <div style={{margin: "auto 0px auto 40px"}}>Work Order : {id}</div>
                    <div style={{margin: "auto 40px auto 0px"}}><ArrowForwardIosIcon /></div>
                </div>

                {/* button */}
                <div
                    className={styled.third}
                >
                    <div
                        className={styled.title}
                    >
                        수리 진행 상황
                    </div>
                    <div
                        className={styled.state}
                    >
                        <Stepper></Stepper>
                    </div>

                    {jigItemInfo.status === 'PUBLISH' &&
                        <div
                            className={styled.button}
                        >
                            <Button
                                color={"primary"}
                                style={{width: "180px"}}
                                onPress={() => {goRequest()}}
                            >
                                담기
                            </Button>
                        </div>
                            }


                    {jigItemInfo.status === 'PROGRESS' &&
                        <div
                            className={styled.button}
                        >
                            <Button
                                color={"primary"}
                                style={{
                                    width: "180px",
                                }}
                                onPress={() => {goTest()}}
                            >
                                Test 결과 입력
                            </Button>
                            <Button
                                color={"primary"}
                                style={{
                                    width: "180px",
                                }}
                                onPress={() => {testPut()}}
                            >
                                제출
                            </Button>
                        </div>
                    }
                </div>
            </div>
            <Modal
                open={modal} // Corrected from 'open'
                onClose={closeModal} // Added onClose handler
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
                    <Report/>
                </Box>
            </Modal>
        </>
    );
    }
