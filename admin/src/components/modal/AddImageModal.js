import classNames from 'classnames';
import BtnDark, { BtnLight } from '../Button';
import { useEffect, useRef, useState } from 'react';
import { postMaintenanceImage } from '../../api/ReservationApi';

export default function AddImageModal({ reservationId, status = 0, onPost, onClose, visible }) {

    const [selectedStatus, setSelectedStatus] = useState(status);
    const [selectedFile, setSelectedFile] = useState(null);
    const ref = useRef();

    useEffect(() => {
        setSelectedStatus(status);
        ref.current.value = '';
    }, [status])

    function onStatusChange(event) {
        setSelectedStatus(event.target.value);
    }

    function onFileChange(event) {
        setSelectedFile(event.target.files[0]);
    }

    function submitImage(reservationId) {
        postMaintenanceImage(reservationId, selectedStatus, selectedFile).then((response) => {
            onPost(response.imageId, selectedStatus, response.imageUrl);
        });
        onClose();
    }

    return (
        <div className={classNames('modal-frame', {'visible': visible})}>
            <div className={classNames('modal')}>
                <div className={classNames('title')}>
                    <span>정비 사진 추가</span>
                </div>
                <div className={classNames('content')}>
                    <div className={classNames('info-table')}>
                        <div className={classNames('tbody')}>
                            <div className={classNames('tr')}>
                                <div className={classNames('td', 'header')}>
                                    <span>구분</span>
                                </div>
                                <div className={classNames('td')}>
                                    <select value={selectedStatus} onChange={onStatusChange}>
                                        <option value='0'>정비 전</option>
                                        <option value='1'>정비 후</option>
                                    </select>
                                </div>
                            </div>
                            <div className={classNames('tr')}>
                                <div className={classNames('td', 'header')}>
                                    <span>파일 첨부</span>
                                </div>
                                <div className={classNames('td')}>
                                    <input type='file' onChange={onFileChange} ref={ref}></input>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className={classNames('btn-bottom')}>
                        <BtnDark text={'등록'} onClick={() => submitImage(reservationId)}/>
                        <BtnLight text={'취소'} onClick={onClose}/>
                    </div>
                </div>
            </div>
        </div>
    );
}
