import styled from "@/styles/modal/rejectedrelease.module.css"
import {Button} from "@nextui-org/react"

interface lst {
    id : string;
    memo : string;
}


export default function RestoreMemo() {
    const obj =
        {id: "123", memo: "튼튼한 걸로 좀 줘요..."}

    return (
        <>
            <div
                className={styled.container}
            >
                <div
                    className={styled.title}
                >
                    요청 내역
                </div>
                <div
                    style={{margin: "0px auto 20px auto",
                        fontWeight: "bold",
                        fontSize: "medium"}}
                >
                    요청 번호 : {obj.id}
                </div>
                <div
                    className={styled.content}
                >
                    {obj.memo}
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
