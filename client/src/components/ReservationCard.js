import React from 'react';
import classNames from 'classnames';
import { ProgressArrow200 } from './Arrow';

const ReservationCard = ({ reservationInfo }) => {
  return (
    <div className={classNames('content')}>
      { reservationInfo.map((reservation, index) => (
        <div className={classNames('content-card')}>
        <div className={classNames('id-area')}>
          <div className={classNames('id-text')}>
            {reservation.reservationId}
          </div>
        </div>
        <div className={classNames('image-area')}>
          <div className={classNames('profile-image')}/>
        </div>
        <div className={classNames('info-area')}>
          <div className={classNames('date-info')}>
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
          <div className={classNames('car-info')}>
            {reservation.carSellName}
          </div>
          <div className={classNames('shop-info')}>
            {reservation.repairShop}
          </div>
        </div>
        <ProgressArrow200/>
      </div>
    ))}
    </div>
  );
};

export default ReservationCard;
