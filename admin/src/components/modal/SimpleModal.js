import BtnDark from "../button/BtnDark";
import ModalFrame from "./ModalFrame";
import BtnGroup from "../button/BtnGroup";

export default function SimpleModal({ text, onClose, visible }) {
  return (
    <ModalFrame visible={visible} title={`알림`}>
      <div className='message'>
        <span>{text}</span>
      </div>
      <BtnGroup>
        <BtnDark onClick={onClose}>닫기</BtnDark>
      </BtnGroup>
    </ModalFrame>
  );
}
