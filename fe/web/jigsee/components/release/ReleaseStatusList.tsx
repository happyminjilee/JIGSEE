import {useState} from "react";
import {Link} from "@nextui-org/react";
import styled from "@/styles/releasestatuslist.module.css"

interface lst {
  requestId: number; // TypeScript에서는 long 타입이 없으므로 number를 사용합니다.
  from: string; // 요청자
  to: string; // 승인자
  model: string;
  count: number;
  requestedAt: string;
  state: string;
}



export default function ReleaseStatusList() {
  const lst= [
      {requestId: 1233, from: '주준형', to: '이민지', model: '회전 지그', count: 4, requestedAt: '2024.04.25', state: '승인 대기 중'},
      {requestId: 1234, from: '주준형', to: '박수형', model: '절단 지그', count: 5, requestedAt: '2024.04.24', state: '승인 완료'},
      {requestId: 1235, from: '주준형', to: '이민지', model: '잡는 지그', count: 6, requestedAt: '2024.04.24', state: '반려'},
      {requestId: 1231, from: '주준형', to: '이민지', model: '회전 지그', count: 4, requestedAt: '2024.04.25', state: '승인 대기 중'},
      {requestId: 1232, from: '주준형', to: '박수형', model: '절단 지그', count: 5, requestedAt: '2024.04.24', state: '승인 완료'},
      {requestId: 1230, from: '주준형', to: '이민지', model: '잡는 지그', count: 6, requestedAt: '2024.04.24', state: '반려'},
      {requestId: 1229, from: '주준형', to: '이민지', model: '회전 지그', count: 4, requestedAt: '2024.04.25', state: '승인 대기 중'},
      {requestId: 1228, from: '주준형', to: '박수형', model: '절단 지그', count: 5, requestedAt: '2024.04.24', state: '승인 완료'},
      {requestId: 1227, from: '주준형', to: '이민지', model: '잡는 지그', count: 6, requestedAt: '2024.04.24', state: '반려'},
  ]
  const cardClick = (requestId: number) => () => {
    console.log("clicked", requestId)
  }

  return (
      <>
        <div className={styled.box}>
          <div style={{display: "flex", justifyContent: "space-between", marginBottom: "15px"}}>
            <div style={{fontWeight: "bold", fontSize: "15px"}}>재고 불출 요청 내역</div>
            <Link
                href="/common/ReleaseTotal/"
                // passHref
                underline="hover"
                style={{color: "black", fontSize: "8px", fontWeight: "lighter"}}
            >
              전체 내역 보기
            </Link>
          </div>
          <div
              className={styled.contents}
          >
            {/* card */}
            {lst.map((info, index) => (
                <div
                    key={index}
                    className={styled.card}
                    onClick={cardClick(info.requestId)}
                >
                  <div
                      className={styled.division1}
                  >
                    <div
                        className={styled.date}
                    >
                      {info.requestedAt}
                    </div>
                    <div
                        className={styled.title}
                    >
                      {info.requestId}
                    </div>
                  </div>

                  <div
                      className={styled.division2}
                  >
                    {info.state}
                  </div>
                </div>
            ))}
          </div>

        </div>
      </>
  );
}
