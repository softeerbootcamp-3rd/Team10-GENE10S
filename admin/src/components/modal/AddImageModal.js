import classNames from 'classnames';
import BtnDark, { BtnLight } from '../Button';
import { useEffect, useState } from 'react';

export default function AddImageModal({ status = 0, onClose, visible }) {

    const [selectedStatus, setSelectedStatus] = useState(status);

    useEffect(() => {
        setSelectedStatus(status);
    }, [status])

    function handleChangeStatus(event) {
        setSelectedStatus(event.target.value);
    }

    function submitImage() {
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
                                    <select value={selectedStatus} onChange={handleChangeStatus}>
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
                                    <input></input>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className={classNames('btn-bottom')}>
                        <BtnDark text={'등록'} onClick={submitImage}/>
                        <BtnLight text={'취소'} onClick={onClose}/>
                    </div>
                </div>
            </div>
        </div>
    );
}
