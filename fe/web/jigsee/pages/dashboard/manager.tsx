import Navbar from "@/pages/manager/navbar";
import styled from "@/styles/dashboard/manager.module.scss";
import Report from "@/components/dashboard/report";
import ReleaseTable from "@/components/dashboard/releasetable";
import dynamic from "next/dynamic";
const Lifetime = dynamic(() => import("@/components/dashboard/lifetime"), {
  ssr: false, // 이 옵션은 서버 사이드 렌더링을 비활성화합니다.
});
export default function Managerdashboard() {
  return (
    <>
      <Navbar />
      <div className={styled.container}>
        <div className={styled.report}>
          <Report />
        </div>
        <div className={styled.graph}>그래프자리</div>
        <div className={styled.bottom}>
          <div className={styled.jiglocation}>
            <ReleaseTable />
          </div>
          <div className={styled.jigalarm}>
            <Lifetime />
          </div>
        </div>
      </div>
    </>
  );
}
