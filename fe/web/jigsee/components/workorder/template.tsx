import {useState, useEffect} from "react";
import CheckBoxIcon from "@mui/icons-material/CheckBox";
import ChickBoxOutlineBlankIcon from "@mui/icons-material/CheckBoxOutlineBlank";
import CheckIcon from '@mui/icons-material/Check';
import ClearIcon from '@mui/icons-material/Clear';
import styled from "@/styles/modal/workorder.module.css";
import {useWoDetailStore} from "@/store/workorderstore"


export default function workorder() {
    const {id, status, createdAt, creator, checkList, jigItemInfo,terminator, updatedAt} = useWoDetailStore()
    const [qualification, setQualification] = useState(false)
    useEffect(() => {
        const allQualified = checkList.every(item=> item.passOrNot)
        setQualification(allQualified)
    }, []);
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
                    <div>요청인 : {creator}</div>
                    <div>요청일 : {createdAt}</div>
                    <div>NO. {id}</div>
                </div>
                <div className={styled.checks}>
                    {status === "PUBLISH" ?
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
                    {status === "PROGRESS" ?
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
                    {status === "FINISH" ?
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
                    Model : {jigItemInfo.model}
                </div>
                <div className={styled.body2}>
                    S/N : {jigItemInfo.serialNo}
                </div>
                <hr className={styled.divider}/>

                <div className={styled.body2}>
                    Technician : {terminator}
                </div>
                <div className={styled.body3}>
                    <div className={styled.compo3}>
                        <div>
                            지그 수리 횟수
                        </div>
                        <div>
                            {jigItemInfo.repairCount}
                        </div>
                    </div>
                    <div className={styled.compo3}>
                        <div>
                            Test End
                        </div>
                        <div>
                            {updatedAt}
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
                        <div>
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
                                {checkList.map((check, index) => (
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
                    </div>
                    <div className={styled.compo41}>
                        <div
                            style={{
                               marginTop: "10px",
                            }}
                        >
                            Qualification
                        </div>
                        <div className={styled.compo42}>
                            {qualification ? <CheckIcon/>: <ClearIcon/>}
                        </div>
                        <div></div>

                    </div>
                </div>
            </div>
        </>
    );
}