import React, {useEffect, useState} from "react";
import {Link, modal} from "@nextui-org/react";
import styled from "@/styles/releasestatuslist.module.css"
import {useReleaseStore, useReleaseDetailStore, useReleaseModalStore} from "@/store/releasestore"
import ApprovedRelease from "@/components/release/ApprovedRelease"
import RejectedRelease from "@/components/release/RejectedRelease"
import Modal from "@mui/material/Modal";
import Box from "@mui/material/Box";
import { useDrag } from 'react-dnd';
import {useCompoStore} from "@/store/workorderstore";


interface lst {
  requestId: string; // TypeScript에서는 long 타입이 없으므로 number를 사용합니다.
  from: string; // 요청자
  to: string; // 승인자
  model: string;
  count: number;
  requestedAt: string;
  state: string;
}



export default function ReleaseStatusList() {
  const lst= [
      {requestId: '1233', from: '주준형', to: '이민지', model: '회전 지그', count: 4, requestedAt: '2024.04.25', state: '승인 대기 중'},
      {requestId: '1234', from: '주준형', to: '박수형', model: '절단 지그', count: 5, requestedAt: '2024.04.24', state: '승인 완료'},
      {requestId: '1235', from: '주준형', to: '이민지', model: '잡는 지그', count: 6, requestedAt: '2024.04.24', state: '반려'},
      {requestId: '1231', from: '주준형', to: '이민지', model: '회전 지그', count: 4, requestedAt: '2024.04.25', state: '승인 대기 중'},
      {requestId: '1232', from: '주준형', to: '박수형', model: '절단 지그', count: 5, requestedAt: '2024.04.24', state: '승인 완료'},
      {requestId: '1230', from: '주준형', to: '이민지', model: '잡는 지그', count: 6, requestedAt: '2024.04.24', state: '반려'},
      {requestId: '1229', from: '주준형', to: '이민지', model: '회전 지그', count: 4, requestedAt: '2024.04.25', state: '승인 대기 중'},
      {requestId: '1228', from: '주준형', to: '박수형', model: '절단 지그', count: 5, requestedAt: '2024.04.24', state: '승인 완료'},
      {requestId: '1227', from: '주준형', to: '이민지', model: '잡는 지그', count: 6, requestedAt: '2024.04.24', state: '반려'},
  ]
  // 지그점검항목 입력 모달
  const [showModal, setShowModal] = useState(false);
  const {setModal, setModalName} = useCompoStore()
  // Jig 정보 입력 모달 오픈, 클로즈 함수
  const openModal = () => setShowModal(true);
  const closeModal = () => setShowModal(false);

  const {fetchRelease, releaseList} = useReleaseStore()
  const {fetchReleaseDetail, ...content} = useReleaseDetailStore()

  useEffect(() => {
      fetchRelease("ALL", 1, 15)
          .then((response) => {
              console.log(response.data.result)
          })
          .catch((error) => {
              console.log(error.message)
          })
  }, [])
  const {isClose, setClose} = useReleaseModalStore()
  const [decision, setDecision] = useState(false)
  const cardClick = (requestId: string) => () => {
      console.log("clicked", requestId)
      fetchReleaseDetail(requestId)
          .then((response) => {
            if (content.status === "PUBLISH") {
                setDecision(true)
            } else {
                if (content.memo) {
                    setDecision(false)
                } else {
                    setDecision(true)
                }
            }
            openModal()
            setClose(true)
          })
          .catch((error) => {
              console.log(error.message)
              if (content.status === "PUBLISH") {
                  setDecision(true)
              } else {
                  if (content.memo) {
                      setDecision(false)
                  } else {
                      setDecision(true)
                  }
              }
              openModal()
              setClose(true)
          })
  }

  return (
      <>
        <div className={styled.box}>
          <div style={{display: "flex", justifyContent: "space-between", marginBottom: "15px"}}>
            <div style={{fontWeight: "bold", fontSize: "15px", margin: "10px 10px 5px 10px"}}>불출 요청 내역</div>
            <Link
                href="/common/ReleaseTotal/"
                // passHref
                underline="hover"
                style={{color: "black", fontSize: "12px", fontWeight: "lighter", margin: "10px 10px 5px 10px"}}
            >
              전체 내역 보기
            </Link>
          </div>
          <div
              className={styled.contents}
          >
            {/* card */}
            {releaseList.map((info, index) => (
                <div
                    key={index}
                    className={styled.card}
                    onClick={cardClick(info.id)}
                >
                  <div
                      className={styled.division1}
                  >
                    <div
                        className={styled.date}
                    >
                      {info.createdAt[0]}.{info.createdAt[1]}.{info.createdAt[2]}
                    </div>
                    <div
                        className={styled.title}
                    >
                      {info.from}
                    </div>
                  </div>

                  <div
                      className={styled.division2}
                  >
                    {info.status === "PUBLISH" ? "결재 대기" : info.status === "FINISH" ? "승인 완료" : ""}
                  </div>
                </div>
            ))}
          </div>
        </div>
          <Modal
              open={isClose} // Corrected from 'open'
              onClose={closeModal} // Added onClose handler
              aria-labelledby="modal-modal-title"
              aria-describedby="modal-modal-description"
              sx={{
                  display: "flex",
                  alignItems: "center",
                  justifyContent: "center",
                  '& .MuiBox-root': {  // Assuming the box is causing issues
                      outline: 'none',
                      border: 'none',
                      boxShadow: 'none'
                  }
              }}
          >
              <Box
                  sx={{
                      width: "100%",
                      height: "80%",
                      display: "flex",
                      alignItems: "center",
                      justifyContent: "center",
                  }}
              >
                  { decision ? <ApprovedRelease/>: <RejectedRelease/>}
              </Box>
          </Modal>
      </>
  );
}
