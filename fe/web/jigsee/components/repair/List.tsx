import React, {useState, useEffect, } from "react";
import { Link, Button, Select, SelectItem, Selection } from "@nextui-org/react";
import styled from "@/styles/releasestatuslist.module.css";
import { useCompoStore, useWoDetailStore, useWoGroupStore, useWoStore } from "@/store/workorderstore";
import {useCartStore, useMartStore, useGroupFilter} from "@/store/repairrequeststore";
import {DropBox} from "@/components/workorder/ListDnDbox"
import {QueryClient, QueryClientProvider, useQuery} from "@tanstack/react-query"
import {ReactQueryDevtools} from "@tanstack/react-query-devtools";



interface Option {
    label: string;
    value: string;
}


export default function RequestList() {
  const{fetchWoGroup, publish, progress, finish} = useWoGroupStore()
  const queryClient = new QueryClient()
  const lst: Option[] = [
      { label: "PUBLISH", value: "발행" },
      { label: "PROGRESS", value: "진행 중" },
      { label: "FINISH", value: "완료" },
  ];
  const {select, setSelect, forFilter, clearForFilter, addForFilter} = useGroupFilter();
  // const {data} = useQuery()
  useEffect(() => {
      clearForFilter()
      // 로딩 로직 추가?
      if (select === "PUBLISH") {
          addForFilter(publish)
      } else if (select === "PROGRESS") {
          addForFilter(progress)
      } else if (select === "FINISH") {
          addForFilter(finish)
      }
      console.log('forFilter', forFilter)
  }, [select]);



  return (
      <>
          <QueryClientProvider client={queryClient}>
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
                          value={select}
                          selectionMode="single"
                          placeholder="선택"
                          className={styled.short}
                          onChange={(e) => {
                              setSelect(e.target.value)
                          }
                          }
                      >
                          {/*{lst.map((option) => (*/}
                          {/*    <SelectItem key={option.label} value={option.value}>*/}
                          {/*        {option.value}*/}
                          {/*    </SelectItem>*/}
                          {/*))}*/}
                          <SelectItem key="PUBLISH" value="PUBLISH">발행</SelectItem>
                          <SelectItem key="PROGRESS" value="PROGRESS">진행 중</SelectItem>
                          <SelectItem key="FINISH" value="FINISH">완료</SelectItem>
                      </Select>
                  </div>
                  {/* card */}
                  <DropBox items={forFilter} boxType={"Mart"}>

                  </DropBox>
              </div>
              <ReactQueryDevtools/>
          </QueryClientProvider>

      </>
  );
}





