import BtnDark from "../button/BtnDark";
import BtnLight from "../button/BtnLight";
import { useEffect, useRef, useState } from "react";
import { postMaintenanceImage } from "../../api/ReservationApi";
import ModalFrame from "./ModalFrame";
import BtnGroup from "../button/BtnGroup";
import InfoTable from "../infotable/InfoTable";
import InfoItem from "../infotable/InfoItem";
import InfoRow from "../infotable/InfoRow";

export default function AddImageModal({
  reservationId,
  status = 0,
  onPost,
  onClose,
  visible,
}) {
  const [selectedStatus, setSelectedStatus] = useState(status);
  const [selectedFile, setSelectedFile] = useState(null);
  const ref = useRef();

  useEffect(() => {
    setSelectedStatus(status);
    ref.current.value = "";
  }, [status]);

  function onStatusChange(event) {
    setSelectedStatus(event.target.value);
  }

  function onFileChange(event) {
    setSelectedFile(event.target.files[0]);
  }

  function submitImage(reservationId) {
    postMaintenanceImage(reservationId, selectedStatus, selectedFile).then(
      (response) => {
        onPost(response.imageId, selectedStatus, response.imageUrl);
      }
    );
    onClose();
  }

  return (
    <ModalFrame visible={visible} title={"정비 사진 추가"}>
      <InfoTable>
        <InfoRow>
          <InfoItem label={"구분"}>
            <select value={selectedStatus} onChange={onStatusChange}>
              <option value='0'>정비 전</option>
              <option value='1'>정비 후</option>
            </select>
          </InfoItem>
        </InfoRow>
        <InfoRow>
          <InfoItem label={"파일 첨부"}>
            <input type='file' onChange={onFileChange} ref={ref}></input>
          </InfoItem>
        </InfoRow>
      </InfoTable>
      <BtnGroup>
        <BtnDark onClick={() => submitImage(reservationId)}>등록</BtnDark>
        <BtnLight onClick={onClose}>취소</BtnLight>
      </BtnGroup>
    </ModalFrame>
  );
}
