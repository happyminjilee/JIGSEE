import { Modal, ModalContent, ModalHeader, ModalBody, ModalFooter } from "@nextui-org/react";
import { Divider, Textarea } from "@nextui-org/react";
import styles from "@/styles/modal/release.module.scss";

// Props에 대한 인터페이스 정의
interface ApproveProps {
  onClose: () => void; // 이 함수는 파라미터를 받지 않고 void를 반환함
}

export default function Approve({ onClose }: ApproveProps) {
  return (
    <Modal className={styles.container} isOpen={true} onClose={onClose}>
      <ModalContent>
        <ModalHeader className={styles.title}>불출 승인</ModalHeader>
        <ModalBody>
          <Divider className={styles.divider} />
          승인 요청 제목 날짜 영역
          <Textarea
            isReadOnly
            variant="flat"
            placeholder="승인 내역"
            defaultValue="모델명 수량 NextUI is a React UI library that provides a set of accessible, reusable, and beautiful components."
            className={styles.content}
          />
        </ModalBody>
        <ModalFooter>
          <button className={styles.btn} onClick={onClose}>
            확인
          </button>
        </ModalFooter>
      </ModalContent>
    </Modal>
  );
}
