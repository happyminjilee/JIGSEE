import React from "react";
import styled from "@/styles/loading.module.css";

export default function Loading() {
  return (
    <>
      <div className={styled.container}>
        <div style={{ width: "900px" }}>
          <img src="/images/Loadinglogo.gif" alt="loading" />
        </div>
      </div>
    </>
  );
}
