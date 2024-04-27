import React, { useState } from "react";
import styled from "@/styles/jigrequest.module.scss";
import { Card, CardHeader, CardBody, CardFooter } from "@nextui-org/react";
import { Table, TableHeader, TableColumn, TableBody, TableRow, TableCell } from "@nextui-org/react";
import { Select, SelectItem, Divider, Input } from "@nextui-org/react";
interface RowData {
  key: number;
  facility: string;
  model: string;
  amount: number;
  [key: string]: any; // 인덱스 시그니처 추가
}

export default function Request() {
  const [rows, setRows] = useState<RowData[]>([]);
  const [selectedFacility, setSelectedFacility] = useState("");
  const [selectedModel, setSelectedModel] = useState("");
  const [amount, setAmount] = useState("");
  // 설비명 리스트 더미
  const facilities = ["line cutter", "raser", "metalize"];
  // 모델명 리스트 더미
  const models = ["swf235430", "dfdg456872", "ddg24652"];
  // 테이블 열이름
  const columns = [
    {
      key: "facility",
      label: "설비",
    },
    {
      key: "model",
      label: "Jig 모델",
    },
    {
      key: "amount",
      label: "수량",
    },
    {
      key: "action",
      label: "삭제",
    },
  ];
  // 추가 버튼 함수
  const handleAddRow = () => {
    const newRow = {
      key: rows.length + 1,
      facility: selectedFacility,
      model: selectedModel,
      amount: Number(amount),
    };
    setRows([...rows, newRow]);
  };
  const handleResetRow = () => {
    setRows([]);
  };
  // 특정 행 삭제 함수
  const handleDeleteRow = (key: number) => {
    setRows(rows.filter((row) => row.key !== key));
  };

  return (
    <>
      <Card className={styled.requestcontainer}>
        <CardHeader className={styled.cardheader}>불출 요청</CardHeader>
        <Divider className={styled.headerline} />
        <CardBody>
          <div className={styled.cardbody}>
            <Select
              variant="bordered"
              label="설비 이름"
              labelPlacement="outside-left"
              placeholder="설비를 선택 하세요"
              onChange={(e) => setSelectedFacility(e.target.value)}
              className={styled.select}
            >
              {facilities.map((facility) => (
                <SelectItem key={facility} value={facility}>
                  {facility}
                </SelectItem>
              ))}
            </Select>
            <Select
              variant="bordered"
              label="Jig model"
              labelPlacement="outside-left"
              placeholder="모델을 선택 하세요"
              className={styled.select}
              onChange={(e) => setSelectedModel(e.target.value)}
            >
              {models.map((model) => (
                <SelectItem key={model} value={model}>
                  {model}
                </SelectItem>
              ))}
            </Select>
            <Input
              type="number"
              variant="bordered"
              labelPlacement="outside-left"
              label="수량"
              className={styled.amountinput}
              onChange={(e) => setAmount(e.target.value)}
            />
          </div>

          <div className={styled.btncontainer}>
            <button className={styled.addbtn} onClick={handleAddRow}>
              추가
            </button>
            <button className={styled.resetbtn} onClick={handleResetRow}>
              초기화
            </button>
          </div>
          <div className={styled.tableScrollContainer}>
            <Table className={styled.table} aria-label="불출 요청 리스트 테이블">
              <TableHeader className={styled.tableheader} columns={columns}>
                {(column) => <TableColumn key={column.key}>{column.label}</TableColumn>}
              </TableHeader>

              <TableBody items={rows}>
                {(item) => (
                  <TableRow className={styled.tableScrollContainer} key={item.key}>
                    {columns.map((column) => (
                      <TableCell key={`${item.key}-${column.key}`}>
                        {column.key !== "action" ? (
                          item[column.key]
                        ) : (
                          <img
                            className={styled.icon}
                            src="/images/delete_gray.svg"
                            alt="delete_icon"
                            onClick={() => handleDeleteRow(item.key)}
                          />
                        )}
                      </TableCell>
                    ))}
                  </TableRow>
                )}
              </TableBody>
            </Table>
          </div>
        </CardBody>
        <Divider />
        <CardFooter className={styled.cardfooter}>
          <button className={styled.addbtn}>제출</button>
        </CardFooter>
      </Card>
    </>
  );
}
