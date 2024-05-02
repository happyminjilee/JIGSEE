import { useState } from "react";
import { Link, Button } from "@nextui-org/react";
import styled from "@/styles/requestlist.module.css";
import { useDrag } from 'react-dnd';

interface lst {
  index: number;
  date: string;
  title: string;
  content: string[];
}
interface RequestListProps {
  onApproveClick: () => void;
  onReturnClick: () => void;
}
export default function RequestList({ onApproveClick, onReturnClick }: RequestListProps) {
  const lst = [
    {
      date: "2024.04.26",
      title: "생산팀인데 지그 주세요",
      content: ["지그 목록", "지그 목록", "지그 목록", "지그 목록", "지그 목록", "지그 목록"],
    },
    {
      date: "2024.04.25",
      title: "생산팀인데 지그 주세요",
      content: ["지그 목록", "지그 목록", "지그 목록"],
    },
    {
      date: "2024.04.24",
      title: "생산팀인데 지그 주세요",
      content: ["지그 목록", "지그 목록", "지그 목록"],
    },
  ];

  return (
    <>
      <div className={styled.box}>
        <div style={{ display: "flex", justifyContent: "space-between" }}>
          <div style={{ fontWeight: "bold", fontSize: "20px" }}>불출 요청</div>
          <Link href="#" underline="hover" style={{ color: "black" }}>
            상세 보기
          </Link>
        </div>

        {/*내부 박스*/}
        {lst.map((info, index) => (
          <div className={styled.container} key={index}>
            <div style={{ marginTop: "10px", marginLeft: "15px" }}>{info.date}</div>
            <div className={styled.card}>
              <div key={index} className={styled.division}>
                <div style={{ fontWeight: "bold", fontSize: "30px" }}>{info.title}</div>
                <div style={{ display: "flex", flexDirection: "row", flexWrap: "wrap" }}>
                  {info.content.map((index) => (
                    <div style={{ margin: "2px" }} key={index}> {index} </div>
                  ))}
                </div>
              </div>
              <div className={styled.division}>
                <Button
                  size="lg"
                  color="primary"
                  style={{ fontWeight: "bold", marginBottom: "5px" }}
                  onClick={onApproveClick}
                >
                  승인
                </Button>
                <Button size="lg" style={{ fontWeight: "bold" }} onClick={onReturnClick}>
                  반려
                </Button>
              </div>
            </div>
            <div></div>
            <div></div>
          </div>
        ))}
      </div>
    </>
  );
}
