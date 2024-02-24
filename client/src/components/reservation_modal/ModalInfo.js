import classNames from 'classnames';
import { useEffect, useState } from 'react';
import { BtnBlack } from '../Button';
import { getCarList } from '../../api/ReservationApi';
import { getUserInfo } from '../../api/UserApi';
import SellNameSelector from '../SellNameSelector';

export default function ModalInfo({ prevStep, nextStep, props }) {
  const [phone1, setPhone1] = useState(props.phone1);
  const [phone2, setPhone2] = useState(props.phone2);
  const [phone3, setPhone3] = useState(props.phone3);
  const [sellName, setSellName] = useState(props.sellName);
  const [plateNumber, setPlateNumber] = useState(props.plateNumber);
  const [carListData, setCarListData] = useState([]);

  function handleNext() {
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

  function handleInputCar(event) {
    const idx = event.currentTarget.id;
    const carInfo = carListData[idx];
    setSellName(carInfo.sellName);
    setPlateNumber(carInfo.plateNumber);
  }

  function focusNext(val, len, nextId) {
    if (val.length === len) {
      document.getElementById(nextId).focus();
    }
  }

  useEffect(() => {
    const phone1 = document.getElementById('phone1');
    const phone2 = document.getElementById('phone2');

    phone1.addEventListener('keyup', e => {
      focusNext(e.target.value, 3, 'phone2');
    });

    phone2.addEventListener('keyup', e => {
      focusNext(e.target.value, 4, 'phone3');
    });

    getCarList().then(result => {
      setCarListData(result);
    });

    getUserInfo().then(response => {
      const phone = response.phoneNumber;
      setPhone1(`0${phone.substring(3, 5)}`);
      setPhone2(phone.substring(5, 9));
      setPhone3(phone.substring(9, 13));
    });
  }, []);

  function GenerateCarButton() {
    if (!carListData) return;
    const carButton = carListData.map((carData, index) => (
      <div
        id={index}
        className={classNames('car-info')}
        onClick={e => {
          handleInputCar(e);
        }}
        key={index}
      >
        <div className={classNames('image-area')}>
          <img className={classNames('image')} src={carData.imageUrl} alt="" />
        </div>
        <div className={classNames('content-area')}>
          <span className={classNames('car-name')}>{carData.sellName}</span>
          <span className={classNames('plate-number')}>{carData.plateNumber}</span>
        </div>
      </div>
    ));
    return carButton;
  }

  return (
    <>
      <div className={classNames('body')}>
        <div className={classNames('frame-left')}>
          <div className={classNames('frame-type')}>내 차 정보 자동입력</div>
          <div id="carList" className={classNames('car-list')}>
            <GenerateCarButton />
          </div>
        </div>
        <div className={classNames('frame-right')}>
          <div className={classNames('category-row')}>
            <span className={classNames('title')}>3. 연락처</span>
            <div className={classNames('content')}>
              <input
                type="text"
                id="phone1"
                maxLength="3"
                value={phone1}
                className={classNames('input-area', 'w-140', 'input-text')}
                onChange={e => handlePhoneChange(setPhone1, e)}
              />
              <input
                type="text"
                id="phone2"
                maxLength="4"
                value={phone2}
                className={classNames('input-area', 'w-180', 'input-text')}
                onChange={e => handlePhoneChange(setPhone2, e)}
              />
              <input
                type="text"
                id="phone3"
                maxLength="4"
                value={phone3}
                className={classNames('input-area', 'w-180', 'input-text')}
                onChange={e => handlePhoneChange(setPhone3, e)}
              />
            </div>
          </div>
          <div className={classNames('category-row')}>
            <span className={classNames('title')}>4. 차량 정보</span>
            <div className={classNames('content')}>
              <span className={classNames('input-hint')}>차종</span>
              <SellNameSelector
                value={sellName}
                onchange={e => setSellName(e.target.value)}
                className={classNames('input-area', 'w-200', 'input-text')}
              />
            </div>
            <div className={classNames('content')}>
              <span className={classNames('input-hint')}>차량번호</span>
              <input
                type="text"
                value={plateNumber}
                className={classNames('input-area', 'w-200', 'input-text')}
                onChange={e => {
                  setPlateNumber(e.target.value);
                }}
              />
            </div>
          </div>
        </div>
      </div>
      <div className={classNames('btn-group')}>
        <BtnBlack text="이전" onClick={handlePrev} />
        <BtnBlack text="다음" onClick={handleNext} />
      </div>
    </>
  );
}
