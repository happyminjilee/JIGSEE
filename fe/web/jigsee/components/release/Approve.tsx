import { Modal, ModalContent, ModalHeader, ModalBody, ModalFooter } from "@nextui-org/react";
import { Divider, Textarea } from "@nextui-org/react";
import styles from "@/styles/modal/releaseapprove.module.scss";
import {useButtonClickStore, useReleaseDetailStore} from "@/store/releasestore";
import {releaseResponse} from "@/pages/api/releaseAxios"
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
        .finally(() => {
          console.log(id)
          console.log(serialNos)
        })
  }, []);
  const cardClick = () => {
    releaseResponse(id, true, "", serialNos)
        .then((res)=> {
          console.log(res)
        })
        .catch((error) => {
          console.log(error.message)
        })
        .finally(() => {
          onClose()
        })
  }
  return (
    <Modal className={styles.container} isOpen={true} onClose={onClose}>
      <ModalContent>
        <ModalHeader className={styles.title}>불출 승인</ModalHeader>
        <ModalBody>
          <Divider className={styles.divider} />
          <div className={styles.content}>
            {serialNos.map((info, index) => (
                <div className={styles.card}>
                  {info}
                </div>
            ))}
          </div>

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
