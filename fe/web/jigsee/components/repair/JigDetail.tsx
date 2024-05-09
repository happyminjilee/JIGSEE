import React, { useState, useEffect } from "react";
import {Link, Button, user} from "@nextui-org/react";
import styled from "@/styles/jigdetail.module.css";
import ArrowForwardIosIcon from "@mui/icons-material/ArrowForwardIos";
import Stepper from "@/components/repair/stepper"
import ClearIcon from '@mui/icons-material/Clear';
import Box from "@mui/material/Box";
import Report from "@/components/workorder/template";
import Modal from "@mui/material/Modal";
import {useCompoStore, useWoDetailStore, useWoGroupStore} from "@/store/workorderstore";
import {doneWo} from "@/pages/api/workorderAxios";
import WoModal from "@/components/workorder/CreateWoModal";
import {useGroupFilter} from "@/store/repairrequeststore";



interface APIChecklist {
    uuid: string,
    content: string, // 점검항목
    standard: string, // 기준 값
    measure: string,
    memo: string,
    passOrNot: boolean,
}

export default function RequestList() {
    const {
        modal, setModal,
        rightCompo, setRightCompo,
        modalName, setModalName,
    } = useCompoStore();
    // 지그점검항목 입력 모달
    const openModal = () => setModal(true);
    const closeModal = () => setModal(false);
    const {
        id, creator, status,
        createdAt, jigItemInfo, checkList
    } = useWoDetailStore()
    const {publish} = useWoGroupStore()
    const {clearForFilter, addForFilter} = useGroupFilter()
    const cardClick = (id: number) => {
        console.log("clicked", id)
        openModal()
        setModalName("REPORT")
    }

    const {setSelect} = useGroupFilter()
    const goRequest = () => {
        setRightCompo("REQUEST")
        setSelect("PUBLISH")
        clearForFilter()
        addForFilter(publish)
    }

    const goTest = () => {
        console.log(id)
        setRightCompo("TEST")
        setModalName("")
    }


    const [transformed, setTransformed ]= useState<APIChecklist[]>([])
    useEffect(() => {
        const transformed = checkList.map(item => ({
            uuid: item.uuid,
            measure: item.measure,
            memo: item.memo,
            passOrNot: item.passOrNot,
            content: item.content, // 점검항목
            standard: item.standard, // 기준 값
        }));
        setTransformed(transformed);
    }, [])



    return (
        <>
            <div className={styled.box}>
                <div
                    className={styled.head}
                >
                    <div>INFO</div>
                    <div
                        className = {styled.clear}
                        onClick={() => {setRightCompo("")}}
                    >
                        <ClearIcon/>
                    </div>
                </div>

                {/*jig 정보*/}
                <div
                    className={styled.first}
                >
                    <div className={styled.card}>Model : {jigItemInfo.model}</div>
                    <div className={styled.card}>S/N : {jigItemInfo.serialNo}</div>
                    <div className={styled.card}>생성자 : {creator}</div>
                    <div className={styled.card}>생성일 : {createdAt[0]}. {createdAt[1]}. {createdAt[2]} </div>
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
                        className={styled.middleBox}
                    >
                        <div
                            className={styled.state}
                        >
                            <Stepper></Stepper>
                        </div>
                        {status === 'PUBLISH' &&
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


                        {status === 'PROGRESS' &&
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
                            </div>
                        }
                    </div>
                </div>
            </div>
        </>
    );
    }
