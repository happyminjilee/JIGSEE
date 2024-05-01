import Navbar from "./navbar";
import styled from "@/styles/engineer.module.scss";
// test 확인 지우기
import WOtest from "@/components/repair/WOtestresult";
import RepairList from "@/components/repair/List";
import { usewoStore } from "@/store/workorderstore";

export default function Repair() {
  const { openWotest } = usewoStore();
  return (
    <>
      <Navbar />
      <div className={styled.container}>
        <div className={styled.jigrepairlist}>
          <RepairList />
        </div>
        <div className={styled.repairrequest}>{openWotest && <WOtest />}</div>
      </div>
    </>
  );
}
