import {useState} from "react";
import {Link} from "@nextui-org/react";
import styled from "@/styles/stocklist.module.css"

// interface Option {
//   model: string;
//   count: number;
// }

interface lst {
  model: string;
  count: number;
}

export default function StockList() {
  const lst = [
      {model: '회전 지그', count: 4},
      {model: '충돌 지그', count: 3},
      {model: '집게 지그', count: 2},
  ]

  // function wordclick() {
  //     console.log("clicked")
  // }

  return (
      <>
        <div className={styled.box}>
            <div style={{display: "flex", justifyContent: "space-between"}}>
                <div style={{fontWeight: "bold", fontSize: "25px"}}>재고 현황</div>
                <Link
                    href="#"
                    underline="hover"
                    style={{color: "black"}}
                >
                    상세 보기
                </Link>
            </div>
            {lst.map((mod, index) => (
              <div
                key={index}
                className={styled.card}
              >
                <p style={{fontWeight: "bold"}}>{mod.model}</p>
                <p>{mod.count}개</p>
              </div>
            ))}
        </div>
      </>
  )
}
