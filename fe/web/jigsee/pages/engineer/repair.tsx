import Navbar from "./navbar";
import styled from "@/styles/engineer.module.scss";
export default function Repair() {
  return (
    <>
      <Navbar />
      <div className={styled.container}>
        <div className={styled.jigrepairlist}>지그 수리 대기</div>
        <div className={styled.repairrequest}>지그 수리 요청</div>
      </div>
    </>
  );
}
