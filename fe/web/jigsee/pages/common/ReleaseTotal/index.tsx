import { Pagination, Select, SelectItem } from "@nextui-org/react";
import React, { useState, useEffect } from "react";
import styled from "@/styles/Total/Total.module.css";
import EngineerNav from "@/pages/engineer/navbar";
import ManagerNav from "@/pages/manager/navbar";
import { useReleaseStore } from "@/store/releasestore";
import Modal from "@mui/material/Modal";
import Box from "@mui/material/Box";
import { Typography } from "@mui/material";
interface item {
  createdAt: any;
  from: string;
  id: string;
  isAccept: boolean;
  serialNos: any;
  status: string;
  to: string;
  updatedAt: any;
}
interface Option {
  label: string;
  value: string;
}

interface Props {
  onClick(): void;
}

export default function RepairTotal() {
  const [open, setOpen] = useState(false);
  const { releaseList, fetchRelease, endPage } = useReleaseStore();
  const [role, setRole] = useState<string>(""); // 초기 상태를 명시적으로 string 타입으로 설정
  const defaultRelease: item = {
    createdAt: [2024, 5, 9],
    from: "기술팀",
    id: "string",
    isAccept: false,
    serialNos: [],
    status: "string",
    to: "string",
    updatedAt: [],
  };
  const [detail, setDetail] = useState<item>(defaultRelease);

  useEffect(() => {
    // 컴포넌트가 클라이언트 사이드에서 마운트되었을 때 로컬 스토리지에서 role 읽기
    const storedRole = localStorage.getItem("role");
    if (storedRole !== null) {
      setRole(storedRole); // 로컬 스토리지의 값이 null이 아닌 경우에만 상태 업데이트
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
    { label: "ALL", value: "ALL" },
    { label: "발행", value: "PUBLISH" },
    { label: "불출완료", value: "FINISH" },
    { label: "반려", value: "REJECT" },
  ];
  // 불출 상황 옵션 변수
  const [values, setValues] = useState<string>("ALL");
  const [page, setPage] = useState<number>(1);
  // page와 선택 옵션이 바뀜에 따라 api 호출
  useEffect(() => {
    setIsLoading(true); // API 호출 시작 전에 로딩 상태를 true로 설정
    fetchRelease(values, page, 5)
      .then(() => {
        setIsLoading(false); // 데이터를 성공적으로 받아온 후에 로딩 상태를 false로 설정
      })
      .catch(() => {
        setIsLoading(false); // 에러가 발생해도 로딩 상태를 false로 설정
      });
  }, [page, values, fetchRelease]);
  // 임시 JIG 데이터- api 요청으로 불러오기

  // 선택한 값에 따라 필터링된 jigData를 저장할 상태 변수
  const [filteredJigData, setFilteredJigData] = useState<any[]>([]);
  // 값이 변경될 때마다 필터링된 데이터 업데이트
  useEffect(() => {
    console.log("vvv", values);
    if (values === "ALL") {
      // "ALL"이면 전체 데이터 표시
      setFilteredJigData(releaseList || []); // releaseList가 undefined인 경우 빈 배열을 사용
    } else {
      // 선택한 값에 따라 jigData 필터링
      const filteredData = releaseList ? releaseList.filter((jig) => jig.status === values) : [];
      setFilteredJigData(filteredData);
    }
  }, [values, releaseList]);

  function cardClick(jig: item) {
    setOpen(true);
    setDetail(jig);
  }
  const [isLoading, setIsLoading] = useState(true);
  const detailChecked = () => {
    setOpen(false);
  };
  return (
    <>
      {Navbar}
      <div className={styled.bigcontainer}>
        <div className={styled.right}>
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
          {isLoading ? (
            <p>Loading...</p>
          ) : filteredJigData.length > 0 ? (
            filteredJigData.map((jig, index) => (
              <div key={index} onClick={() => cardClick(jig)} className={styled.fullWidth}>
                <p>
                  {jig.id} | 요청자 {jig.from} | status: {jig.status}
                </p>
              </div>
            ))
          ) : (
            <p>No Data</p> // 데이터가 없을 때 "No Data" 메시지 표시
          )}
        </div>
        <div className={styled.center}>
          <Pagination onChange={(e) => setPage(e)} total={endPage} />
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
          <Box
            sx={{
              width: "400px",
              height: "500px",
              backgroundColor: "var(--realwhite)",
              display: "flex",
              alignItems: "center",
              justifyContent: "start",
              flexDirection: "column",
              padding: "30px 30px",
              borderRadius: "10px",
              border: "solid 3px var(--samsungblue)",
            }}
          >
            <Typography
              color="primary"
              sx={{
                width: "100%",
                fontSize: "40px",
                marginTop: "10px",
                marginBottom: "10px",
                fontWeight: "5px",
              }}
              align="center"
            >
              {detail.isAccept && `승인 완료 `}
              {detail.isAccept === false && `미승인`}
            </Typography>

            <Box
              sx={{
                width: "200px",
                height: "150px",
                display: "flex", // Flexbox 레이아웃 사용
                flexDirection: "column", // 자식 요소를 세로로 정렬
                justifyContent: "center", // 자식 요소를 센터에 위치
                alignItems: "center", // 가로 방향에서도 중앙에 위치
                overflow: "auto", // 내용이 넘칠 경우 스크롤 생성
                padding: "8px", // 안쪽 여백 제공
                gap: "5px",
                marginTop: "20px",
              }}
            >
              <Typography sx={{ width: "100%", fontSize: "15px" }} align="left">
                {detail.createdAt.length > 0 &&
                  `요청일 : ${detail.createdAt[0]}년 ${detail.createdAt[1]}월 ${detail.createdAt[2]}일 `}
              </Typography>
              <Typography sx={{ width: "100%", fontSize: "15px" }} align="left">
                {detail.updatedAt.length > 0 &&
                  `최종승인 : ${detail.updatedAt[0]}년 ${detail.updatedAt[1]}월 ${detail.updatedAt[2]}일 `}
              </Typography>
              <Typography sx={{ width: "100%", fontSize: "15px" }} align="left">
                {detail.from && `요청자 : ${detail.from} `}
              </Typography>
              <Typography sx={{ width: "100%", fontSize: "15px" }} align="left">
                {detail.to && `승인자 : ${detail.to} `}
              </Typography>
            </Box>

            <Box
              sx={{
                width: "300px",
                height: "100px",
                display: "flex", // Flexbox 레이아웃 사용
                flexDirection: "column", // 자식 요소를 세로로 정렬
                justifyContent: "center", // 자식 요소를 센터에 위치
                alignItems: "center", // 가로 방향에서도 중앙에 위치
                overflow: "auto", // 내용이 넘칠 경우 스크롤 생성
                padding: "8px", // 안쪽 여백 제공
                marginTop: "10px",
                marginBottom: "20px",
                border: "1px solid var(--black)",
                borderRadius: "10px",
              }}
            >
              {detail.serialNos.map((jig: string, index: number) => (
                <div key={index} style={{ width: "100%", textAlign: "center" }}>
                  {jig}
                </div>
              ))}
            </Box>
            <button className={styled.btn} onClick={detailChecked}>
              확인
            </button>
          </Box>
        </Box>
      </Modal>
    </>
  );
}
