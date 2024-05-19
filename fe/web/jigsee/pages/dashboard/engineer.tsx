import Navbar from "@/pages/engineer/navbar";
import styled from "@/styles/dashboard/engineer.module.scss";
import Report from "@/components/dashboard/report";
import MyWoList from "@/components/dashboard/myWo";
import { useEffect, useState } from "react";
import { useUserWoListStore } from "@/store/workorderstore";
import Editmethod from "@/components/dashboard/editedmethod";
import ReleaseTable from "@/components/dashboard/releasetable";
import dynamic from "next/dynamic";
const DonutChart = dynamic(() => import("@/components/dashboard/repairdonut"), {
  ssr: false, // 이 옵션은 서버 사이드 렌더링을 비활성화합니다.
});
const Lifetime = dynamic(() => import("@/components/dashboard/lifetime"), {
  ssr: false, // 이 옵션은 서버 사이드 렌더링을 비활성화합니다.
});
export default function Engineerdashboard() {
  const { list, fetchUserWo } = useUserWoListStore();

  useEffect(() => {
    if (typeof window !== "undefined") {
      const employeeNo = localStorage.getItem("employeeNo") || "";
      const name = localStorage.getItem("name") || "";
      fetchUserWo(employeeNo, name, 1, 5)
        .then((res) => {})
        .catch((error) => {});
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
          <div className={styled.jiglocation}>
            <ReleaseTable />
            <Lifetime />
          </div>
          <div className={styled.percent}>
            <DonutChart />
          </div>
        </div>
        <div className={styled.bottom}>
          <div className={styled.method}>
            <Editmethod />
          </div>
          <div className={styled.wo}>
            <MyWoList />
          </div>
        </div>
      </div>
    </>
  );
}
