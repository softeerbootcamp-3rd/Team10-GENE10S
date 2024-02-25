import { useEffect, useState } from "react";
import BtnDark from "../button/BtnDark";
import BtnLight from "../button/BtnLight";
import ModalFrame from "./ModalFrame";
import BtnGroup from "../button/BtnGroup";

export default function AvailableTimeModal({
  status,
  date,
  time,
  hasReserv,
  onConfirm,
  onCancel,
  visible,
}) {
  const [year, setYear] = useState("");
  const [month, setMonth] = useState("");
  const [day, setDay] = useState("");
  const [state, setState] = useState("");
  const [message, setMessage] = useState("");

  useEffect(() => {
    if (date === undefined || date === "") return;
    const split = date.split("-");
    setYear(split[0]);
    if (split[1].indexOf(0) === "0") {
      setMonth(split[1].indexOf(1));
    } else {
      setMonth(split[1]);
    }
    setDay(split[2]);
  }, [date]);

  useEffect(() => {
    setMessage("");
  }, [visible]);

  useEffect(() => {
    setState(status);
  }, [status]);

  async function handleConfirm() {
    if (status === "add") {
      onConfirm(date, time);
    } else {
      if (hasReserv) {
        await onConfirm(date, time, message);
      } else {
        await onConfirm(date, time);
      }
    }
    setMessage("");
  }

  function handleCancel() {
    setMessage("");
    onCancel();
  }

  return (
    <ModalFrame
      visible={visible}
      title={`예약 가능 시간 ${state === "add" ? "추가" : "삭제"}`}
    >
      <div className='message'>
        <span>{`${year}년 ${month}월 ${day}일 ${time} 예약 가능 시간을 ${
          state === "add" ? "추가" : "삭제"
        }하시겠습니까?`}</span>
        {hasReserv && (
          <span>
            이미 예약 이력이 있는 시간입니다. 고객에게 안내될 삭제 사유를
            입력해주세요.
          </span>
        )}
      </div>
      {hasReserv && <textarea type='text' className='input-text' rows='10' onChange={e => setMessage(e.target.value)} value={message} />}
      <BtnGroup>
        <BtnDark onClick={handleConfirm}>완료</BtnDark>
        <BtnLight onClick={handleCancel}>취소</BtnLight>
      </BtnGroup>
    </ModalFrame>
  );
}
