import React, { useEffect, useState } from "react";
import styled from "@/styles/dashboard/report.module.scss";
import { useDashboardstore } from "@/store/dashboardstore";

export default function Report() {
  const { getJignumbers, deleted, change, request, finish } = useDashboardstore();
  useEffect(() => {
    getJignumbers();
  }, []);
  return (
    <>
      <div className={styled.report}>
        <div className={styled.card}>
          <div className={styled.title}>이번달 폐기된 Jig</div>
          <div className={styled.views}>{deleted}</div>
        </div>
        <div className={styled.card}>
          <div className={styled.title}>이번달 교체된 Jig</div>
          <div className={styled.views}>{change}</div>
        </div>
        <div className={styled.card}>
          <div className={styled.title}>수리 요청 Jig</div>
          <div className={styled.views}>{request}</div>
        </div>
        <div className={styled.card}>
          <div className={styled.title}>수리 완료 Jig</div>
          <div className={styled.views}>{finish}</div>
        </div>
      </div>
    </>
  );
}
