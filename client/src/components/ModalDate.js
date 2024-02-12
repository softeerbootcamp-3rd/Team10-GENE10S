import classNames from 'classnames';
import { BtnBlack } from './Button';
import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import Calendar from './Calendar';

export default function ModalDate({ nextStep, props }) {
  const [departureDate, setDepartureDate] = useState(props.departureDate);
  const [departureTime, setDepartureTime] = useState(props.departureTime);
  const [pickupDate, setPickupDate] = useState(props.pickupDate);
  const [pickupTime, setPickupTime] = useState(props.pickupTime);

  // 더미 데이터
  useEffect(() => {
    setDepartureDate('2024년 3월 1일');
    setDepartureTime('8 : 00');
    setPickupDate('2024년 3월 11일');
    setPickupTime('9 : 00');
  }, []);

  function handleNext() {
    if (departureDate === '' || departureTime === '' || pickupDate === '' || pickupTime === '') return;
    nextStep(departureDate, departureTime, pickupDate, pickupTime);
  }

  return (
    <>
      <div className={classNames('body')}>
        <div className={classNames('frame-left')}>
          <Calendar year={2024} month={3} />
        </div>
        <div className={classNames('frame-right')}>
          <div className={classNames('category-row')}>
            <span className={classNames('title')}>1. 센터 방문</span>
            <span className={classNames('content')}>
              <div className={classNames('input-area', 'w-200')}>
                <span className={classNames('input-text')}>{departureDate}</span>
              </div>
              <div className={classNames('input-area', 'w-160')}>
                <span className={classNames('input-text')}>{departureTime}</span>
              </div>
            </span>
          </div>
          <div className={classNames('category-row')}>
            <span className={classNames('title')}>2. 픽업 시간</span>
            <span className={classNames('content')}>
              <div className={classNames('input-area', 'w-200')}>
                <span className={classNames('input-text')}>{pickupDate}</span>
              </div>
              <div className={classNames('input-area', 'w-160')}>
                <span className={classNames('input-text')}>{pickupTime}</span>
              </div>
            </span>
          </div>
        </div>
      </div>
      <div className={classNames('btn-group')}>
        <Link to="/" replace={true}>
          <BtnBlack text="이전" />
        </Link>
        <BtnBlack text="다음" onClick={handleNext} />
      </div>
    </>
  );
}
