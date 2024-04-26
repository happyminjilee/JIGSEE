import { Modal, ModalContent, ModalHeader, ModalBody, ModalFooter } from "@nextui-org/react";
import { Divider, Textarea } from "@nextui-org/react";

// Props에 대한 인터페이스 정의
interface ReturnProps {
  onClose: () => void; // 이 함수는 파라미터를 받지 않고 void를 반환함
}
export default function Return({ onClose }: ReturnProps) {
  return (
    <Modal isOpen={true} onClose={onClose}>
      <ModalContent>
        <ModalHeader className="flex flex-col gap-1">
          불출 반려
          <Divider />
          승인 요청 제목 날짜 영역
        </ModalHeader>
        <ModalBody>
          <Textarea
            isReadOnly
            variant="flat"
            placeholder="승인 내역"
            defaultValue="모델명 수량 NextUI is a React UI library that provides a set of accessible, reusable, and beautiful components."
            className="max-w-xs"
          />
        </ModalBody>
        <ModalFooter>
          <button onClick={onClose}>확인</button>
        </ModalFooter>
      </ModalContent>
    </Modal>
  );
}
