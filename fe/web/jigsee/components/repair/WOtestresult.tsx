import React, { useEffect, useState } from "react";
import styles from "@/styles/wotestresult.module.scss"; // Corrected import
import Box from "@mui/material/Box";
import { DataGrid, GridColDef } from "@mui/x-data-grid";
import { useWoDetailStore } from "@/store/workorderstore";
// import { GridEditCellProps } from "@mui/x-data-grid";
interface testMethodItem {
  uuid: string;
  content: string;
  standard: string;
  measure: string;
  memo: string;
  passOrNot: boolean;
}
interface RowItem {
  id: string;
  contents: string;
  standard: string;
  measure: string;
  memo: string;
  passOrNot: boolean;
}
export default function WOtestresult() {
  // const { woId } = useWoStore();
  const [testMethod, setTestMethod] = useState<testMethodItem[]>([]);
  const { checkList, fetchWoDetail, id } = useWoDetailStore();
  useEffect(() => {
    fetchWoDetail(1);
    setTestMethod(checkList);
  }, [id]);

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
      headerName: "메모",
      width: 90,
      editable: true,
    },
    {
      field: "passOrNot",
      headerName: "판정 결과",
      width: 100,
      type: "boolean",
      editable: false,
    },
  ];
  const [rows, setRows] = useState<RowItem[]>([]);

  useEffect(() => {
    const newRows = testMethod.map((method) => ({
      id: method.uuid,
      contents: method.content,
      standard: method.standard,
      measure: method.measure,
      memo: method.memo,
      passOrNot: false,
    }));
    setRows(newRows);
  }, [testMethod]);
  const updateTest = () => {
    console.log("updaterow", updatedRows);
  };
  const [updatedRows, setUpdatedRows] = useState<string[]>([]);

  return (
    <div className={styles.container}>
      <div className={styles.header}>Test Result</div>
      <div className={styles.body}>
        <div className={styles.inputname}>
          <label htmlFor="inputname">Technician </label>
          <input type="text" className={styles.inputname} />
        </div>

        <Box
          className={styles.box}
          sx={{
            "& .MuiDataGrid-cell--editable": {
              bgcolor: "#ffffff",
              border: "1px solid var(--samsungblue)",
            },
          }}
        >
          <DataGrid
            rows={rows}
            columns={columns}
            disableColumnMenu
            disableColumnSorting
            disableColumnResize
            disableVirtualization
            hideFooter
            editMode="row"
          />
        </Box>
        <button onClick={updateTest} className={styles.editbtn}>
          수정
        </button>
      </div>
    </div>
  );
}
