import classNames from 'classnames';
import { useState } from 'react';
import { registerCar } from '../api/CarApi';

export default function Modal({ onClose, visible }) {
  const [sellName, setSellName] = useState('');
  const [plateNumber, setPlateNumber] = useState('');

  const handleSellNameChange = event => {
    setSellName(event.target.value);
  };

  const handlePlateNumberChange = event => {
    setPlateNumber(event.target.value);
  };

  const closeModal = () => {
    setSellName('');
    setPlateNumber('');
    onClose();
  }

  const handleRegisterClick = () => {
    const requestBody = {
      sellName: sellName,
      plateNumber: plateNumber,
    };

    registerCar(requestBody)
      .then(result => {
        alert('차량이 성공적으로 등록되었습니다.');
        window.location.reload();
      })
      .catch(error => {
        console.error('Error registering car:', error);
      });
    closeModal();
  };

  return (
    <div className={classNames('modal-frame', {'visible': visible})}>
      <div className={classNames('modal')}>
        <div className={classNames('modal-header')}>
          <span>차량 등록하기</span>
        </div>
        <div className={classNames('body')}>
          <div className={classNames('info-row')}>
            <span className={classNames('key')}>차종</span>
            <input className={classNames('input')} value={sellName} onChange={handleSellNameChange} />
          </div>
          <div className={classNames('info-row')}>
            <span className={classNames('key')}>차량 번호</span>
            <input className={classNames('input')} value={plateNumber} onChange={handlePlateNumberChange} />
          </div>
        </div>
        <div className={classNames('bottom')}>
          <div className={classNames('btn-cancel')} onClick={closeModal}>
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
