import Navbar from "./navbar";
import React, { useState } from "react";
import styled from "@/styles/manager.module.scss";
import ArrowForwardIosIcon from "@mui/icons-material/ArrowForwardIos";
import RequestList from "@/components/release/RequestList";
import Approve from "@/components/release/Approve";
import Return from "@/components/release/Return";
import EditStandard from "@/components/release/EditStandard";
import Request from "@/components/release/Request";
import StockList from "@/components/release/StockList";
import Modal from "@mui/material/Modal";
import Box from "@mui/material/Box";
import { useRouter } from "next/router";

export default function Manager() {
  const router = useRouter();
  const goToJigrestore = () => {
    router.push("/common/RestoreTotal");
  };
  // 불출 승인 , 반려 모달 표시 상태변수
  const [showApproveModal, setApproveShowModal] = useState(false);
  const [showReturnModal, setReturnShowModal] = useState(false);
  // 불출 승인 모달 오픈, 클로즈 함수
  const openApproveModal = () => setApproveShowModal(true);
  const closeApproveModal = () => setApproveShowModal(false);
  // 불출 반려 모달 오픈, 클로즈 함수
  const openReturnModal = () => setReturnShowModal(true);
  const closeReturnModal = () => setReturnShowModal(false);
  // 지그점검항목 입력 모달
  const [showEditStandardModal, setShowEditStandardModal] = useState(false);
  // Jig 정보 입력 모달 오픈, 클로즈 함수
  const openEditStandardModal = () => setShowEditStandardModal(true);
  const closeEditStandardModal = () => setShowEditStandardModal(false);
  // Jig 불출 요청 입력 모달 오픈
  const [showRequestModal, setShowRequestModal] = useState(false);
  // Jig 불출 요청 오픈, 클로즈 함수
  const openRequestModal = () => setShowRequestModal(true);
  const closeRequestModal = () => setShowRequestModal(false);
  return (
    <>
      <Navbar />
      <div className={styled.container}>
        <div className={styled.releasecontainer}>
          <RequestList onApproveClick={openApproveModal} onReturnClick={openReturnModal} />
          {showApproveModal && <Approve onClose={closeApproveModal} />}
          {showReturnModal && <Return onClose={closeReturnModal} />}
        </div>
        <div className={styled.stockcontainer}>
          <StockList />
        </div>
        <div className={styled.btncontainer}>
          <button className={styled.jigbtn} onClick={goToJigrestore}>
            Jig 보수 요청 확인
            <ArrowForwardIosIcon color="primary" />
          </button>
          <button onClick={openRequestModal} className={styled.jigbtn}>
            Jig 불출 요청 <ArrowForwardIosIcon color="primary" />
          </button>

          <button onClick={openEditStandardModal} className={styled.jigbtn}>
            Jig 정보 입력 <ArrowForwardIosIcon color="primary" />
          </button>
        </div>
        {/* jig 정보 입력 컴포넌트 모달 창  */}
        <Modal
          open={showEditStandardModal} // Corrected from 'open'
          onClose={closeEditStandardModal} // Added onClose handler
          aria-labelledby="modal-modal-title"
          aria-describedby="modal-modal-description"
          sx={{
            display: "flex",
            alignItems: "center",
            justifyContent: "center",
          }}
        >
          <Box sx={{ width: "500px", height: "700px", marginTop: "100px" }}>
            <Box
              sx={{
                width: "100%",
                height: "80%",
                display: "flex",
                alignItems: "start",
                justifyContent: "center",
              }}
            >
              <EditStandard onClose={closeEditStandardModal} />
              <img
                src="/images/delete_gray.svg"
                alt="delete"
                onClick={closeEditStandardModal}
                style={{ width: "50px", height: "50px" }}
              ></img>
            </Box>
          </Box>
        </Modal>

        {/* jig 불출 요청 컴포넌트 모달  */}
        <Modal
          open={showRequestModal} // Corrected from 'open'
          onClose={closeRequestModal} // Added onClose handler
          aria-labelledby="modal-modal-title"
          aria-describedby="modal-modal-description"
          sx={{
            display: "flex",
            alignItems: "center",
            justifyContent: "center",
          }}
        >
          <Box
            sx={{
              width: "100%",
              height: "80%",
              display: "flex",
              alignItems: "start",
              justifyContent: "center",
            }}
          >
            {/* 닫기 아이콘 */}

            <Box>
              <Request />
            </Box>
            <img
              src="/images/delete_gray.svg"
              alt="delete"
              onClick={closeRequestModal}
              style={{ width: "50px", height: "50px" }}
            ></img>
            {/* <img src="/images/delete_normal.svg" alt="delete" onClick={closeRequestModal}></img> */}
          </Box>
        </Modal>
      </div>
    </>
  );
}
