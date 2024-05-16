import {
  Pagination,
  Select,
  SelectItem,
  Selection,
  Link,
} from "@nextui-org/react";
import React, { useState, useEffect } from "react";
import styled from "@/styles/Total/Total.module.css";
import EngineerNav from "@/pages/engineer/navbar";
import ManagerNav from "@/pages/manager/navbar";
import {
  useCompoStore,
  useWoDetailStore,
  useWoStore,
} from "@/store/workorderstore";
import TotalCardModal from "@/components/repair/TotalCardModal";
import Box from "@mui/material/Box";
import Modal from "@mui/material/Modal";
import WoModal from "@/components/workorder/template";

interface Option {
  label: string;
  value: string;
}

interface JigData {
  date: string;
  serialNumber: string;
  model: string;
  status: string; // 가정으로 추가한 속성입니다.
}

export default function RepairTotal() {
  const [role, setRole] = useState<string>(""); // 초기 상태를 명시적으로 string 타입으로 설정
  const [show, setShow] = useState(false);

  useEffect(() => {
    // 컴포넌트가 클라이언트 사이드에서 마운트되었을 때 로컬 스토리지에서 role 읽기
    const storedRole = localStorage.getItem("role");
    if (storedRole !== null) {
      setRole(storedRole); // 로컬 스토리지의 값이 null이 아닌 경우에만 상태 업데이트
      fetchWo(values, 1, 5);
    }
  }, []);
  let Navbar;
  if (role === "MANAGER") {
    Navbar = <ManagerNav />;
  } else if (role === "ENGINEER") {
    Navbar = <EngineerNav />;
  } else {
    Navbar = <ManagerNav />; // 기본값으로 ManagerNav 설정
  }
  // 수리요청온 지그의 WO 에 따른 상태 데이터
  const lst: Option[] = [
    { label: "전체", value: "" },
    { label: "발행", value: "PUBLISH" },
    { label: "진행 중", value: "PROGRESS" },
    { label: "완료", value: "FINISH" },
  ];
  const [values, setValues] = useState<string>("");
  const [page, setPage] = useState(1);
  useEffect(() => {
    fetchWo(values, page, 5)
      .then((res) => {
        console.log(res);
        console.log(values);
        console.log(list);
      })
      .catch((error) => {
        console.log(error.message);
      });
  }, [page, values]);
  // 임시 JIG 데이터
  const jigData: JigData[] = [
    {
      date: "2024.04.21",
      serialNumber: "S/N S00000001",
      model: "Model Name",
      status: "발행",
    },
    {
      date: "2024.04.22",
      serialNumber: "S/N S00000002",
      model: "Model Name",
      status: "발행",
    },
    {
      date: "2024.04.23",
      serialNumber: "S/N S00000003",
      model: "Model Name",
      status: "발행",
    },
    {
      date: "2024.04.22",
      serialNumber: "S/N S00000002",
      model: "Model Name",
      status: "발행",
    },
    {
      date: "2024.04.23",
      serialNumber: "S/N S00000003",
      model: "Model Name",
      status: "발행",
    },
    // 다른 JIG 데이터 객체들...
  ];

  const { endPage, list, fetchWo } = useWoStore();
  const { fetchWoDetail } = useWoDetailStore();
  const { setWoId, setModalName, setModal, modal, modalName } = useCompoStore();
  const cardClick = (id: number) => {
    fetchWoDetail(id)
      .then((res) => {
        console.log(res);
        // setWoId(id);
        // setModalName("TOTAL");
        setShow(true);
        // setModal(true);
      })
      .catch((error) => {
        console.log(error.message);
      });
  };

  return (
    <>
      {Navbar}
      <div className={styled.bigContainer}>
        <div className={styled.containerLeft}>
          <div className={styled.right}>
            {role === "ENGINEER" ? (
              <Link
                href="/common/RepairTotal/MyTotal"
                // passHref
                underline="hover"
                style={{
                  color: "black",
                  fontSize: "12px",
                  fontWeight: "lighter",
                }}
              >
                나의 내역 보기
              </Link>
            ) : (
              <div></div>
            )}
            <Select
              selectionMode="single"
              placeholder="선택"
              onChange={(e) => {
                setValues(e.target.value);
              }}
              color="primary"
              className={styled.short}
              labelPlacement="outside"
              aria-label="status label"
            >
              {lst.map((option) => (
                <SelectItem key={option.value} value={option.value}>
                  {option.label}
                </SelectItem>
              ))}
            </Select>
          </div>
          <div className={styled.container}>
            {list.map((jig, index) => (
              <div
                key={index}
                onClick={() => cardClick(jig.id)}
                className={styled.fullWidth}
              >
                <div className={styled.inCard}>
                  <div className={styled.inInCard}>
                    <div>
                      {jig.createdAt[0]}. {jig.createdAt[1]}. {jig.createdAt[2]}
                    </div>
                    <div>
                      {jig.serialNo} | {jig.model}
                    </div>
                  </div>

                  <div>{jig.status}</div>
                </div>
              </div>
            ))}
          </div>
          <div className={styled.center}>
            <Pagination onChange={(e) => setPage(e)} total={endPage} />
          </div>
        </div>
        <div className={styled.verticalLine}></div>
        <div className={show ? styled.containerLeft : styled.displayNone}>
          <WoModal />
        </div>
      </div>

      {/* <Modal
        open={modal} // Corrected from 'open'
        onClose={() => {
          setModal(false);
        }} // Added onClose handler
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
          {modalName === "TOTAL" && <WoModal />}
        </Box>
      </Modal> */}
    </>
  );
}
