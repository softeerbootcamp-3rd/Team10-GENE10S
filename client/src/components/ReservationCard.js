import React, { useEffect, useState } from 'react';
import classNames from 'classnames';
import { ProgressArrow200 } from './Arrow';
import { Link } from 'react-router-dom';

const ReservationCard = ({ reservationInfo }) => {
  const [animationQueue, setAnimationQueue] = useState([]);
  const [subAnimate, setSubAnimate] = useState({ dateInfo: false, carInfo: false, shopInfo: false });

  useEffect(() => {
    // 카드 애니메이션을 순차적으로 실행하기 위한 큐 생성
    const queue = [];
    reservationInfo.forEach((_, index) => {
      queue.push(setTimeout(() => {
        setAnimationQueue(prevQueue => [...prevQueue, index]);
      }, index * 200)); // 각 카드의 애니메이션 간격을 설정할 수 있습니다 (여기서는 200밀리초)
    });

    // 컴포넌트 언마운트 시 setTimeout 제거
    return () => {
      queue.forEach(timeout => clearTimeout(timeout));
    };
  }, [reservationInfo]);

  useEffect(() => {
    // 각 요소의 애니메이션을 순차적으로 실행하기 위해 setTimeout 사용
    if (animationQueue.length > 0) {
      setTimeout(() => setSubAnimate(prevState => ({ ...prevState, dateInfo: true })), 0);
      setTimeout(() => setSubAnimate(prevState => ({ ...prevState, carInfo: true })), 200);
      setTimeout(() => setSubAnimate(prevState => ({ ...prevState, shopInfo: true })), 400);
    } else {
      // 카드가 없을 경우 애니메이션 상태 초기화
      setSubAnimate({ dateInfo: false, carInfo: false, shopInfo: false });
    }
  }, [animationQueue])

  return (
    <div className={classNames('content')}>
      { reservationInfo.map((reservation, index) => (
        <Link
          key={reservation.reservationId}
          className={classNames('content-card', { 'animate': animationQueue.includes(index) })}
          to='/reservation/detail'
        >
        <div className={classNames('id-area')}>
          <div className={classNames('id-text')}>
            {reservation.reservationId}
          </div>
        </div>
        <div className={classNames('image-area')}>
          <img src={reservation.imageUrl} className={classNames('profile-image')}/>
        </div>
        <div className={classNames('info-area')}>
          <div className={classNames('date-info', { 'animate': subAnimate.dateInfo})}>
            <div className={classNames('text')}>
              {reservation.departureTime} ~ {reservation.arrivalTime}
            </div>
            <div className={classNames('separator')}>
              <svg xmlns="http://www.w3.org/2000/svg">
                <circle cx="2" cy="2" r="2" fill="#B6B0A8"/>
              </svg>
            </div>
            <div className={classNames('text')}>
              {reservation.progressStage}
            </div>
          </div>
          <div className={classNames('car-info', { 'animate': subAnimate.carInfo})}>
            {reservation.carSellName}
          </div>
          <div className={classNames('shop-info', { 'animate': subAnimate.shopInfo})}>
            {reservation.repairShop}
          </div>
        </div>
        <ProgressArrow200/>
      </Link>
    ))}
    </div>
  );
};

export default ReservationCard;
