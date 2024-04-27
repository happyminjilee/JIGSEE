import Navbar from "./navbar";
import styled from "@/styles/engineer.module.scss";
// test 확인 지우기
import WOtest from "@/components/repair/WOtestresult";

export default function Repair() {
  return (
    <>
      <Navbar />
      <div className={styled.container}>
        <div className={styled.jigrepairlist}>지그 수리 대기</div>
        <div className={styled.repairrequest}>
          <WOtest />
        </div>
      </div>
    </>
  );
}
