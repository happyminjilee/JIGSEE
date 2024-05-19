import React, { useEffect, useState } from "react";
import styled from "@/styles/dashboard/report.module.scss";
import { useDashboardstore } from "@/store/dashboardstore";
import CircularProgress from '@mui/material/CircularProgress';

export default function Report() {
  const { getJignumbers, deleted, change, request, finish, isLoading , setIsLoading} = useDashboardstore();
  useEffect(() => {
    setIsLoading(true)
    getJignumbers()
        .finally(() => {
          setIsLoading(false)
        })
    const interval = setInterval(getJignumbers, 10000)
    return () => clearInterval(interval)
  }, []);
  return (
    <>
      <div className={styled.report}>
        <div className={styled.card}>
          <div className={styled.title}>이번달 폐기된 Jig</div>
          <div className={styled.views}>
            {isLoading ? <CircularProgress/> : `${deleted}`}

          </div>
        </div>
        <div className={styled.card}>
          <div className={styled.title}>이번달 교체된 Jig</div>
          <div className={styled.views}>
            {isLoading ? <CircularProgress/> : `${change}`}
          </div>
        </div>
        <div className={styled.card}>
          <div className={styled.title}>수리 요청 Jig</div>
          <div className={styled.views}>
            {isLoading ? <CircularProgress/> : `${request}`}
          </div>
        </div>
        <div className={styled.card}>
          <div className={styled.title}>수리 완료 Jig</div>
          <div className={styled.views}>
            {isLoading ? <CircularProgress/> : `${finish}`}
          </div>
        </div>
      </div>
    </>
  );
}
