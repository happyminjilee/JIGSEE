import React, { useEffect, useState, useCallback } from "react";
import {
  Table,
  TableHeader,
  TableColumn,
  TableBody,
  TableRow,
  TableCell,
  Input,
} from "@nextui-org/react";
import styled from "@/styles/dashboard/releasetable.module.scss";
import { useDashboardstore } from "@/store/dashboardstore";

interface Item {
  model: string;
  countReady: number;
  countWarehouse: number;
}

export default function Releasetable() {
  const { getJigcounts, modelscount, setJigmodel, jigmodel } =
    useDashboardstore();
  const [filterValue, setFilterValue] = useState("");
  const [filteredItems, setFilteredItems] = useState<Item[]>([]);

  useEffect(() => {
    getJigcounts();
  }, [getJigcounts]);

  useEffect(() => {
    // Input 값에 따라 필터링
    setFilteredItems(
      modelscount.filter((item) =>
        item.model.toLowerCase().includes(filterValue.toLowerCase())
      )
    );
  }, [filterValue, modelscount]);
  // Input value가 바뀌는 것을 감지하는 함수
  const onSearchChange = useCallback(
    (event: React.ChangeEvent<HTMLInputElement>) => {
      setFilterValue(event.target.value);
    },
    []
  );

  // Table 행이 선택되면 호출되는 함수
  const onRowSelectionChange = (keys: "all" | Set<React.Key>) => {
    if (keys !== "all" && keys.size > 0) {
      const selectedKey = Array.from(keys)[0];
      const selectedItem = modelscount.find(
        (item) => item.model === selectedKey
      );
      if (selectedItem) {
        setJigmodel(selectedItem.model);
      }
    }
  };
  return (
    <>
      <div className={styled.container}>
        <Input
          placeholder="model 검색"
          onChange={onSearchChange}
          className={styled.input}
          variant="bordered"
        />
        <Table
          aria-label="Example table with client side sorting"
          selectionMode="single"
          color="primary"
          className={styled.table}
          fullWidth
          isHeaderSticky
          onSelectionChange={onRowSelectionChange}
        >
          <TableHeader>
            <TableColumn key="name">모델</TableColumn>
            <TableColumn key="warehouse">창고</TableColumn>
            <TableColumn key="factory">현장대기함</TableColumn>
          </TableHeader>
          <TableBody items={filteredItems}>
            {(item) => (
              <TableRow key={item.model}>
                <TableCell>{item.model}</TableCell>
                <TableCell>{item.countWarehouse}</TableCell>
                <TableCell>{item.countReady}</TableCell>
              </TableRow>
            )}
          </TableBody>
        </Table>
      </div>
    </>
  );
}
