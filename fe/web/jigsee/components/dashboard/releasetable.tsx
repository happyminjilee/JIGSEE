import * as React from "react";
import { Table, TableHeader, TableColumn, TableBody, TableRow, TableCell } from "@nextui-org/react";
import styled from "@/styles/dashboard/releasetable.module.scss";

interface Item {
  name: string;
  warehouse: number;
  factory: number;
}

const list: Item[] = [
  { name: "js1", warehouse: 159, factory: 6 },
  { name: "js2", warehouse: 237, factory: 9 },
  { name: "js3", warehouse: 262, factory: 16 },
  { name: "js4", warehouse: 305, factory: 8 },
  { name: "js5", warehouse: 12, factory: 2 },
  { name: "js6", warehouse: 258, factory: 4 },
];

export default function Releasetable() {
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
          <TableColumn key="factory">공장</TableColumn>
        </TableHeader>
        <TableBody items={list}>
          {(item) => (
            <TableRow key={item.name}>
              <TableCell>{item.name}</TableCell>
              <TableCell>{item.warehouse}</TableCell>
              <TableCell>{item.factory}</TableCell>
            </TableRow>
          )}
        </TableBody>
      </Table>
    </>
  );
}
