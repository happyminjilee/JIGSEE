import { Pagination } from "@nextui-org/react";
import { useState, useEffect } from "react";
import styled from "@/styles/Total/Total.module.css";
import EngineerNav from "@/pages/engineer/navbar";
import ManagerNav from "@/pages/manager/navbar";
import { useRestoreStore } from "@/store/restorestore";
import Modal from "@mui/material/Modal";
import Box from "@mui/material/Box";
import RestoreDetail from "@/components/restore/RestoreMemo";
import Loading from "@/pages/loading/index";
interface Props {
  onClick(): void;
}
interface ListData {
  createdAt: string; // 요청날짜
  from: string; // 요청자
  id: number; // 보수 요청 내역 id
}

export default function RestoreTotal() {
  const [role, setRole] = useState<string>(""); // 초기 상태를 명시적으로 string 타입으로 설정
  const [isLoading, setIsLoading] = useState(true);
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
    setIsLoading(true);
    getRestoreList()
      .then(() => {
        if (restoreList.length > 0) {
          setIsLoading(false);
        }
      })
      .catch(() => {
        setIsLoading(false);
      });
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
  const restoreDummies = [
    {
      createdAt: "2024.04.22", // 요청날짜
      from: "주준형프로", // 요청자
      id: 12,
    },
    {
      createdAt: "2024.04.21", // 요청날짜
      from: "박수형프로", // 요청자
      id: 1,
    },
    {
      createdAt: "2024.04.23", // 요청날짜
      from: "차현철프로", // 요청자
      id: 3,
    },
    {
      createdAt: "2024.04.24", // 요청날짜
      from: "김용준프로", // 요청자
      id: 4,
    },
    ,
    {
      createdAt: "2024.04.25", // 요청날짜
      from: "강성범프로", // 요청자
      id: 7,
    },
    // 다른 JIG 데이터 객체들...
  ];
  return (
    <>
      {Navbar}
      <div className={styled.bigcontainer}>
        {restoreList.length > 0 ? (
          // Render restoreList once it's loaded
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
        ) : (
          <div className={styled.container}>
            {restoreDummies.map((item, index) => (
              <div key={index} className={styled.fullWidth}>
                <h3>생성일 {item?.createdAt}</h3>
                <p>
                  보수 요청 번호 {item?.id} | 요청자 {item?.from}
                </p>
              </div>
            ))}
          </div>
        )}
        <div className={styled.center}>
          <Pagination onChange={(e) => setPage(e)} total={5} />
        </div>
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
