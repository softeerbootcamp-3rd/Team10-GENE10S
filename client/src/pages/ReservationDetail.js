import classNames from 'classnames';
import Footer from '../components/Footer';
import Header from '../components/Header';
import axios from 'axios';
import { useEffect, useState } from 'react';

import ReservationDetailInfo from '../components/reservation_detail/ReservationDetailInfo';
import ReservationDetailService from '../components/reservation_detail/ReservationDetailService';
import ReservationDetailStep from '../components/reservation_detail/ReservationDetailStep';
import ReservationDetailResult from '../components/reservation_detail/ReservationDetailResult';
import { useLocation } from 'react-router-dom';

export default function ReservationDetail() {
  const location = useLocation();
  const [reservationId, setReservationId] = useState(null);
  const [reservationDetail, setReservationDetail] = useState({});

  useEffect(() => {
    window.scrollTo(0, 0);
  }, []);

  useEffect(() => {
    console.log('location: ', location);
    if (location.state && location.state.reservationId) {
      setReservationId(location.state.reservationId);
    }
  }, [location]);

  useEffect(() => {
    if (reservationId) {
      axios.get(`/v1/reservation/${reservationId}/detail`)
      .then((response) => {
        console.log("response: ", response.data.data  );
        setReservationDetail(response.data.data);
      })
      .catch((error) => (console.log(error)));
    }
  }, [reservationId]);
  
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
          <ReservationDetailInfo reservationDetail={reservationDetail} />

          {/* service */}
          <ReservationDetailService serviceType={reservationDetail.serviceType} />

          {/* step */}
          <ReservationDetailStep progressStage={reservationDetail.progressStage} />

          {/* result */}
          <ReservationDetailResult reservationDetail={reservationDetail} />

        </div>

      </div>
      
      <Footer />
    </>
  );
}
