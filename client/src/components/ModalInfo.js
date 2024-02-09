import classNames from 'classnames';
import { useEffect, useState } from 'react';

export default function ModalInfo({ prevStep, nextStep, props }) {
  const [phone1, setPhone1] = useState(props.phone1);
  const [phone2, setPhone2] = useState(props.phone2);
  const [phone3, setPhone3] = useState(props.phone3);
  const [sellName, setSellName] = useState(props.sellName);
  const [plateNumber, setPlateNumber] = useState(props.plateNumber);

  function handleNext() {
    if (phone1 === '' || phone2 === '' || phone3 === '' || sellName === '' || plateNumber === '') return;
    nextStep(phone1, phone2, phone3, sellName, plateNumber);
  }

  function handlePrev() {
    prevStep(phone1, phone2, phone3, sellName, plateNumber);
  }

  function handlePhoneChange(setValue, event) {
    const inputValue = event.target.value;
    if (!isNaN(inputValue)) {
      setValue(inputValue);
    }
  }

  function next(val, len, nextId) {
    if (val.length === len) {
      document.getElementById(nextId).focus();
    }
  }

  useEffect(() => {
    const phone1 = document.getElementById('phone1');
    const phone2 = document.getElementById('phone2');

    phone1.addEventListener('keyup', e => {
      next(e.target.value, 3, 'phone2');
    });

    phone2.addEventListener('keyup', e => {
      next(e.target.value, 4, 'phone3');
    });
  }, []);

  return (
    <div className={classNames('modal_body')}>
      <div className={classNames('frame_left')}>
        <div>내 차 정보 자동입력</div>
        <div className={classNames('car_list')} />
      </div>
      <div className={classNames('frame_right')}>
        <div className={classNames('category_row')}>
          <span className={classNames('row_title')}>3. 연락처</span>
          <span className={classNames('row_content')}>
            <input
              type="text"
              id="phone1"
              maxLength="3"
              value={phone1}
              onChange={e => handlePhoneChange(setPhone1, e)}
            />
            <span className={classNames('space')}> - </span>
            <input
              type="text"
              id="phone2"
              maxLength="4"
              value={phone2}
              onChange={e => handlePhoneChange(setPhone2, e)}
            />
            <span className={classNames('space')}> - </span>
            <input
              type="text"
              id="phone3"
              maxLength="4"
              value={phone3}
              onChange={e => handlePhoneChange(setPhone3, e)}
            />
          </span>
        </div>
        <div className={classNames('category_row')}>
          <span className={classNames('row_title')}>4. 차량 정보</span>
          <span className={classNames('row_content')}>
            <select className={classNames('car-select')} value={sellName} onChange={e => setSellName(e.target.value)}>
              <option value="">차종을 선택해주세요</option>
              <option value="g90lwb">G90 Long Wheel Base</option>
              <option value="g90">G90</option>
              <option value="g80">G80</option>
              <option value="eg80">Electrified G80</option>
              <option value="g70">G70</option>
              <option value="g70sb">G70 Shooting Brake</option>
              <option value="gv80">GV80</option>
              <option value="gv80c">GV80 Coupe</option>
              <option value="gv70">GV70</option>
              <option value="egv70">Electrified GV70</option>
              <option value="gv60">GV60</option>
            </select>
            <input
              type="text"
              value={plateNumber}
              onChange={e => {
                setPlateNumber(e.target.value);
              }}
            />
          </span>
        </div>
        <div className={classNames('button')}>
          <span onClick={handlePrev}>이전</span>
          <span onClick={handleNext}>다음</span>
        </div>
      </div>
    </div>
  );
}
