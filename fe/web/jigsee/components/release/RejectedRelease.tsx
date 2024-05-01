import styled from "@/styles/modal/rejectedrelease.module.css"
import {Button, modal} from "@nextui-org/react"
import React from "react";
import {useReleaseDetailStore, useReleaseModalStore} from "@/store/releasestore"

interface lst {
    requestedId : string;
    isAccepted : boolean;
    memo : string;
    serialNos : string[];
}


export default function RejectedRelease() {
    const {memo} = useReleaseDetailStore()
    const {isClose, setClose} = useReleaseModalStore()
    return (
        <>
            <div
                className={styled.container}
            >
                <div
                    className={styled.title}
                >
                    반려 사유
                </div>

                <div
                    className={styled.content}
                >
                    {memo}
                </div>

                <Button style={{
                    width: "300px",
                    height: "60px",
                    margin: "10px auto"
                }}
                        color="primary"
                        onPress={() => {setClose(false)}}
                >
                    확인
                </Button>

            </div>
        </>
    );
}
