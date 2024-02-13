import React from 'react';
import classNames from 'classnames';
import { ProgressArrow200 } from './Arrow';
import { Link } from 'react-router-dom';

const ReservationCard = ({ reservationInfo }) => {
  return (
    <div className={classNames('content')}>
      { reservationInfo.map((reservation, index) => (
        <Link className={classNames('content-card')}
          to='/'
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
      </Link>
    ))}
    </div>
  );
};

export default ReservationCard;
