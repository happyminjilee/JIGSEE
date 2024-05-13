import Navbar from "@/pages/engineer/navbar";
import styled from "@/styles/dashboard/engineer.module.scss";
import Report from "@/components/dashboard/report";
import ReleaseTable from "@/components/dashboard/releasetable";
import dynamic from "next/dynamic";
import Editmethod from "@/components/dashboard/editedmethod";
const DonutChart = dynamic(() => import("@/components/dashboard/repairdonut"), {
  ssr: false, // 이 옵션은 서버 사이드 렌더링을 비활성화합니다.
});
const Lifetime = dynamic(() => import("@/components/dashboard/lifetime"), { ssr: false });
export default function Engineerdashboard() {
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
          <div className={styled.wo}>내가 신청한 wo</div>
        </div>
      </div>
    </>
  );
}
