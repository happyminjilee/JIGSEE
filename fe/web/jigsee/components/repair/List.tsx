import React, {useState, useEffect, ForwardedRef} from "react";
import { Link, Button } from "@nextui-org/react";
import styled from "@/styles/releasestatuslist.module.css";
import { list } from "postcss";
import { useCompoStore, useWoDetailStore, useWoGroupStore, useWoStore } from "@/store/workorderstore";
import {DnDWrapper} from "@/components/workorder/DndWrapper";
import {useCartStore, useItemStore} from "@/store/repairrequeststore";
import {any} from "prop-types";

interface cardProps {
  id: number;
  createdAt: string;
  model: string;
  serialNo: string;
  status: string;
}

interface card {
  isDragging: boolean;
  dragData: cardProps;
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

  console.log("publish", publish)
  console.log("progress", progress)


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

  const whenDragging = () => {
    console.log("드래그중!")
  }
  const wheDragEnd = () => {
    console.log("드래그 끝낫을때!")
  }
  const [selected, setSelected ]= useState([])

  const Card = React.forwardRef(
      (
          {dragData, isDragging}:card,
          ref: ForwardedRef<HTMLElement>
       ) => (
          <div
              key={dragData.id}
              className={styled.card}
              onClick={() => {
                cardClick(dragData.id, dragData.status)
              }}>
            <div className={styled.division1}>
              <div className={styled.date}>
                {dragData.createdAt[0]}.{dragData.createdAt[1]}.{dragData.createdAt[2]}
              </div>
              <div className={styled.title}>
                {dragData.serialNo} | {dragData.model}
              </div>
            </div>

            <div className={styled.division2}>{dragData.status}</div>
          </div>
      )
  )

  return (
      <>
        <div className={styled.box}>
          <div style={{
            display: "flex",
            justifyContent: "space-between",
            margin: "10px 15px 10px 10px",
          }}>
            <div style={{fontWeight: "bold", fontSize: "15px"}}>수리 진행 내역</div>
            <Link
                href="/common/ReleaseTotal/"
                // passHref
                underline="hover"
                style={{color: "black", fontSize: "8px", fontWeight: "lighter"}}
            >
              전체 내역 보기
            </Link>
          </div>
          <div className={styled.contents}>
          {/* card */}
          <DnDWrapper dragList={selected} onDragEnd={wheDragEnd} onDragging={whenDragging} dragSectionName={"mart"}>
            {(item, ref, isDragging) => (
                <Card
                    dragData={item}
                    isDragging={isDragging}
                    ref={ref}
                />
            )}
          </DnDWrapper>

        </div>
      </div>
    </>
  );
}





