import React, { useState } from "react";
import styled from "@/styles/jigrequest.module.scss";
import { Card, CardHeader, CardBody, CardFooter } from "@nextui-org/react";
import { Select, SelectItem, Divider, Input } from "@nextui-org/react";

// Step 1: prop types 정의
interface EditStandardProps {
  onClose: () => void; // Function that will be called to close the modal
}
interface RowData {
  id: number;
  method: string;
  standard: string;
}
export default function EditStandard({ onClose }: EditStandardProps) {
  const [selectedFacility, setSelectedFacility] = useState("");
  const [selectedModel, setSelectedModel] = useState("");

  // 설비명 리스트 더미
  const facilities = ["line cutter", "raser", "metalize"];
  // 모델명 리스트 더미
  const models = ["swf235430", "dfdg456872", "ddg24652"];

  // Row 타입의 배열로 rows 상태를 정의합니다.
  const [rows, setRows] = useState<RowData[]>([{ id: 0, method: "", standard: "" }]);

  // 새로운 행을 추가하는 함수
  const addRow = () => {
    setRows([
      ...rows,
      { id: rows.length + 1, method: "", standard: "" }, // Added an `id` and changed 'contents' to 'method'
    ]);
  };

  // 특정 행의 데이터를 변경하는 함수
  const updateRow = (index: number, field: keyof RowData, value: string) => {
    const newRow = [...rows];
    if (field === "method" || field === "standard") {
      newRow[index][field] = value; // Ensuring the field matches the RowData keys
      setRows(newRow);
    }
  };
  return (
    <>
      <Card className={styled.requestcontainer}>
        <CardHeader className={styled.cardheader}>지그 점검 항목 수정</CardHeader>
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
            <div className={styled.addbtnline}>
              보수 방법{" "}
              <img
                className={styled.icon}
                src="/images/add_blue_btn.svg"
                alt="add-button"
                onClick={addRow}
              />
            </div>
            <Divider className={styled.headerline} />

            {rows.map((row, index) => (
              <div key={index} className={styled.rowcontainer}>
                <label>
                  방법
                  <input
                    type="text"
                    value={row.method}
                    onChange={(e) => updateRow(index, "method", e.target.value)}
                  />
                </label>
                <label>
                  기준
                  <input
                    type="text"
                    value={row.standard}
                    onChange={(e) => updateRow(index, "standard", e.target.value)}
                  />
                </label>
              </div>
            ))}
          </div>
        </CardBody>

        <CardFooter className={styled.cardfooter}>
          <button className={styled.addbtn} onClick={onClose}>
            제출
          </button>
        </CardFooter>
      </Card>
    </>
  );
}
