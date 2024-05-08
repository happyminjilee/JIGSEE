import styled from "@/styles/modal/reuseModal.module.css"
import {Button} from "@nextui-org/react"
import {Input} from "@nextui-org/react"
import {useCompoStore, useWoDetailStore} from "@/store/workorderstore";
import {useState} from "react";
import ClearIcon from "@mui/icons-material/Clear";
import {updateJigStatus} from "@/pages/api/jigAxios"
import {RadioGroup, Radio} from "@nextui-org/react";

interface lst {
    model: string;
    model_name: string;
    count: number;
}


export default function reuseModal() {
    const {setModal, setModalName} = useCompoStore()
    const {jigItemInfo} = useWoDetailStore()
    const close = () => {
        setModal(false)
        setModalName("")
    }

    const [jigPos, setJigPos] = useState("")

    const reUseBtn = () => {
        updateJigStatus(jigItemInfo.serialNo, jigPos)
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
                    Test 결과 재사용 가능
                </div>

                <div
                    className={styled.content}
                >
                    <RadioGroup
                        label="보낼 위치"
                        orientation="horizontal"
                        onValueChange={setJigPos}
                    >
                        <Radio value="WAREHOUSE">창고</Radio>
                        <Radio value="READY">불출 대기</Radio>
                    </RadioGroup>
                </div>


                <Button style={{
                    width: "300px",
                    height: "60px",
                    margin: "10px auto"
                }}
                        color="primary"
                        onPress={() => {reUseBtn()}}
                >
                    보내기
                </Button>

            </div>
        </>
    );
}