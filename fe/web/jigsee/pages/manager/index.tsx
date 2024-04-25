import Navbar from "./navbar";
import styled from "@/styles/manager.module.scss";
import ArrowForwardIosIcon from "@mui/icons-material/ArrowForwardIos";
export default function Manager() {
  return (
    <>
      <Navbar />
      <div className={styled.container}>
        <div className={styled.releasecontainer}>불출요청내역 컴포넌트 자리</div>
        <div className={styled.stockcontainer}>재고현황리스트</div>
        <div className={styled.btncontainer}>
          <button className={styled.jigbtn}>
            Jig 불출 요청 <ArrowForwardIosIcon color="primary" />
          </button>
          <button className={styled.jigbtn}>
            Jig 정보 요청 <ArrowForwardIosIcon color="primary" />
          </button>
        </div>
      </div>
    </>
  );
}
