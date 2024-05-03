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

interface forSlice {
  id: number,
  model: string, // 지그 모델명
  serialNo: string, // 지그 일련번호
  creator: string, // 작성자
  terminator: string, // 작성 종료자
  status: string, // wo 상태
  createdAt: string, // wo 생성시간
  updatedAt: string // wo 수정시간
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
          // if (publish !== null) {
          //   if (publish.length >7) {
          //     setForPublish(publish.slice(0, 7))
          //   } else if (publish.length > 1) {
          //     setForPublish(publish.slice(0, publish.length - 1))
          //   } else {
          //     setForPublish(publish)
          //   }
          // } else {
          //   setForPublish([])
          // }
          //
          // if (progress !== null) {
          //   if (progress.length >7) {
          //     setForProgress(progress.slice(0, 7))
          //   } else if (progress.length > 1) {
          //     setForProgress(progress.slice(0, progress.length-1))
          //   } else {
          //     setForProgress(progress)
          //   }
          // } else {
          //   setForProgress([])
          // }
        console.log(res)
        })
        .catch((error) => {
          console.log(error.message)
        })
  }, []);
  const [forPublish, setForPublish] = useState<forSlice[]>([]) ;
  const [forProgress,setForProgress] = useState<forSlice[]>([]);
  console.log("publish", publish)
  console.log("progress", progress)
  console.log("forPublish", forPublish)
  console.log("forProgress", forProgress)
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

  const cardClick = (Id: number, state: string) => {
    // 클릭한 S/N로 아이디로 바꾸기 , 추후 수정 예정
    setWoId("testModelId");
    setRightCompo(state);
    console.log("cardclick", state)
    console.log("cardclick", rightCompo)
    fetchWoDetail(Id)
        .then((res) => {
          console.log('woDetail', res)
        })
        .catch((error) => {
          console.log(error.message)
        })
  };

  return (
    <>
      <div className={styled.box}>
        <div style={{
          display: "flex",
          justifyContent: "space-between",
          margin: "10px 15px 10px 10px",
        }}>
          <div style={{ fontWeight: "bold", fontSize: "15px" }}>수리 진행 내역</div>
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
          {publish.map((info, index) => (
            <div key={index} className={styled.card} onClick={() =>{cardClick(info.id, info.status )}}>
              <div className={styled.division1}>
                <div className={styled.date}>
                  {info.createdAt[0]}.{info.createdAt[1]}.{info.createdAt[2]}
                </div>
                <div className={styled.title}>
                  {info.serialNo} | {info.model}
                </div>
              </div>

              <div className={styled.division2}>{info.status}</div>
            </div>
          ))}
          {progress.map((info, index) => (
              <div key={index} className={styled.card} onClick={() =>{cardClick(info.id, info.status )}}>
                <div className={styled.division1}>
                  <div className={styled.date}>
                    {info.createdAt[0]}.{info.createdAt[1]}.{info.createdAt[2]}
                  </div>
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
