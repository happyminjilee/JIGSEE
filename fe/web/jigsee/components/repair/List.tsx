import React, {useState, useEffect, ForwardedRef} from "react";
import { Link, Button, Select, SelectItem, Selection } from "@nextui-org/react";
import styled from "@/styles/releasestatuslist.module.css";
import { list } from "postcss";
import { useCompoStore, useWoDetailStore, useWoGroupStore, useWoStore } from "@/store/workorderstore";
import {DnDWrapper} from "@/components/workorder/DndWrapper";
import {useCartStore, useMartStore, useGroupFilter, useItemStore} from "@/store/repairrequeststore";
import {any} from "prop-types";
import {DropBox} from "@/components/workorder/ListDnDbox"

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

interface Option {
    label: string;
    value: string;
}


export default function RequestList() {
  // wo id 상태 변화를 위한 store 변수 선언
  const { woId, setWoId, rightCompo, setRightCompo } = useCompoStore();
  // 확인용 함수 - 나중에 api 함수 연결
  const {martList, clearMart, removeFromMart, addToMart} = useMartStore()
  const {fetchWoDetail, id} = useWoDetailStore()
  const{fetchWoGroup, publish, progress, finish} = useWoGroupStore()
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

  const lst: Option[] = [
      { label: "PUBLISH", value: "발행" },
      { label: "PROGRESS", value: "진행 중" },
      { label: "FINISH", value: "완료" },
  ];
  const {select, setSelect, forFilter, clearForFilter, addForFilter} = useGroupFilter();

  useEffect(() => {
      clearForFilter()
      // 로딩 로직 추가?
      for (const e of select.split(',')) {
          console.log(e)
          if (e === "PUBLISH") {
              addForFilter(publish)
          } else if (e === "PROGRESS") {
              addForFilter(progress)
          } else if (e === "FINISH") {
              addForFilter(finish)
          }
      }
      console.log('forFilter', forFilter)
  }, [select]);



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
          <div>
              <Select
                  label="선택"
                  selectionMode="multiple"
                  placeholder="선택"
                  className={styled.short}
                  onChange={(e) =>
                  {
                      setSelect(e.target.value)}
                    }
              >
                  {lst.map((option) => (
                      <SelectItem key={option.label} value={option.value}>
                          {option.value}
                      </SelectItem>
                  ))}
              </Select>
          </div>
          {/* card */}
          <DropBox items={martList} boxType={"Mart"}>

          </DropBox>


      </div>
    </>
  );
}





