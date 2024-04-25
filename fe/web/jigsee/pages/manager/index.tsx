import Navbar from "./navbar";
import styled from "@/styles/manager.module.scss";

export default function Manager() {
  return (
    <>
      <Navbar />
      <div className={styled.container}>
        <div className={styled.releasecontainer}>불출요청내역 컴포넌트 자리</div>
        <div className={styled.stockcontainer}>재고현황리스트</div>
        <div className={styled.btncontainer}>재고현황리스트</div>
      </div>
    </>
  );
}
