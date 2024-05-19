import {
  Modal,
  ModalContent,
  ModalHeader,
  ModalBody,
  ModalFooter,
} from "@nextui-org/react";
import { Divider, Textarea } from "@nextui-org/react";
import styles from "@/styles/modal/releasereturn.module.css";
import {
  useButtonClickStore,
  useReleaseDetailStore,
  useReleaseStore,
} from "@/store/releasestore";
import React, { useEffect, useState } from "react";
import { releaseResponse } from "@/pages/api/releaseAxios";
// Props에 대한 인터페이스 정의
interface ReturnProps {
  onClose: () => void; // 이 함수는 파라미터를 받지 않고 void를 반환함
}
export default function Return({ onClose }: ReturnProps) {
  const { id } = useButtonClickStore();
  const { fetchReleaseDetail } = useReleaseDetailStore();
  const { fetchRelease } = useReleaseStore();
  useEffect(() => {
    fetchReleaseDetail(id)
      .then((res) => {})
      .catch((error) => {})
      .finally(() => {});
  }, []);

  const [memo, setMemo] = useState("");
  const cardClick = () => {
    releaseResponse(id, false, memo, [])
      .then((res) => {})
      .catch((error) => {})
      .finally(() => {
        fetchRelease("PUBLISH", 1, 10)
          .then((res) => {})
          .catch((error) => {})
          .finally(() => {
            setMemo("");
            onClose();
          });
      });
  };
  useEffect(() => {}, [memo]);

  return (
    <Modal className={styles.container} isOpen={true} onClose={onClose}>
      <ModalContent>
        <ModalHeader className={styles.title}>불출 반려</ModalHeader>
        <ModalBody>
          <Divider className={styles.divider} />
          <Textarea
            label="Description"
            placeholder="반려 사유 입력"
            className={styles.content}
            size="lg"
            minRows={12}
            onValueChange={(value: string) => setMemo(value)}
          />
        </ModalBody>
        <ModalFooter>
          <button className={styles.btn} onClick={cardClick}>
            확인
          </button>
        </ModalFooter>
      </ModalContent>
    </Modal>
  );
}
