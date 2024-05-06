import { Modal, ModalContent, ModalHeader, ModalBody, ModalFooter } from "@nextui-org/react";
import { Divider, Textarea } from "@nextui-org/react";
import styles from "@/styles/modal/release.module.scss";
import {useButtonClickStore, useReleaseDetailStore} from "@/store/releasestore";
import {useEffect} from "react";
// Props에 대한 인터페이스 정의
interface ReturnProps {
  onClose: () => void; // 이 함수는 파라미터를 받지 않고 void를 반환함
}
export default function Return({ onClose }: ReturnProps) {
  const {id} = useButtonClickStore()
  const {fetchReleaseDetail,} = useReleaseDetailStore()
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
        <ModalHeader className={styles.title}>불출 반려</ModalHeader>
        <ModalBody>
          <Divider className={styles.divider} />
          반려 요청 제목 날짜 영역
          <Textarea
            isReadOnly
            variant="flat"
            placeholder="승인 내역"
            defaultValue="모델명 수량 NextUI is a React UI library that provides a set of accessible, reusable, and beautiful components."
            className={styles.content}
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
