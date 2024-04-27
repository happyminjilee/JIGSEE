import styled from "@/styles/modal/rejectedrelease.module.css"
import {Button} from "@nextui-org/react"

interface lst {
    requestedId : string;
    isAccepted : boolean;
    memo : string;
    serialNos : string[];
}


export default function ApprovedRelease() {
    const memo = "좀 아껴써라 이 jig 들아 너희는 그냥 내가 시키는 일만 하면 돼 알았어?"

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
                        color="primary">
                    확인
                </Button>

            </div>
        </>
    );
}
