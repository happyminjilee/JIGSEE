import Navbar from "@/pages/engineer/navbar";
import styled from "@/styles/dashboard/engineer.module.scss";
import Report from "@/components/dashboard/report";
import MyWoList from "@/components/dashboard/myWo";
import {useEffect, useState} from "react";
import {useUserWoListStore} from "@/store/workorderstore";

export default function Engineerdashboard() {
  const {list, fetchUserWo} = useUserWoListStore()

  useEffect(() => {
    if (typeof window !== "undefined") {
        const employeeNo = localStorage.getItem("employeeNo") || ""
        const name = localStorage.getItem("name") || ""
        fetchUserWo(employeeNo, name, 1, 5)
            .then((res) => {
                console.log('res', res)
            })
            .catch((error) => {
                console.log('error', error)
            })
      }
  }, []);
  return (
    <>
      <Navbar />
      <div className={styled.container}>
        <div className={styled.report}>
          <Report />
        </div>
        <div className={styled.middle}>
          <div className={styled.jiglocation}>사용대기 , 창고 지그 수</div>
          <div className={styled.percent}>지그 수리현황 퍼센트</div>
        </div>
        <div className={styled.bottom}>
          <div className={styled.method}>지그 점검항목 수정</div>
          <div className={styled.wo}>
              <MyWoList/>
          </div>
        </div>
      </div>
    </>
  );
}
