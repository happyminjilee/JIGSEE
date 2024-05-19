import { useEffect, useState } from "react";

import styled from "@/styles/stocklist.module.css";
import { usejigStore } from "@/store/jigstore";

export default function StockList() {
  const { stockList, setStockList } = usejigStore();
  useEffect(() => {
    setStockList(); // 예: 서버에서 재고 데이터를 가져와 상태를 설정
  }, []);

  return (
    <>
      <div className={styled.box}>
        <div style={{ display: "flex", justifyContent: "space-between" }}>
          <div style={{ fontWeight: "bold", fontSize: "20px" }}>재고 현황</div>
        </div>
        <div className={styled.contents}>
          {stockList.length > 0 ? (
            stockList.map((stock, index) => (
              <div key={index} className={styled.card}>
                <p style={{ fontWeight: "bold" }}>{stock.model}</p>
                <p>{stock.count}개</p>
              </div>
            ))
          ) : (
            <div className={styled.card}>재고 없음</div>
          )}
        </div>
      </div>
    </>
  );
}
