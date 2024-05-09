import styled from "@/styles/modal/createwo.module.css"
import {Button} from "@nextui-org/react"
import {Input} from "@nextui-org/react"
import {useCompoStore, useUserWoListStore, useWoGroupStore} from "@/store/workorderstore";
import {useState} from "react";
import {createWo} from "@/pages/api/workorderAxios";
import ClearIcon from "@mui/icons-material/Clear";

interface lst {
    model: string;
    model_name: string;
    count: number;
}


export default function createWoModal() {
    const {
        modal, setModal,
        modalName, setModalName} = useCompoStore()
    const {fetchWoGroup} = useWoGroupStore()
    const [serialNumber, setSerialNumber] = useState("");
    const submit = () => {
        if (serialNumber.trim() === "") {
            alert("시리얼 번호를 입력해 주세요");
            return
        } else {
            createWo(serialNumber)
                .then((res) => {
                    console.log(res)
                    alert("요청 완료")
                })
                .catch((error) => {
                    console.log(error.message)
                    alert("요청 실패")
                })
                .finally(() => {
                    fetchWoGroup()
                    setModal(false)
                    setModalName("")
                })
        }
    }

    const close = () => {
        setModal(false)
        setModalName("")
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
                    요청 지그 입력
                </div>
                
                <div
                    className={styled.content}
                >
                    <Input
                        type="text"
                        variant={"underlined"}
                        onChange={(e) => setSerialNumber(e.target.value)}
                    />
                </div>

                <Button style={{
                    width: "300px",
                    height: "60px",
                    margin: "10px auto"
                }}
                        color="primary"
                        onPress={() => {submit()}}
                >
                    확인
                </Button>

            </div>
        </>
    );
}