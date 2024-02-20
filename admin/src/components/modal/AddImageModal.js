import classNames from 'classnames';
import BtnDark, { BtnLight } from '../Button';

export default function AddImageModal({ onClose, visible }) {

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
                                    <input></input>
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
