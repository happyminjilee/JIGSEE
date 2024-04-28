import { useState } from "react";
import { Link, Button } from "@nextui-org/react";
import styled from "@/styles/repairrequest.module.css";
import { list } from "postcss";

interface lst {
    id: number;
    createdAt: string;
    model: string;
    serialNo: string;
    status: string;
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

    const cardClick = (requestId: number) => () => {
        console.log("clicked", requestId)
    }

    return (
        <>
            <div className={styled.box}>
                <div style={{display: "flex", justifyContent: "space-between", marginBottom: "15px"}}>
                    <div style={{fontWeight: "bold", fontSize: "15px"}}>재고 불출 요청 내역</div>
                </div>
                <div
                    className={styled.contents}
                >
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
                    >
                        WO생성
                    </Button>
                </div>
            </div>
        </>
    );
}
