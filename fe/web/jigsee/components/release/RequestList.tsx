import { useEffect, useState } from "react";
import { Link, Button } from "@nextui-org/react";
import styled from "@/styles/requestlist.module.css";
import { useRouter } from "next/router";
import { useButtonClickStore, useReleaseStore } from "@/store/releasestore";
import { useDrag } from "react-dnd";

interface lst {
  index: number;
  date: string;
  title: string;
  content: string[];
}
interface RequestListProps {
  onApproveClick: (id: string) => void;
  onReturnClick: (id: string) => void;
}
export default function RequestList({ onApproveClick, onReturnClick }: RequestListProps) {
  const { releaseList, fetchRelease } = useReleaseStore();
  const {} = useButtonClickStore();
  useEffect(() => {
    fetchRelease("PUBLISH", 1, 10)
      .then((res) => {
        console.log(res);
      })
      .catch((error) => {
        console.log(error.message);
      });
  }, []);

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
  const router = useRouter();
  const linkClick = () => {
    router.push("/common/ReleaseTotal");
  };

  return (
    <>
      <div className={styled.box}>
        <div style={{ display: "flex", justifyContent: "space-between" }}>
          <div style={{ fontWeight: "bold", fontSize: "20px" }}>불출 요청</div>
          <Link onClick={linkClick} underline="hover" style={{ color: "black", cursor: "pointer" }}>
            상세 보기
          </Link>
        </div>

        {/*내부 박스*/}
        {releaseList.map((info, index) => (
          <div className={styled.container} key={index}>
            <div style={{ marginTop: "10px", marginLeft: "15px" }}>{info.createAt}</div>
            <div className={styled.card}>
              <div key={index} className={styled.division}>
                <div style={{ fontWeight: "bold", fontSize: "30px" }}>{info.id}</div>
                <div style={{ display: "flex", flexDirection: "row", flexWrap: "wrap" }}>
                  <div style={{ margin: "2px" }}> {info.from} </div>
                  <div style={{ margin: "2px" }}> {info.to} </div>
                </div>
              </div>
              <div className={styled.division}>
                <Button
                  size="lg"
                  color="primary"
                  style={{ fontWeight: "bold", marginBottom: "5px" }}
                  onClick={() => onApproveClick(info.id)}
                >
                  승인
                </Button>
                <Button
                  size="lg"
                  style={{ fontWeight: "bold" }}
                  onClick={() => onReturnClick(info.id)}
                >
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
