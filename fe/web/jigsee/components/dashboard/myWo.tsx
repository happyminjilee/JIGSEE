import styled from "@/styles/dashboard/mywo.module.css";
import { useUserWoListStore } from "@/store/workorderstore";
import { useEffect, useState } from "react";
import { Link } from "@nextui-org/react";
import VerifiedIcon from "@mui/icons-material/Verified";
import RadioButtonUncheckedIcon from "@mui/icons-material/RadioButtonUnchecked";
import BuildCircleIcon from "@mui/icons-material/BuildCircle";
import CheckCircleIcon from "@mui/icons-material/CheckCircle";

export default function MyWoList() {
  const { list } = useUserWoListStore();
  console.log("list", list);
  return (
    <>
      <div className={styled.box}>
        <div className={styled.title}> 나의 수리 진행 내역</div>
        <div className={styled.content}>
          {list.map((lst, index) => (
            <div className={styled.card}>
              {lst.status === "PUBLISH" && (
                <div>
                  <RadioButtonUncheckedIcon color="primary" /> 발행
                </div>
              )}
              {lst.status === "PROGRESS" && (
                <div>
                  <BuildCircleIcon color="primary" /> 진행 중
                </div>
              )}
              {lst.status === "FINISH" && (
                <div>
                  <CheckCircleIcon color="primary" /> 완료
                </div>
              )}
              <div>{lst.model}</div>
              {lst.updatedAt === "" ? (
                <div>lst.createdAt</div>
              ) : (
                <div>lst.updatedAt | lst.terminator</div>
              )}
            </div>
          ))}
        </div>
        <div className={styled.link}>
          <Link href="/common/RepairTotal/MyTotal">전체 보기</Link>
        </div>
      </div>
    </>
  );
}
