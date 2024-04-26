import Navbar from "./navbar";
import React, { useState } from "react";
import styled from "@/styles/manager.module.scss";
import ArrowForwardIosIcon from "@mui/icons-material/ArrowForwardIos";
import RequestList from "@/components/release/RequestList";
import Approve from "@/components/release/Approve";
import Return from "@/components/release/Return";

export default function Manager() {
  const [showApproveModal, setApproveShowModal] = useState(false);
  const [showReturnModal, setReturnShowModal] = useState(false);

  const openApproveModal = () => setApproveShowModal(true);
  const closeApproveModal = () => setApproveShowModal(false);
  const openReturnModal = () => setReturnShowModal(true);
  const closeReturnModal = () => setReturnShowModal(false);
  return (
    <>
      <Navbar />
      <div className={styled.container}>
        <div className={styled.releasecontainer}>
          <RequestList onApproveClick={openApproveModal} onReturnClick={openReturnModal} />
          {showApproveModal && <Approve onClose={closeApproveModal} />}
          {showReturnModal && <Return onClose={closeReturnModal} />}
        </div>
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
