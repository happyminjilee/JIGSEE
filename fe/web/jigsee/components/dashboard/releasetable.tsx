import React, { useEffect, useState } from "react";
import { Table, TableHeader, TableColumn, TableBody, TableRow, TableCell } from "@nextui-org/react";
import styled from "@/styles/dashboard/releasetable.module.scss";
import { useDashboardstore } from "@/store/dashboardstore";

interface Item {
  model: string;
  countReady: number;
  countWarehouse: number;
}

export default function Releasetable() {
  const { getJigcounts, modelscount } = useDashboardstore();
  useEffect(() => {
    getJigcounts();
  }, []);
  return (
    <>
      {" "}
      <Table
        aria-label="Example table with client side sorting"
        selectionMode="single"
        color="primary"
        className={styled.table}
      >
        <TableHeader>
          <TableColumn key="name">모델</TableColumn>
          <TableColumn key="warehouse">창고</TableColumn>
          <TableColumn key="factory">현장대기함</TableColumn>
        </TableHeader>
        <TableBody items={modelscount}>
          {(item) => (
            <TableRow key={item.model}>
              <TableCell>{item.model}</TableCell>
              <TableCell>{item.countWarehouse}</TableCell>
              <TableCell>{item.countReady}</TableCell>
            </TableRow>
          )}
        </TableBody>
      </Table>
    </>
  );
}
