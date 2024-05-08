import React, { useEffect, useState } from "react";
import styles from "@/styles/wotestresult.module.scss"; // Corrected import
import Box from "@mui/material/Box";
import { DataGrid, GridColDef } from "@mui/x-data-grid";
import {useCompoStore, useWoDetailStore} from "@/store/workorderstore";
import ClearIcon from "@mui/icons-material/Clear";
import {
  GridRowsProp,
  GridRowModesModel,
  GridRowModes,
  GridActionsCellItem,
  GridEventListener,
  GridRowId,
  GridRowModel,
  GridRowEditStopReasons,
} from "@mui/x-data-grid";
import EditIcon from "@mui/icons-material/Edit";
import SaveIcon from "@mui/icons-material/Save";

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
  isNew?: boolean; // Add isNew property here
}
interface EditToolbarProps {
  setRows: (newRows: (oldRows: GridRowsProp) => GridRowsProp) => void;
  setRowModesModel: (newModel: (oldModel: GridRowModesModel) => GridRowModesModel) => void;
}
export default function WOtestresult() {
  // const { woId } = useWoStore();
  const [testMethod, setTestMethod] = useState<testMethodItem[]>([]);
  const { checkList, fetchWoDetail, id, fetchWoUpdateTmp, fetchWoDone } = useWoDetailStore();
  const {setRightCompo, setWoId} = useCompoStore()
  // id 가 바뀔때마다 새로운 리스트를 불러옴
  useEffect(() => {
    // id를 입력하는 것으로 추후 수정해야함
    fetchWoDetail(id);
    setTestMethod(checkList);
  }, [id]);

  const columns: GridColDef[] = [
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
      editable: true,
    },
    {
      field: "actions",
      type: "actions",
      headerName: "수정",
      width: 100,
      cellClassName: "actions",
      getActions: ({ id }) => {
        const isInEditMode = rowModesModel[id]?.mode === GridRowModes.Edit;

        if (isInEditMode) {
          return [
            <GridActionsCellItem
              icon={<SaveIcon />}
              label="Save"
              sx={{
                color: "primary.main",
              }}
              onClick={handleSaveClick(id)}
            />,
          ];
        }

        return [
          <GridActionsCellItem
            icon={<EditIcon />}
            label="Edit"
            className="textPrimary"
            onClick={handleEditClick(id)}
            color="inherit"
          />,
        ];
      },
    },
  ];
  const [rows, setRows] = useState<RowItem[]>([]);

  useEffect(() => {
    const newRows: RowItem[] = testMethod.map((method) => ({
      id: method.uuid,
      contents: method.content,
      standard: method.standard,
      measure: method.measure,
      memo: method.memo,
      passOrNot: false,
    }));
    setRows(newRows);
  }, [testMethod]);

  const [rowModesModel, setRowModesModel] = React.useState<GridRowModesModel>({});

  // 행 수정 중지 이벤트 핸들러
  const handleRowEditStop: GridEventListener<"rowEditStop"> = (params, event) => {
    if (params.reason === GridRowEditStopReasons.rowFocusOut) {
      event.defaultMuiPrevented = true;
    }
  };

  // 수정 버튼 클릭 핸들러
  const handleEditClick = (id: GridRowId) => () => {
    setRowModesModel({ ...rowModesModel, [id]: { mode: GridRowModes.Edit } });
  };

  // 저장 버튼 클릭 핸들러
  const handleSaveClick = (id: GridRowId) => () => {
    setRowModesModel({ ...rowModesModel, [id]: { mode: GridRowModes.View } });
  };

  // 행 업데이트 처리 함수
  const processRowUpdate = (newRow: GridRowModel) => {
    // id 값으로 있는 행인지 판별
    const existingRow = rows.find((row) => row.id === newRow.id);

    // row가 있다면 업데이트
    if (existingRow) {
      const updatedRow: RowItem = { ...existingRow, ...newRow, isNew: false };
      setRows(rows.map((row) => (row.id === newRow.id ? updatedRow : row)));
      return updatedRow;
    }

    // row가 없다면 newRow 리턴
    return newRow;
  };
  // 행 모드 변경 핸들러
  const handleRowModesModelChange = (newRowModesModel: GridRowModesModel) => {
    setRowModesModel(newRowModesModel);
  };

  // 임시저장 로직
  const updateTest = () => {
    // 바뀐 행 출력
    console.log("updaterow", rows);
    const newList = rows.map((item) => ({
      uuid: item.id,
      content: item.contents,
      standard: item.standard,
      measure: item.measure,
      memo: item.memo,
      passOrNot: item.passOrNot,
    }));
    console.log("newrow", newList);
    // 1대신 id를 입력하는 것으로 추후 수정해야함
    fetchWoUpdateTmp(id, newList);
  };


  // 제출 로직

  const {modalName, setModalName, setModal} = useCompoStore()
  const submitTest = () => {
    const newList = rows.map((item) => ({
      uuid: item.id,
      content: item.contents,
      standard: item.standard,
      measure: item.measure,
      memo: item.memo,
      passOrNot: item.passOrNot,
    }));
    // 1대신 id를 입력하는 것으로 추후 수정해야함
    fetchWoDone(id, newList)
        .then((res) => {
          console.log('look at me', res)
          if (res) {
            setModalName("REUSE")
            setModal(true)
          } else {
            setModalName("DISPOSE")
            setModal(true)
          }
        })
        .catch((error) => {
            window.alert("요청 실패!")
            clear()
        })
  };
  const clear = () => {
    setRightCompo("PROGRESS")
  }

  return (
    <div className={styles.container}>
      <div
          className={styles.clear}
          onClick={clear}
      >
        <ClearIcon/>
      </div>
      <div className={styles.header}>Test Result</div>
      <div className={styles.body}>
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
            rowModesModel={rowModesModel}
            onRowModesModelChange={handleRowModesModelChange}
            onRowEditStop={handleRowEditStop}
            processRowUpdate={processRowUpdate}
          />
        </Box>
        <div className={styles.btncontianer}>
          <button onClick={updateTest} className={styles.editbtn}>
            임시 저장
          </button>
          <button onClick={submitTest} className={styles.submitbtn}>
            제출
          </button>
        </div>
      </div>
    </div>
  );
}
