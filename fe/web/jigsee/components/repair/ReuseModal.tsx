import styled from "@/styles/modal/reuseModal.module.css";
import { Button } from "@nextui-org/react";
import { Input } from "@nextui-org/react";
import {
  useCompoStore,
  useWoDetailStore,
  useWoGroupStore,
} from "@/store/workorderstore";
import { useState } from "react";
import ClearIcon from "@mui/icons-material/Clear";
import { updateJigStatus } from "@/pages/api/jigAxios";
import { RadioGroup, Radio } from "@nextui-org/react";

interface lst {
  model: string;
  model_name: string;
  count: number;
}

export default function reuseModal() {
  const { setModal, setModalName, setRightCompo } = useCompoStore();
  const { jigItemInfo } = useWoDetailStore();
  const { fetchWoGroup } = useWoGroupStore();
  const close = () => {
    setModal(false);
    setModalName("");
  };

  const [jigPos, setJigPos] = useState("");

  const reUseBtn = () => {
    updateJigStatus(jigItemInfo.serialNo, jigPos)
      .then((res) => {})
      .catch((error) => {})
      .finally(() => {
        fetchWoGroup()
          .then(() => {
            window.alert("요청 완료!");
          })
          .catch((error) => {
            window.alert("요청 실패!");
          })
          .finally(() => {
            close();
            window.location.reload();
          });
      });
  };

  return (
    <>
      <div className={styled.container}>
        <div
          className={styled.close}
          onClick={() => {
            close();
          }}
        >
          <ClearIcon />
        </div>
        <div className={styled.title}>Test 결과 재사용 가능</div>

        <div className={styled.content}>
          <RadioGroup
            label="보낼 위치"
            orientation="horizontal"
            onValueChange={setJigPos}
          >
            <Radio value="WAREHOUSE">창고</Radio>
            <Radio value="READY">불출 대기</Radio>
          </RadioGroup>
        </div>

        <Button
          style={{
            width: "250px",
            height: "40px",
            margin: "10px auto",
          }}
          color="primary"
          onPress={() => {
            reUseBtn();
          }}
        >
          보내기
        </Button>
      </div>
    </>
  );
}
