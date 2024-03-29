import classNames from 'classnames';
import axios from '../api/Settings';
import Footer from '../components/Footer';
import Header from '../components/Header';
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
  const [fadeIn, setFadeIn] = useState(false);

  useEffect(() => {
    window.scrollTo(0, 0);
    setFadeIn(true);
  }, []);

  useEffect(() => {
    if (location.state && location.state.reservationId) {
      setReservationId(location.state.reservationId);
    }
  }, [location]);

  useEffect(() => {
    if (reservationId) {
      axios
        .get(`/v1/reservation/${reservationId}/detail`)
        .then(response => {
          setReservationDetail(response.data.data);
        })
        .catch(error => console.log(error));
    }
  }, [reservationId]);

  return (
    <div className={classNames('reservation-detail-page', { fadein: fadeIn })}>
      <Header />
      <div className={classNames('title')}>
        <div className={classNames('text')}>마이페이지 {'>'} 예약 내역</div>
        <div className={classNames('reservation-info')}>
          <div className={classNames('text-1')}>예약 번호</div>
          <div className={classNames('text-2')}>GEN-20240212-00004364</div>
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
      <Footer />
    </div>
  );
}
