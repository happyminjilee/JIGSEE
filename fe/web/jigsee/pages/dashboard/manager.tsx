import Navbar from "@/pages/manager/navbar";
import dynamic from "next/dynamic";
import styled from "@/styles/dashboard/manager.module.scss";
import Report from "@/components/dashboard/report";
const ManagerGraph = dynamic(() => import("@/components/dashboard/managergraph"), {
    ssr: false
    }
)
import ReleaseTable from "@/components/dashboard/releasetable";
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
        <div className={styled.graph}>
            <ManagerGraph/>
        </div>
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
