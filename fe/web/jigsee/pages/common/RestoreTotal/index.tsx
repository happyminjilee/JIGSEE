import { Pagination, Selection } from "@nextui-org/react";
import { useState, useEffect } from "react";
import styled from "@/styles/Total/Total.module.css";
import EngineerNav from "@/pages/engineer/navbar";
import ManagerNav from "@/pages/manager/navbar";
import { getJigrestorerList } from "@/pages/api/restoreAxios";
import { useRestoreStore } from "@/store/restorestore";
interface Option {
  label: string;
  value: string;
}

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
  }, []);
  let Navbar;
  if (role === "MANAGER") {
    Navbar = <ManagerNav />;
  } else if (role === "ENGINEER") {
    Navbar = <EngineerNav />;
  } else {
    Navbar = <ManagerNav />; // 기본값으로 ManagerNav 설정
  }

  const { restoreList, page, setPage, getRestoreList, endpage } = useRestoreStore();
  useEffect(() => {
    getRestoreList(); // Fetch list whenever the page changes
  }, [page, getRestoreList]);

  // function cardClick(jig: JigData) {
  //   console.log("clicked", jig);
  // }

  return (
    <>
      {Navbar}
      <div className={styled.container}>
        {restoreList.map((item, index) => (
          <div key={index} className={styled.fullWidth}>
            <h3>생성일 {item.createdAt.split("T")[0]}</h3>
            <p>
              보수 요청 번호 {item.id} | 요청자 {item.from}
            </p>
          </div>
        ))}
      </div>
      <div className={styled.center}>
        <Pagination onChange={(e) => setPage(e)} total={endpage} />
      </div>
    </>
  );
}
