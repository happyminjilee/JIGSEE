import Navbar from "@/pages/manager/navbar";
import styled from "@/styles/dashboard/manager.module.scss";
import Report from "@/components/dashboard/report";

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
          <div className={styled.jiglocation}>jig Location</div>
          <div className={styled.jigalarm}>점검주기 수정이 필요한 지그 알림</div>
        </div>
      </div>
    </>
  );
}
