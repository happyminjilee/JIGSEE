import React, {useState, useEffect, ForwardedRef} from "react";
import { Link, Button, Select, SelectItem, Selection } from "@nextui-org/react";
import styled from "@/styles/releasestatuslist.module.css";
import { list } from "postcss";
import { useCompoStore, useWoDetailStore, useWoGroupStore, useWoStore } from "@/store/workorderstore";
import {DnDWrapper} from "@/components/workorder/DndWrapper";
import {useCartStore, useMartStore, useGroupFilter} from "@/store/repairrequeststore";
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
                  selectionMode="single"
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
          <DropBox items={forFilter} boxType={"Mart"}>

          </DropBox>


      </div>
    </>
  );
}





