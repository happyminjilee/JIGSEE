import Navbar from "./navbar";
import styled from "@/styles/engineer.module.scss";
// test 확인 지우기
import WOtest from "@/components/repair/WOtestresult";
import RepairList from "@/components/repair/List";
import RepairRequest from "@/components/repair/Requests"
import Information from "@/components/repair/JigDetail"
import { useWoStore, useCompoStore } from "@/store/workorderstore";
import { DndProvider, useDrop } from 'react-dnd';
import { HTML5Backend } from 'react-dnd-html5-backend';

import React from "react";
import {useReleaseModalStore} from "@/store/releasestore";
import {useState, useEffect} from "react"
import Report from "@/components/workorder/template"
import Request from "@/components/repair/Requests"

export default function Repair() {
  const { rightCompo } = useCompoStore();
  useEffect(() => {
      console.log(`rightCompo has changed to: ${rightCompo}`);
      // 여기서 추가적인 로직을 실행할 수 있습니다.
  }, [rightCompo]);
  return (
    <>
      <Navbar />
      <DndProvider backend={HTML5Backend}>
          <div className={styled.container}>
            <div className={styled.jigrepairlist}>
              <RepairList />
            </div>
            <div className={styled.repairrequest}>
                {rightCompo === "PUBLISH"  && <Information/>}
                {rightCompo === "PROGRESS"  && <Information/>}
                {rightCompo === "FINISH"  && <Information/>}
                {rightCompo === "REQUEST" && <Request/>}
                {rightCompo === "TEST" && <WOtest/>}
            </div>
          </div>
      </DndProvider>
    </>
  );
}
