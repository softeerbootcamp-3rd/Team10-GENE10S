import classNames from 'classnames';
import Footer from '../components/Footer';
import Header from '../components/Header';
import { useEffect } from 'react';

import ReservationDetailInfo from '../components/reservation_detail/ReservationDetailInfo';
import ReservationDetailService from '../components/reservation_detail/ReservationDetailService';
import ReservationDetailStep from '../components/reservation_detail/ReservationDetailStep';
import ReservationDetailResult from '../components/reservation_detail/ReservationDetailResult';

export default function ReservationDetail() {

  useEffect(() => {
    window.scrollTo(0, 0);
  }, []);
  
  return (
    <>
      <Header />

      <div className={classNames('reservation-detail-page')}>
        <div className={classNames('title')}>
          <div className={classNames('text')}>
            마이페이지 {'>'} 예약 내역
          </div>
          <div className={classNames('reservation-info')}>
            <div className={classNames('text-1')}>
              예약 번호
            </div>
            <div className={classNames('text-2')}>
              GEN-20240212-00004364
            </div>
          </div>
        </div>

        {/* body */}
        <div className={classNames('body')}>

          {/* info */}
          <ReservationDetailInfo />

          {/* service */}
          <ReservationDetailService />

          {/* step */}
          <ReservationDetailStep />

          {/* result */}
          <ReservationDetailResult />

        </div>

      </div>
      
      <Footer />
    </>
  );
}
