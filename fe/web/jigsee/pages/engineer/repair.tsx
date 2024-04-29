import Navbar from "./navbar";
import styled from "@/styles/engineer.module.scss";
// test 확인 지우기
import WOtest from "@/components/repair/WOtestresult";
import RepairList from "@/components/repair/List";
export default function Repair() {
  return (
    <>
      <Navbar />
      <div className={styled.container}>
        <div className={styled.jigrepairlist}>
          <RepairList />
        </div>
        <div className={styled.repairrequest}>
          <WOtest />
        </div>
      </div>
    </>
  );
}
