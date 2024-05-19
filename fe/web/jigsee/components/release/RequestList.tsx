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
export default function RequestList({
  onApproveClick,
  onReturnClick,
}: RequestListProps) {
  const { releaseList, fetchRelease } = useReleaseStore();
  const {} = useButtonClickStore();
  useEffect(() => {
    fetchRelease("PUBLISH", 1, 10)
      .then((res) => {})
      .catch((error) => {});
  }, []);

  const router = useRouter();
  const linkClick = () => {
    router.push("/common/ReleaseTotal");
  };

  return (
    <>
      <div className={styled.box}>
        <div style={{ display: "flex", justifyContent: "space-between" }}>
          <div
            style={{
              fontWeight: "bold",
              fontSize: "20px",
              margin: "auto 10px",
            }}
          >
            불출 요청
          </div>
          <Link
            onClick={linkClick}
            underline="hover"
            style={{
              color: "black",
              cursor: "pointer",
              margin: "auto 10px",
              fontSize: "12px",
            }}
          >
            상세 보기
          </Link>
        </div>

        {/*내부 박스*/}
        <div className={styled.contents}>
          {releaseList.map((info, index) => (
            <div className={styled.container} key={index}>
              <div style={{ marginTop: "10px", marginLeft: "15px" }}>
                {info.createdAt[0]}.{info.createdAt[1]}.{info.createdAt[2]}
              </div>
              <div className={styled.card}>
                <div key={index} className={styled.division}>
                  <div style={{ fontWeight: "bold", fontSize: "20px" }}>
                    {info.id}
                  </div>
                  <div
                    style={{
                      display: "flex",
                      flexDirection: "row",
                      flexWrap: "wrap",
                    }}
                  >
                    <div style={{ margin: "2px" }}> 요청자 : {info.from} </div>
                    <div style={{ margin: "2px" }}> {info.to} </div>
                  </div>
                </div>
                <div className={styled.division}>
                  <Button
                    size="md"
                    color="primary"
                    style={{ fontWeight: "bold", marginBottom: "5px" }}
                    onClick={() => onApproveClick(info.id)}
                  >
                    승인
                  </Button>
                  <Button
                    size="md"
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
      </div>
    </>
  );
}
