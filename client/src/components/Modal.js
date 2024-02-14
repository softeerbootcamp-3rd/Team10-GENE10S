import classNames from 'classnames';
import { useState } from 'react';

export default function Modal({ onClose }) {
  const [carType, setCarType] = useState('');
  const [carNumber, setCarNumber] = useState('');

  const handleCarTypeChange = (event) => {
    setCarType(event.target.value);
  };

  const handleCarNumberChange = (event) => {
    setCarNumber(event.target.value);
  };

  const handleRegisterClick = () => {
    const requestBody = {
      carType: carType,
      carNumber: carNumber
    };

    //아직 API가 없음

    onClose();
  };

  return (
    <div className={classNames('modal-frame')}>
      <div className={classNames('modal')}>
        <div className={classNames('modal-header')}>
          <span>차량 등록하기</span>
        </div>
        <div className={classNames('body')}>
          <div className={classNames('info-row')}>
            <span className={classNames('key')}>차종</span>
            <input className={classNames('input')} value={carType} onChange={handleCarTypeChange} />
          </div>
          <div className={classNames('info-row')}>
            <span className={classNames('key')}>차량 번호</span>
            <input className={classNames('input')} value={carNumber} onChange={handleCarNumberChange} />
          </div>
        </div>
        <div className={classNames('bottom')}>
          <div className={classNames('btn-cancel')} onClick={onClose}>
            <span>취소하기</span>
          </div>
          <div className={classNames('btn-submit')} onClick={handleRegisterClick}>
            <span>등록하기</span>
          </div>
        </div>
      </div>
    </div>
  );
}
