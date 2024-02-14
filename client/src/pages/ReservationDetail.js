import classNames from 'classnames';
import Footer from '../components/Footer';
import Header from '../components/Header';
import axios from 'axios';
import { useEffect, useState } from 'react';
import { ProgressArrow200 } from '../components/Arrow';

import before1 from '../assets/before-1.png';
import before2 from '../assets/before-2.png';
import before3 from '../assets/before-3.png';
import before4 from '../assets/before-4.png';

import after1 from '../assets/after-1.png';
import after2 from '../assets/after-2.png';
import after3 from '../assets/after-3.png';
import after4 from '../assets/after-4.png';

export default function ReservationDetail() {
  const [profileInfo, setProfileInfo] = useState({});
  const [carList, setCarList] = useState([]);
  const [reservationInfo, setReservationList] = useState([]);

  useEffect(() => {
    window.scrollTo(0, 0);
  }, []);

  useEffect(() => {
    axios.get('/v1/user/profile')
      .then ((response) => setProfileInfo(response.data.data))
      .catch ((error) => console.log("Error message :", error))

    axios.get('/v1/reservation/car-list')
      .then ((response) => setCarList(response.data.data))

    axios.get('/v1/reservation/list')
      .then ((response) => setReservationList(response.data.data))
  }, [])
  
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
                  <div className={classNames('frame-37224')}>
                    <div className={classNames('text')}>
                      2024년 3월 1일 (금)
                    </div>
                    <div className={classNames('text')}>
                      오전 08:00
                    </div>
                  </div>
                </div>
                <div className={classNames('arrival')}>
                  <div className={classNames('text')}>
                    픽업 시간
                  </div>
                  <div className={classNames('frame-37225')}>
                    <div className={classNames('text')}>
                      2024년 3월 9일 (토)
                    </div>
                    <div className={classNames('text')}>
                      오후 04:30
                    </div>
                  </div>
                </div>
              </div>

              {/* shop-info */}
              <div className={classNames('shop-info')}>
                <div className={classNames('text')}>
                  <div className={classNames('text')}>
                    블루핸즈 인천공항점
                  </div>
                  <div className={classNames('detail-info')}>
                    <div className={classNames('text-address')}>
                      인천 중구 용유서로172번길 56
                    </div>
                    <div className={classNames('frame-37226')}>
                      <svg className={classNames('phone')} xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 18 18" fill="none">
                        <path d="M13.8638 11.3398L10.5367 11.9894C8.28929 10.8528 6.90104 9.54728 6.09313 7.51221L6.71518 4.1502L5.53932 1H2.50889C1.59792 1 0.880565 1.75849 1.01662 2.66605C1.35627 4.93175 2.35775 9.03973 5.28521 11.9894C8.3595 15.0869 12.7873 16.431 15.2242 16.9653C16.1653 17.1716 17 16.4319 17 15.4616V12.5439L13.8638 11.3398Z" stroke="#DDD8D2" strokeWidth="1.2" strokeLinecap="round" strokeLinejoin="round"/>
                      </svg>
                      <div className={classNames('text-phone')}>
                        032-710-7436
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
                      Genesis G80
                    </div>
                    <div className={classNames('text-2')}>
                      123가 4567
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
                    COUPONNUMBER123
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
                    에어컨이 망가진 거 같아요. 확인 부탁드립니다.
                  </div>
                </div>
              </div>
            </div>
          </div>

          {/* service */}
          <div className={classNames('service')}>
            
            {/* category */}
            <div className={classNames('category')}>
              <div className={classNames('text')}>
                선택 서비스
              </div>
            </div>

            {/* row-content */}
            <div className={classNames('row-content')}>
              <div className={classNames('grid-row')}>
                <div className={classNames('text-deactive')}>
                  각종 오일 및 호스 상태
                </div>
                <div className={classNames('text-deactive')}>
                  배터리 상태 및 충전 전압
                </div>
                <div className={classNames('text-deactive')}>
                  엔진 구동 상태
                </div>
                <div className={classNames('text-deactive')}>
                  엔진 냉각수
                </div>
                <div className={classNames('text-deactive')}>
                  에어클리너 점검
                </div>
                <div className={classNames('text-deactive')}>
                  에어컨 필터
                </div>
                <div className={classNames('text-deactive')}>
                  하체충격 및 손상 여부
                </div>
                <div className={classNames('text-deactive')}>
                  브레이크 패드 및 라이닝 마모
                </div>
                <div className={classNames('text-deactive')}>
                  각종 등화 장치 점검
                </div>
                <div className={classNames('text-deactive')}>
                  엔진 미션 마운트
                </div>
                <div className={classNames('text-deactive')}>
                  서스펜션 뉴계토우 점검
                </div>
                <div className={classNames('text-deactive')}>
                  스테빌라이저 및 드라이버 샤프트
                </div>
                <div className={classNames('text-deactive')}>
                  스캐너 고장코드 진단
                </div>
                <div className={classNames('text-deactive')}>
                  에어컨 및 히터 작동 생태
                </div>
                <div className={classNames('text-deactive')}>
                  타이어 공기압 및 마모도
                </div>
              </div>
            </div>
          </div>

          {/* step */}
          <div className={classNames('step')}>
            
            {/* category */}
            <div className={classNames('category')}>
              <div className={classNames('text')}>
                진행 단계
              </div>
            </div>

            {/* step-detail */}
            <div className={classNames('step-detail')}>
              <div className={classNames('progress-bar')}>

                {/* step-1 */}
                <div className={classNames('step-1')}>
                  <div className={classNames('icon')}>
                    <svg className={classNames('ellipse-2')} xmlns="http://www.w3.org/2000/svg" width="60" height="60" viewBox="0 0 60 60" fill="none">
                      <circle cx="30" cy="30" r="28.25" stroke="#B6B0A8" strokeWidth="3.5"/>
                    </svg>
                    <svg className={classNames('ellipse-3')} xmlns="http://www.w3.org/2000/svg" width="25" height="25" viewBox="0 0 25 25" fill="none">
                      <path d="M25 12.5C25 19.4036 19.4036 25 12.5 25C5.59644 25 0 19.4036 0 12.5C0 5.59644 5.59644 0 12.5 0C19.4036 0 25 5.59644 25 12.5Z" fill="#B6B0A8"/>
                    </svg>
                    <svg className={classNames('line-9')} xmlns="http://www.w3.org/2000/svg" width="4" height="101" viewBox="0 0 4 101" fill="none">
                      <path d="M2 0L2 101.008" stroke="#B6B0A8" strokeWidth="3.5" strokeDasharray="4 4"/>
                    </svg>
                  </div>
                  <div className={classNames('text')}>
                    <div className={classNames('text-1')}>
                      2024년 2월 6일 PM 02:30
                    </div>
                    <div className={classNames('text-2')}>
                      예약 완료
                    </div>
                  </div>
                </div>

                {/* step-2 */}
                <div className={classNames('step-2')}>
                  <div className={classNames('icon')}>
                    <svg className={classNames('ellipse-2')} xmlns="http://www.w3.org/2000/svg" width="60" height="60" viewBox="0 0 60 60" fill="none">
                      <circle cx="30" cy="30.0078" r="28.25" stroke="#B6B0A8" strokeWidth="3.5"/>
                    </svg>
                    <svg className={classNames('ellipse-3')} xmlns="http://www.w3.org/2000/svg" width="25" height="25" viewBox="0 0 25 25" fill="none">
                      <path d="M25 12.5078C25 19.4114 19.4036 25.0078 12.5 25.0078C5.59644 25.0078 0 19.4114 0 12.5078C0 5.60425 5.59644 0.0078125 12.5 0.0078125C19.4036 0.0078125 25 5.60425 25 12.5078Z" fill="#B6B0A8"/>
                    </svg>
                    <svg className={classNames('line-9')} xmlns="http://www.w3.org/2000/svg" width="4" height="286" viewBox="0 0 4 325" fill="none">
                      {/* 길이 조절하고싶으면 태그의 각 수를 조절한다. 현재: 325 */}
                      <path d="M2 0.0078125L2 325.008" stroke="#F3AB39" strokeWidth="3.5" strokeDasharray="4 4"/>
                    </svg>            
                  </div>
                  <div className={classNames('text')}>
                    <div className={classNames('text-1')}>
                      2024년 3월 25일 AM 10:22
                    </div>
                    <div className={classNames('text-2')}>
                      입고
                    </div>
                    <div className={classNames('frame-37222')}>
                      <div className={classNames('text-1')}>
                        세부 사항
                      </div>
                      <div className={classNames('text-2')}>
                        Genesis G80 [ 12가 3456 ] 블루핸드 인천공항점 입고 완료
                      </div>
                    </div>
                  </div>
                </div>

                {/* step-3 */}
                <div className={classNames('step-3')}>
                  <div className={classNames('icon')}>
                    <svg className={classNames('ellipse-2')} xmlns="http://www.w3.org/2000/svg" width="60" height="60" viewBox="0 0 60 60" fill="none">
                      <circle cx="30" cy="30.0078" r="28.25" stroke="#F3AB39" strokeWidth="3.5"/>
                    </svg>
                    <svg className={classNames('ellipse-3')} xmlns="http://www.w3.org/2000/svg" width="25" height="25" viewBox="0 0 25 25" fill="none">
                      <path d="M25 12.5078C25 19.4114 19.4036 25.0078 12.5 25.0078C5.59644 25.0078 0 19.4114 0 12.5078C0 5.60425 5.59644 0.0078125 12.5 0.0078125C19.4036 0.0078125 25 5.60425 25 12.5078Z" fill="#CF8D26"/>
                    </svg>
                  </div>
                  <div className={classNames('text')}>
                    <div className={classNames('text-1')}>
                      2024년 3월 25일 PM 06:43
                    </div>
                    <div className={classNames('text-2')}>
                      점검 중
                    </div>
                    <div className={classNames('frame-37222')}>
                      <div className={classNames('text-1')}>
                        세부 사항
                      </div>
                      <div className={classNames('text-2')}>
                        [ 각종 오일 및 호스 상태 ] 확인 중 ..
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          {/* result */}
          <div className={classNames('result')}>

            {/* category */}
            <div className={classNames('category')}>
              <div className={classNames('text')}>
                점검 결과
              </div>
            </div>

            {/* before */}
            <div className={classNames('before')}>

              {/* subtitle */}
              <div className={classNames('subtitle')}>
                <div className={classNames('text')}>
                  점검 전
                </div>
              </div>

              {/* image */}
              <div className={classNames('image')}>
                <div className={classNames('arrow-left')}>
                  <ProgressArrow200/>
                </div>
                <div className={classNames('images')}>
                  <img src={before1}/>
                  <img src={before2}/>
                  <img src={before3}/>
                  <img src={before4}/>
                </div>
                <div className={classNames('arrow-right')}>
                  <ProgressArrow200/>
                </div>
              </div>
            </div>

            {/* after */}
            <div className={classNames('after')}>
              
              {/* subtitle */}
              <div className={classNames('subtitle')}>
                <div className={classNames('text')}>
                  점검 후
                </div>
              </div>

              {/* image */}
              <div className={classNames('image')}>
                <div className={classNames('arrow-left')}>
                  <ProgressArrow200/>
                </div>
                <div className={classNames('images')}>
                  <img src={after1}/>
                  <img src={after2}/>
                  <img src={after3}/>
                  <img src={after4}/>
                </div>
                <div className={classNames('arrow-right')}>
                  <ProgressArrow200/>
                </div>
              </div>
            </div>

            {/* comment */}
            <div className={classNames('comment')}>

              {/* subtitle */}
              <div className={classNames('subtitle')}>
                <div className={classNames('text')}>
                  관리자 코멘트
                </div>
              </div>

              {/* content */}
              <div className={classNames('content')}>
                <div className={classNames('text')}>
                  에어컨 안 망가졌네요.<br/>
                  다음 방문은 2026년 2월 1일 이전에 하시면 되겠습니다.<br/>
                  근데 차가 정말 멋있네요. 저도 타고 싶습니다 G80.<br/>
                  정비만 몇 년째 하고 있는데 내 차는 못 사네...<br/>
                  감사합니다.
                </div>
              </div>
            </div>
          </div>
        </div>

      </div>
      
      <Footer />
    </>
  );
}
