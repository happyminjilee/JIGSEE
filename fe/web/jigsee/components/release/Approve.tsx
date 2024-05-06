import { Modal, ModalContent, ModalHeader, ModalBody, ModalFooter } from "@nextui-org/react";
import { Divider, Textarea } from "@nextui-org/react";
import styles from "@/styles/modal/release.module.scss";
import {useButtonClickStore, useReleaseDetailStore} from "@/store/releasestore";
import {useEffect} from "react";

// Props에 대한 인터페이스 정의
interface ApproveProps {
  onClose: () => void; // 이 함수는 파라미터를 받지 않고 void를 반환함
}


export default function Approve({ onClose }: ApproveProps) {
  const {id} = useButtonClickStore()
  const {fetchReleaseDetail, serialNos} = useReleaseDetailStore()
  useEffect(() => {
    fetchReleaseDetail(id)
        .then((res) => {
          console.log(res)
        })
        .catch((error) => {
          console.log(error.message)
        })
  }, []);
  const cardClick = () => {
    onClose()
  }
  return (
    <Modal className={styles.container} isOpen={true} onClose={onClose}>
      <ModalContent>
        <ModalHeader className={styles.title}>불출 승인</ModalHeader>
        <ModalBody>
          <Divider className={styles.divider} />
          승인 요청 제목 날짜 영역

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
