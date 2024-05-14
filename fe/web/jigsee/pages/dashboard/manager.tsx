import Navbar from "@/pages/manager/navbar";
import dynamic from "next/dynamic";
import styled from "@/styles/dashboard/manager.module.scss";
import Report from "@/components/dashboard/report";
const ManagerGraph = dynamic(() => import("@/components/dashboard/managergraph"), {
    ssr: false
    }
)
import ReleaseTable from "@/components/dashboard/releasetable";
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
          <div className={styled.jiglocation}>{/* <ReleaseTable /> */}</div>
          <div className={styled.jigalarm}>점검주기 수정이 필요한 지그 알림</div>
        </div>
      </div>
    </>
  );
}
