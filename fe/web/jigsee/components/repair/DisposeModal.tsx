import styled from "@/styles/modal/disposeModal.module.css"
import {Button} from "@nextui-org/react"
import {Input} from "@nextui-org/react"
import {useCompoStore, useWoDetailStore} from "@/store/workorderstore";
import {useState} from "react";
import ClearIcon from "@mui/icons-material/Clear";
import {updateJigStatus} from "@/pages/api/jigAxios"

interface lst {
    model: string;
    model_name: string;
    count: number;
}

export default function disposeModal() {
    const {setModal, setModalName} = useCompoStore()
    const {jigItemInfo} = useWoDetailStore()
    const close = () => {
        setModal(false)
        setModalName("")
    }

    const disposeBtn = () => {
        updateJigStatus(jigItemInfo.serialNo, "DELETE")
    }

    return (
        <>
            <div
                className={styled.container}
            >
                <div
                    className={styled.close}
                    onClick={() => {close()}}
                >
                    <ClearIcon/>
                </div>
                <div
                    className={styled.title}
                >
                    지그 폐기
                </div>

                <div
                    className={styled.content}
                >
                    Test 결과 재사용 부적합 판정
                </div>

                <Button style={{
                    width: "300px",
                    height: "60px",
                    margin: "10px auto"
                }}
                        color="primary"
                        onPress={() => {disposeBtn()}}
                >
                    폐 기
                </Button>

            </div>
        </>
    );
}