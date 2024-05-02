import { useState, useEffect } from "react";
import { Link, Button } from "@nextui-org/react";
import styled from "@/styles/releasestatuslist.module.css";
import { list } from "postcss";
import { useCompoStore, useWoDetailStore, useWoGroupStore, useWoStore } from "@/store/workorderstore";
interface lst {
  id: number;
  createdAt: string;
  model: string;
  serialNo: string;
  status: string;
}

export default function RequestList() {
  // wo id 상태 변화를 위한 store 변수 선언
  const { woId, setWoId, rightCompo, setRightCompo } = useCompoStore();
  // 확인용 함수 - 나중에 api 함수 연결

  const {fetchWoDetail, id} = useWoDetailStore()
  const{fetchWoGroup, publish, progress} = useWoGroupStore()
  useEffect(() => {
    fetchWoGroup()
        .then((res) => {
          console.log(res)
        })
        .catch((error) => {
          console.log(error.message)
        })
  }, []);
  const forPublish = publish.slice(0, 7);
  const forProgress = progress.slice(0, 7);

  const lst = [
    {
      createdAt: "2024.04.26",
      id: 123123,
      model: "ModelName",
      serialNo: "S0000001",
      status: "PUBLISH",
    },
    {
      createdAt: "2024.04.26",
      id: 123123,
      model: "ModelName",
      serialNo: "S0000001",
      status: "PUBLISH",
    },
    {
      createdAt: "2024.04.26",
      id: 123123,
      model: "ModelName",
      serialNo: "S0000001",
      status: "PUBLISH",
    },
    {
      createdAt: "2024.04.26",
      id: 123124,
      model: "ModelName",
      serialNo: "S0000002",
      status: "PROGRESS",
    },
    {
      createdAt: "2024.04.26",
      id: 123125,
      model: "ModelName",
      serialNo: "S0000003",
      status: "PROGRESS",
    },
    {
      createdAt: "2024.04.26",
      id: 123125,
      model: "ModelName",
      serialNo: "S0000003",
      status: "PROGRESS",
    },
    {
      createdAt: "2024.04.26",
      id: 123125,
      model: "ModelName",
      serialNo: "S0000003",
      status: "PROGRESS",
    },
  ];

  const cardClick = (Id: string, state: string) => {
    // 클릭한 S/N로 아이디로 바꾸기 , 추후 수정 예정
    setWoId("testModelId");
    setRightCompo(state);
    fetchWoDetail(Id)
        .then((res) => {
          console.log(res)
        })
        .catch((error) => {
          console.log(error.message)
        })
  };

  return (
    <>
      <div className={styled.box}>
        <div style={{ display: "flex", justifyContent: "space-between", marginBottom: "15px" }}>
          <div style={{ fontWeight: "bold", fontSize: "15px" }}>재고 불출 요청 내역</div>
          <Link
            href="/common/ReleaseTotal/"
            // passHref
            underline="hover"
            style={{ color: "black", fontSize: "8px", fontWeight: "lighter" }}
          >
            전체 내역 보기
          </Link>
        </div>
        <div className={styled.contents}>
          {/* card */}
          {forPublish.map((info, index) => (
            <div key={index} className={styled.card} onClick={() =>{cardClick(info.serialNo, info.status )}}>
              <div className={styled.division1}>
                <div className={styled.date}>{info.createdAt}</div>
                <div className={styled.title}>
                  {info.serialNo} | {info.model}
                </div>
              </div>

              <div className={styled.division2}>{info.status}</div>
            </div>
          ))}
          {forProgress.map((info, index) => (
              <div key={index} className={styled.card} onClick={() =>{cardClick(info.serialNo, info.status )}}>
                <div className={styled.division1}>
                  <div className={styled.date}>{info.createdAt}</div>
                  <div className={styled.title}>
                    {info.serialNo} | {info.model}
                  </div>
                </div>

                <div className={styled.division2}>{info.status}</div>
              </div>
          ))}
        </div>
      </div>
    </>
  );
}
