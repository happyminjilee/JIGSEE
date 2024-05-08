import React, {useState, useEffect, } from "react";
import { Link, Button, Select, SelectItem, Selection, Switch } from "@nextui-org/react";
import styled from "@/styles/releasestatuslist.module.css";
import {useCompoStore, useUserWoListStore, useWoDetailStore, useWoGroupStore, useWoStore} from "@/store/workorderstore";
import {useCartStore, useMartStore, useGroupFilter} from "@/store/repairrequeststore";
import {DropBox} from "@/components/workorder/ListDnDbox"
import {QueryClient, QueryClientProvider, useQuery} from "@tanstack/react-query"
import {ReactQueryDevtools} from "@tanstack/react-query-devtools";
import Box from "@mui/material/Box";
import Report from "@/components/workorder/template";
import Modal from "@mui/material/Modal";
import CreateWoModal from "@/components/workorder/CreateWoModal";



interface Option {
    label: string;
    value: string;
}


export default function RequestList() {
  const [mine, setMine] = useState(false)
  const {modal, setModalName, setModal} = useCompoStore()
  const{fetchWoGroup, publish, progress, finish} = useWoGroupStore()
  const queryClient = new QueryClient()
  const lst: Option[] = [
      { label: "PUBLISH", value: "발행" },
      { label: "PROGRESS", value: "진행 중" },
      { label: "FINISH", value: "완료" },
  ];
  const {select, setSelect, forFilter, clearForFilter, addForFilter} = useGroupFilter();
  const {list, fetchUserWo} = useUserWoListStore()
  // const {data} = useQuery()
  useEffect(() => {
      clearForFilter()
      // 로딩 로직 추가?
      if (mine) {
          addForFilter(list)
      } else {
          if (select === "PUBLISH") {
              addForFilter(publish)
          } else if (select === "PROGRESS") {
              addForFilter(progress)
          } else if (select === "FINISH") {
              addForFilter(finish)
          }
          console.log('forFilter', forFilter)
      }

  }, [select, mine]);
    const createWo = () => {
        setModalName("CREATEWO")
        setModal(true)
    }


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
                      <div className="flex flex-col gap-2">
                          <Switch isSelected={mine} onValueChange={setMine} size={"sm"}>
                              {mine ?
                                  <div style={{color: "black", fontSize: "8px", fontWeight: "lighter", marginTop: "1px", marginRight: "2px"}}>내 요청 내역</div>
                                  :
                                  <Link
                                      href="/common/ReleaseTotal/"
                                      // passHref
                                      underline="hover"
                                      style={{color: "black", fontSize: "8px", fontWeight: "lighter"}}
                                  >
                                      전체 내역 보기
                                  </Link>}
                          </Switch>
                          {mine ? <div
                                  style={{margin: "0px"}}
                              ></div>
                              :
                              <Select
                                  size="sm"
                                  label="선택"
                                  value={select}
                                  selectionMode="single"
                                  placeholder="선택"
                                  className={styled.short}
                                  onChange={(e) => {
                                      setSelect(e.target.value)
                                  }}
                              >
                                  <SelectItem key="PUBLISH" value="PUBLISH">발행</SelectItem>
                                  <SelectItem key="PROGRESS" value="PROGRESS">진행 중</SelectItem>
                                  <SelectItem key="FINISH" value="FINISH">완료</SelectItem>
                              </Select>
                          }
                      </div>

                  </div>


                  {/* card */}
                  <DropBox items={forFilter} boxType={"Mart"}>

                  </DropBox>
                  {select !== "PUBLISH" ? <div></div> :
                      <Button
                          color="primary"
                          size="lg"
                          style={{
                              fontWeight: "bold",
                              marginBottom: "5px",
                              margin: "15px auto",
                              width: "190px",
                          }}
                          onPress={() => {
                              createWo()
                          }}
                      >
                          바로 수리 신청
                      </Button>
                  }
              </div>
              <ReactQueryDevtools/>
          </QueryClientProvider>

      </>
  );
}





