import {useState} from "react";
import CheckBoxIcon from "@mui/icons-material/CheckBox";
import ChickBoxOutlineBlankIcon from "@mui/icons-material/CheckBoxOutlineBlank";
import CheckIcon from '@mui/icons-material/Check';
import ClearIcon from '@mui/icons-material/Clear';
import styled from "@/styles/modal/workorder.module.css";
import {isDataView} from "node:util/types";

// enum Status {
//     PUBLISH = "PUBLISH",
//     PROGRESS = "PROGRESS",
//     FINISH = "FINISH"
// }

// 점검항목에 대한 인터페이스 정의
interface CheckListItem {
    uuid: string;       // 점검항목 구분을 위한 id
    content: string;    // 점검항목
    standard: string;   // 기준 값
    measure: string;    // 측정값
    memo: string;       // 비고
    passOrNot: boolean; // 통과 유무
}

// 주 데이터 구조에 대한 인터페이스 정의
interface WorkOrder {
    id: number;                   // 고유 ID
    status: string;               // 상태
    creator: string;              // 생성자
    terminator: string;           // 완료자
    model: string;                // 지그 모델명
    serialNo: string;             // 지그 일련번호
    createdAt: Date;              // wo 생성일
    checkList: CheckListItem[];   // 점검항목 리스트
}

export default function workorder() {
    const [qualification, setQualification] = useState()
    const WorkOrder =
        {
            id: 31112323,
            status: "PUBLISH",
            creator: "주준형",
            terminator: "이민지",
            model: "Secret Weapon",
            serialNo: "S105",
            createdAt: "2024.04.28",
            checkList: [
                {
                    uuid: "abc1",
                    content: "S1",
                    standard: ">500",
                    measure: "700",
                    passOrNot: true,
                    memo: "LGTM",
                },
                {
                    uuid: "abc2",
                    content: "S2",
                    standard: "<490",
                    measure: "500",
                    passOrNot: false,
                    memo: "Not Good",
                },
                {
                    uuid: "abc3",
                    content: "S2",
                    standard: "<490",
                    measure: "500",
                    passOrNot: false,
                    memo: "Not Good",
                },
                {
                    uuid: "abc4",
                    content: "S2",
                    standard: "<490",
                    measure: "500",
                    passOrNot: false,
                    memo: "Not Good",
                },
            ]
        }


    return (
        <>
            <div className={styled.container}>
                <div className={styled.header}>
                    수리 요청 WORK ORDER
                </div>
                <div className={styled.body1}>
                    <div>요청인 : {WorkOrder.creator}</div>
                    <div>요청일 : {WorkOrder.createdAt}</div>
                    <div>NO. {WorkOrder.id}</div>
                </div>
                <div className={styled.checks}>
                    {WorkOrder.status === "PUBLISH" ?
                        <div className={styled.check}>
                            <CheckBoxIcon>
                            </CheckBoxIcon>
                            PUBLISH
                        </div>

                        :
                        <div className={styled.check}>
                            <ChickBoxOutlineBlankIcon>
                            </ChickBoxOutlineBlankIcon>
                            PUBLISH
                        </div>

                    }
                    {WorkOrder.status === "PROGRESS" ?
                        <div className={styled.check}>
                            <CheckBoxIcon>
                            </CheckBoxIcon>
                            PROGRESS
                        </div>

                        :
                        <div className={styled.check}>
                            <ChickBoxOutlineBlankIcon>
                            </ChickBoxOutlineBlankIcon>
                            PROGRESS
                        </div>

                    }
                    {WorkOrder.status === "FINISH" ?
                        <div className={styled.check}>
                            <CheckBoxIcon>
                            </CheckBoxIcon>
                            FINISH
                        </div>
                        :
                        <div className={styled.check}>
                            <ChickBoxOutlineBlankIcon>
                            </ChickBoxOutlineBlankIcon>
                            FINISH
                        </div>

                    }
                </div>
                <div className={styled.body2}>
                    Model : {WorkOrder.model}
                </div>
                <div className={styled.body2}>
                    S/N : {WorkOrder.serialNo}
                </div>
                <hr className={styled.divider}/>

                <div className={styled.body2}>
                    Technician : {WorkOrder.terminator}
                </div>
                <div className={styled.body3}>
                    <div className={styled.compo3}>
                        <div>
                            Test Start
                        </div>
                        <div>
                            asdf
                        </div>
                    </div>
                    <div className={styled.compo3}>
                        <div>
                            Test End
                        </div>
                        <div>
                            asdf
                        </div>
                    </div>
                </div>

                <div className={styled.body4}>
                    <div className={styled.compo4}>
                        <div
                            style={{
                                display: "flex",
                                flexDirection: "row",
                                justifyContent: "flex-start",
                                width: "350px",
                                padding: "1px 0px 1px 10px",
                                margin: "10px 0px 0px 15px",
                            }}
                        >
                            Test Result
                        </div>
                            <table
                                style={{
                                    width: "80%",
                                    borderCollapse: "collapse",
                                    margin: "10px auto",
                                    maxHeight: "130px",
                                }}
                            >
                                <thead>
                                <tr className={styled.cell}>
                                    <th className={styled.cell}>Content</th>
                                    <th className={styled.cell}>Standard</th>
                                    <th className={styled.cell}>Measure</th>
                                    <th className={styled.cell}>Pass/Fail</th>
                                </tr>
                                </thead>
                                <tbody
                                    style={{
                                        overflowY: "scroll",
                                    }}
                                >
                                {WorkOrder.checkList.map((check, index) => (
                                    <tr key={check.uuid} className={styled.cell}>
                                        <td className={styled.cell}>{check.content}</td>
                                        <td className={styled.cell}>{check.standard}</td>
                                        <td className={styled.cell}>{check.measure}</td>
                                        <td className={styled.cell}>
                                            {check.passOrNot ? <CheckIcon></CheckIcon>: <ClearIcon></ClearIcon>}
                                        </td>
                                    </tr>
                                ))}
                                </tbody>
                            </table>
                    </div>
                    <div className={styled.compo41}>
                        <div
                            style={{
                               marginTop: "10px",
                            }}
                        >
                            Qualification
                        </div>
                        {}
                    </div>
                </div>
            </div>
        </>
    );
}