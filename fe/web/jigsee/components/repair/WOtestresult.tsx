import React, { useEffect, useState } from "react";
import styles from "@/styles/wotestresult.module.scss"; // Corrected import
import { DatePicker } from "@mui/x-date-pickers/DatePicker";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import Box from "@mui/material/Box";
import { DataGrid, GridColDef } from "@mui/x-data-grid";
import { getjigMethod } from "@/pages/api/jigAxios";
import { usewoStore } from "@/store/workorderstore";
interface testMethodItem {
  content: string;
  standard: string;
}
interface RowItem {
  id: number;
  contents: string;
  standard: string;
  measure: string;
  memo: string;
  passOrNot: boolean;
}
export default function WOtestresult() {
  const { woId, openWotest, setopenWotest } = usewoStore();
  const [testMethod, setTestMethod] = useState<testMethodItem[]>([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        console.log("tttt", woId);
        const result = await getjigMethod("testModelId");
        console.log(result.data.result.list);
        setTestMethod(result.data.result.list);
      } catch (error) {
        console.error("Failed to fetch data:", error);
      }
    };

    fetchData();
  }, [woId]);

  const columns: GridColDef<(typeof rows)[number]>[] = [
    { field: "contents", headerName: "기준 항목", width: 90 },
    {
      field: "standard",
      headerName: "기준 값",
      width: 90,
    },
    {
      field: "measure",
      headerName: "측정 값",
      width: 90,
      editable: true,
    },
    {
      field: "memo",
      headerName: "비고",
      width: 90,
      editable: true,
    },
    {
      field: "passOrNot",
      headerName: "판정 결과",
      width: 100,
      type: "boolean",
      editable: true,
    },
  ];
  const [rows, setRows] = useState<RowItem[]>([]);

  useEffect(() => {
    const newRows = testMethod.map((method, index) => ({
      id: index + 1,
      contents: method.content,
      standard: method.standard,
      measure: "",
      memo: "",
      passOrNot: false,
    }));
    setRows(newRows);
  }, [testMethod]);

  return (
    <div className={styles.container}>
      <div className={styles.header}>Test Result</div>
      <div className={styles.body}>
        <div className={styles.inputname}>
          <label htmlFor="inputname">책임자</label>
          <input type="text" className={styles.inputname} />
        </div>

        <div className={styles.datepicker}>
          <LocalizationProvider dateAdapter={AdapterDayjs}>
            <DatePicker className={styles.dateInput} label="시작일" format="YYYY-M-D" />
          </LocalizationProvider>
          <LocalizationProvider dateAdapter={AdapterDayjs}>
            <DatePicker className={styles.dateInput} label="종료일" format="YYYY-M-D" />
          </LocalizationProvider>
        </div>

        <Box className={styles.box}>
          <DataGrid
            rows={rows}
            columns={columns}
            disableColumnMenu
            disableColumnSorting
            disableColumnResize
            disableVirtualization
            hideFooter
          />
        </Box>
        <button className={styles.editbtn}>수정</button>
      </div>
    </div>
  );
}
