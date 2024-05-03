import { Pagination } from "@nextui-org/react";
import { useState, useEffect } from "react";
import styled from "@/styles/Total/Total.module.css";
import EngineerNav from "@/pages/engineer/navbar";
import ManagerNav from "@/pages/manager/navbar";
import { useRestoreStore } from "@/store/restorestore";
import Modal from "@mui/material/Modal";
import Box from "@mui/material/Box";
import RestoreDetail from "@/components/restore/RestoreMemo";
interface Props {
  onClick(): void;
}
interface ListData {
  createdAt: string; // 요청날짜
  from: string; // 요청자
  id: number; // 보수 요청 내역 id
}

export default function RepairTotal() {
  const [role, setRole] = useState<string>(""); // 초기 상태를 명시적으로 string 타입으로 설정

  useEffect(() => {
    // 컴포넌트가 클라이언트 사이드에서 마운트되었을 때 로컬 스토리지에서 role 읽기
    const storedRole = localStorage.getItem("role");
    if (storedRole !== null) {
      setRole(storedRole); // 로컬 스토리지의 값이 null이 아닌 경우에만 상태 업데이트
    }
    getRestoreList();
  }, []);
  let Navbar;
  if (role === "MANAGER") {
    Navbar = <ManagerNav />;
  } else if (role === "ENGINEER") {
    Navbar = <EngineerNav />;
  } else {
    Navbar = <ManagerNav />; // 기본값으로 ManagerNav 설정
  }

  const {
    restoreList,
    page,
    setPage,
    getRestoreList,
    endpage,
    getRestoreDetail,
    setRestoreJigid,
    restoreId,
    open,
    setOpen,
  } = useRestoreStore();
  useEffect(() => {
    getRestoreList(); // Fetch list whenever the page changes
  }, [page, getRestoreList]);

  function cardClick(jigID: number) {
    setRestoreJigid(jigID);
    setOpen(true);
    console.log("clicked", jigID);
  }
  // jig선택한 id가 바뀔때마다 jig Detail이 변함
  useEffect(() => {
    getRestoreDetail();
  }, [restoreId]);

  return (
    <>
      {/* {Navbar} */}
      <div className={styled.container}>
        {restoreList.map((item, index) => (
          <div key={index} className={styled.fullWidth} onClick={() => cardClick(item.id)}>
            <h3>
              생성일 {item.createdAt[0]}년 {item.createdAt[1]}월 {item.createdAt[2]}일
            </h3>
            <p>
              보수 요청 번호 {item.id} | 요청자 {item.from}
            </p>
          </div>
        ))}
      </div>
      <div className={styled.center}>
        <Pagination onChange={(e) => setPage(e)} total={endpage} />
      </div>
      <Modal
        open={open} // Corrected from 'open'
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
        sx={{
          display: "flex",
          alignItems: "center",
          justifyContent: "center",
          "& .MuiBox-root": {
            // Assuming the box is causing issues
            outline: "none",
            border: "none",
            boxShadow: "none",
          },
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
          <RestoreDetail />
        </Box>
      </Modal>
    </>
  );
}
