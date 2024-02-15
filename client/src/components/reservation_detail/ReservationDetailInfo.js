import classNames from 'classnames';

function parseAndFormatDate(dateString) {
  const date = new Date(dateString);
  
  const year = date.getFullYear();
  const month = date.getMonth() + 1;
  const day = date.getDate();
  const hours = date.getHours();
  const minutes = date.getMinutes();

  const formattedDate = `${year}년 ${month}월 ${day}일 (${getDayOfWeek(date)})`;
  const formattedTime = `${hours < 10 ? '오전' : '오후'} ${hours % 12 || 12}:${minutes < 10 ? '0' : ''}${minutes}`;

  return { formattedDate, formattedTime };
}

function getDayOfWeek(date) {
  const daysOfWeek = ['일', '월', '화', '수', '목', '금', '토'];
  return daysOfWeek[date.getDay()];
}


export default function ReservationDetailInfo({ reservationDetail }) {
  const { formattedDate: formattedDepartureDate, formattedTime: formattedDepartureTime } = parseAndFormatDate(reservationDetail.from);
  const { formattedDate: formattedArrivalDate, formattedTime: formattedArrivalTime } = parseAndFormatDate(reservationDetail.to);
  
  return (
    <>
      <div className={classNames('info')}>

        {/* reservation-date */}
        <div className={classNames('reservation-date')}>

          {/* category */}
          <div className={classNames('category')}>
            <div className={classNames('text')}>
              예약 날짜
            </div>
          </div>

          {/* time-info */}
          <div className={classNames('time-info')}>
            <div className={classNames('departure')}>
              <div className={classNames('text')}>
                센터 방문
              </div>
              <div className={classNames('time-text')}>
                <div className={classNames('text')}>
                  {formattedDepartureDate}
                </div>
                <div className={classNames('text')}>
                  {formattedDepartureTime}
                </div>
              </div>
            </div>
            <div className={classNames('arrival')}>
              <div className={classNames('text')}>
                픽업 시간
              </div>
              <div className={classNames('time-text')}>
                <div className={classNames('text')}>
                  {formattedArrivalDate}
                </div>
                <div className={classNames('text')}>
                  {formattedArrivalTime}
                </div>
              </div>
            </div>
          </div>

          {/* shop-info */}
          <div className={classNames('shop-info')}>
            <div className={classNames('text')}>
              <div className={classNames('text')}>
                {reservationDetail.repairShop}
              </div>
              <div className={classNames('detail-info')}>
                <div className={classNames('text-address')}>
                  {reservationDetail.repairShopAddress}
                </div>
                <div className={classNames('shop-text')}>
                  <svg className={classNames('phone')} xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 18 18" fill="none">
                    <path d="M13.8638 11.3398L10.5367 11.9894C8.28929 10.8528 6.90104 9.54728 6.09313 7.51221L6.71518 4.1502L5.53932 1H2.50889C1.59792 1 0.880565 1.75849 1.01662 2.66605C1.35627 4.93175 2.35775 9.03973 5.28521 11.9894C8.3595 15.0869 12.7873 16.431 15.2242 16.9653C16.1653 17.1716 17 16.4319 17 15.4616V12.5439L13.8638 11.3398Z" stroke="#DDD8D2" strokeWidth="1.2" strokeLinecap="round" strokeLinejoin="round"/>
                  </svg>
                  <div className={classNames('text-phone')}>
                    {reservationDetail.managerPhoneNumber}
                  </div>
                </div>
              </div>
            </div>
            <div className={classNames('map')}>
              <img className={classNames('basemap-image')} />
            </div>
          </div>
        </div>

        {/* reservation-info */}
        <div className={classNames('reservation-info')}>

          {/* category */}
          <div className={classNames('category')}>
            <div className={classNames('text')}>
              예약 정보
            </div>
          </div>

          {/* car-info */}
          <div className={classNames('car-info')}>
            <div className={classNames('subtitle')}>
              <div className={classNames('text')}>
                차량 정보
              </div>
            </div>
            <div className={classNames('detail')}>
              <div className={classNames('text')}>
                <div className={classNames('text-1')}>
                  {reservationDetail.carSellName}
                </div>
                <div className={classNames('text-2')}>
                  {reservationDetail.carPlateNumber}
                </div>
              </div>
              <img className={classNames('image-7')}/>
            </div>
          </div>

          {/* coupon-info */}
          <div className={classNames('coupon-info')}>
            <div className={classNames('subtitle')}>
              <div className={classNames('text')}>
                사용 쿠폰
              </div>
            </div>
            <div className={classNames('detail')}>
              <div className={classNames('text-1')}>
                제네시스 홈투홈 쿠폰
              </div>
              <div className={classNames('text-2')}>
                {reservationDetail.couponSerialNumber}
              </div>
            </div>
          </div>

          {/* request-info */}
          <div className={classNames('request-info')}>
            <div className={classNames('subtitle')}>
              <div className={classNames('text')}>
                요청 사항
              </div>
            </div>
            <div className={classNames('detail')}>
              <div className={classNames('text')}>
                {reservationDetail.customerRequest}
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
