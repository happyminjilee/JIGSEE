import {Input, Button, Checkbox, Pagination, Select, SelectItem, Selection, Card} from "@nextui-org/react";
import {useState} from "react";
import styled from "@/styles/Total/Total.module.css";

interface Option {
    label: string;
    value: string
}

interface JigData {
    date: string;
    serialNumber: string;
    model: string;
    status: string; // 가정으로 추가한 속성입니다.
}

interface Props {
    onClick(): void;
}

export default function RepairTotal() {
    // 수리요청온 지그의 WO 에 따른 상태 데이터
    const lst: Option[] = [{label: 'Publish', value: '발행'}, {label: 'Onprogress',value:'진행 중'}, {label: 'complete', value: '완료'}];
    const [values, setValues] = useState<Selection>(new Set(['publish', 'onprogress']))

    // 임시 JIG 데이터
    const jigData: JigData[] = [
        { date: "2024.04.21", serialNumber: "S/N S00000001", model: "Model Name", status: "발행" },
        { date: "2024.04.22", serialNumber: "S/N S00000002", model: "Model Name", status: "발행" },
        { date: "2024.04.23", serialNumber: "S/N S00000003", model: "Model Name", status: "발행" },
        // 다른 JIG 데이터 객체들...
    ];
    function cardClick(jig: JigData) {
            console.log("clicked", jig);
        }


    return (
        <>
            <div className={styled.right}>
                <Select
                    label = "선택"
                    selectionMode="multiple"
                    placeholder="선택"
                    selectedKeys = {values}
                    onSelectionChange={setValues}
                    className={styled.short}
                >
                    {lst.map((option) => (
                        <SelectItem key={option.value} value={option.value}>
                            {option.label}
                        </SelectItem>
                    ))}
                </Select>
            </div>
            <div>
                {jigData.map((jig, index) => (
                    <div
                        key={index}
                        onClick={() => cardClick(jig)}
                        className = {styled.fullWidth}
                    >
                        <h3>{jig.date}</h3>
                        <p>{jig.serialNumber} | {jig.model}</p>
                         {jig.status}
                    </div>
                ))}
            </div>
            <div className={styled.center}>
                <Pagination
                    total={10}
                />
            </div>
        </>
    )
}