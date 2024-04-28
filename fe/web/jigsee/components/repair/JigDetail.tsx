import React, { useState } from "react";
import { Link, Button } from "@nextui-org/react";
import styled from "@/styles/jigdetail.module.css";
import ArrowForwardIosIcon from "@mui/icons-material/ArrowForwardIos";
import Stepper from "@/components/repair/stepper"
import { list } from "postcss";
import {fontStyle, fontWeight} from "@mui/system";

interface lst {
    id: number;
    stauts: string;
    creator: string;
    terminator: string;
    model: string;
    serialNo: string;
    createdAt: string;
}



export default function RequestList() {
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

    const cardClick = (id: number) => () => {
        console.log("clicked", id)
    }

    return (
        <>
            <div className={styled.box}>
                <div
                    className={styled.head}
                >
                    INFO
                </div>

                {/*jig 정보*/}
                <div
                    className={styled.first}
                >
                    <div className={styled.card}>Model : {lst[0].model}</div>
                    <div className={styled.card}>S/N : {lst[0].serialNo}</div>
                    <div className={styled.card}>생성자 : {lst[0].creator}</div>
                    <div className={styled.card}>생성일 : {lst[0].createdAt}</div>
                </div>

                {/* Work order 이동 */}
                <div
                    className={styled.second}
                    onClick={cardClick(lst[0].id)}
                >
                    <div style={{margin: "auto 0px auto 40px"}}>Work Order : {lst[0].id}</div>
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

                    {lst[0].status === 'PUBLISH' &&
                        <div
                            className={styled.button}
                        >
                            <Button
                                color={"primary"}
                                style={{width: "180px"}}
                            >
                                담기
                            </Button>
                        </div>
                            }


                    {lst[0].status === 'PROGRESS' &&
                        <div
                            className={styled.button}
                        >
                            <Button
                                color={"primary"}
                                style={{
                                    width: "180px",
                                }}
                            >
                                Work Order 수정
                            </Button>
                            <Button
                                color={"primary"}
                                style={{
                                    width: "180px",

                                }}
                            >
                                제출
                            </Button>
                        </div>
                    }
                </div>
            </div>
        </>
    );
    }
