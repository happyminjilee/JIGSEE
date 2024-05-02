import { Pagination, Select, SelectItem, Selection } from "@nextui-org/react";
import { useState, useEffect } from "react";
import styled from "@/styles/Total/Total.module.css";
import EngineerNav from "@/pages/engineer/navbar";
import ManagerNav from "@/pages/manager/navbar";
import { useReleaseStore } from "@/store/releasestore";

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

interface Props {
  onClick(): void;
}

export default function RepairTotal() {
  const { releaseList, fetchRelease, endPage } = useReleaseStore();
  const [role, setRole] = useState<string>(""); // 초기 상태를 명시적으로 string 타입으로 설정
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
  const jigData: JigData[] = [
    { date: "2024.04.21", serialNumber: "S/N S00000001", model: "Model Name", status: "발행" },
    { date: "2024.04.22", serialNumber: "S/N S00000002", model: "Model Name", status: "발행" },
    { date: "2024.04.23", serialNumber: "S/N S00000003", model: "Model Name", status: "발행" },
    // 다른 JIG 데이터 객체들...
  ];
  function cardClick(jigid: string) {
    console.log("clicked", jigid);
  }
  const [isLoading, setIsLoading] = useState(true);
  return (
    <>
      {Navbar}

      <div className={styled.right}>
        <Select
          label="선택"
          selectionMode="single"
          placeholder="ALL"
          onChange={(e) => {
            setValues(e.target.value);
          }}
          className={styled.short}
          labelPlacement="outside-left"
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
        ) : releaseList.length > 0 ? (
          releaseList.map((jig, index) => (
            <div key={index} onClick={() => cardClick(jig.id)} className={styled.fullWidth}>
              <p>
                {jig.id} | 요청자 {jig.from} | 승인자 {jig.to} | 수정일 {jig.updatedAt[0]}년
                {jig.updatedAt[1]}월{jig.updatedAt[2]}일 status: {jig.status}
              </p>
            </div>
          ))
        ) : (
          <p>No data available.</p>
        )}
      </div>
      <div className={styled.center}>
        <Pagination onChange={(e) => setPage(e)} total={endPage} />
      </div>
    </>
  );
}
