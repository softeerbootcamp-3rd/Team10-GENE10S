import classNames from 'classnames';
import Footer from '../components/Footer';
import Header from '../components/Header';
import React from 'react';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

export default function Reservation() {
  const [activeButton, setActiveButton] = useState(null);
  const [serviceType, setServiceType] = useState('');
  const [visitDate, setVisitDate] = useState('');
  const [visitTime, setVisitTime] = useState('');
  const [pickupDate, setPickupDate] = useState('');
  const [pickupTime, setPickupTime] = useState('');
  const [phoneFirst, setPhoneFirst] = useState('');
  const [phoneMiddle, setPhoneMiddle] = useState('');
  const [phoneLast, setPhoneLast] = useState('');
  const [carType, setCarType] = useState('');
  const [carNumber, setCarNumber] = useState('');
  const [serviceList, setServiceList] = useState([]);
  const [couponNumber, setCouponNumber] = useState('');

  const navigate = useNavigate();

  const handleServiceTypeChange = event => {
    setServiceType(event.target.value);
  };

  const handleVisitDateChange = event => {
    setVisitDate(event.target.value);
  };

  const handleVisitTimeChange = event => {
    setVisitTime(event.target.value);
  };

  const handlePickupDateChange = event => {
    setPickupDate(event.target.value);
  };

  const handlePickupTimeChange = event => {
    setPickupTime(event.target.value);
  };

  const handlePhoneFirstChange = event => {
    setPhoneFirst(event.target.value);
  };

  const handlePhoneMiddleChange = event => {
    setPhoneMiddle(event.target.value);
  };

  const handlePhoneLastChange = event => {
    setPhoneLast(event.target.value);
  };

  const handleCarTypeChange = event => {
    setCarType(event.target.value);
  };

  const handleCarNumberChange = event => {
    setCarNumber(event.target.value);
  };

  const handleServiceListChange = event => {
    setServiceList(event.target.value);
  };

  const handleCouponNumberChange = event => {
    setCouponNumber(event.target.value);
  };
  const serviceIdentifiers = {
    '각종 오일 및 호스 상태': 'oil',
    '배터리 상태 및 충전 전압': 'battery',
    '엔진 구동 상태': 'engine-run',
    '엔진 냉각수': 'engine-cooler',
    '에어클리너 점검': 'air-cleaner',
    '하체충격 및 손상 여부': 'bottom',
    '브레이크패드 및 라이닝 마모': 'breakpad',
    '각종 등화 장치 점검': 'lamp',
    '엔진 미션 마운트': 'engine-mount',
    '서스펜션 뉴계토우 점검': 'suspension',
    '스테빌라이저 및 드라이버 샤프트': 'shaft',
    '스캐너 고장코드 진단': 'scanner',
    '에어컨 및 히터 작동 생태': 'heater',
    '타이어 공기압 및 마모도': 'tire',
    '에어컨 필터': 'filter',
  };

  const toggleServiceClass = event => {
    const button = event.target;
    const serviceName = button.textContent;
    const serviceIdentifier = serviceIdentifiers[serviceName];

    button.classList.toggle('service-item-selected');

    setServiceList(prevServiceList => {
      if (prevServiceList.includes(serviceIdentifier)) {
        return prevServiceList.filter(identifier => identifier !== serviceIdentifier);
      } else {
        return [...prevServiceList, serviceIdentifier];
      }
    });
  };

  const handleSubmit = event => {
    const fullPhoneNumber = `${phoneFirst}-${phoneMiddle}-${phoneLast}`;
    event.preventDefault();
    navigate('/reservation/complete', {
      state: {
        shopName: serviceType.includes('type1') ? '블루핸즈 김포공항점' : '블루핸즈 인천공항점',
        visitDate: visitDate,
        visitTime: visitTime,
        pickupDate: pickupDate,
        pickupTime: pickupTime,
        phone: fullPhoneNumber,
        carType: carType.includes('type1') ? 'Genesis G80' : 'Genesis G90',
        carNumber: carNumber,
        serviceList: serviceList,
        couponNumber: couponNumber,
        username: '철수맘',
        shopAddress: '인천 중구 용유서로 172번길',
        shopPhoneNumber: '032-888-8888',
      },
    });
  };

  const handleButtonClick = buttonId => {
    setActiveButton(buttonId);
  };

  const buttonClass = buttonId => classNames({ 'active-button': activeButton === buttonId });

  return (
    <React.Fragment>
      <Header />
      <div className={classNames('wrapper')}>
        <div className={classNames('reservation-title')}>
          <div className={classNames('reservation-title__text')}>
            <span>Reservation</span>
          </div>
        </div>

        <div className={classNames('reservation-form')}>
          <form onSubmit={handleSubmit}>
            <div className={classNames('form-group')}>
              <div className={classNames('form-group__label')}>
                <label htmlFor="serviceType">지점 선택</label>
              </div>
              <select id="serviceType" value={serviceType.value} onChange={handleServiceTypeChange}>
                <option value="">지점을 선택해주세요</option>
                <option value="type1">블루핸즈 김포공항점</option>
                <option value="type2">블루핸즈 인천공항점</option>
              </select>
            </div>

            <div className={classNames('form-group')}>
              <div className={classNames('form-group__label')}>
                <label htmlFor="visitDate">센터 방문</label>
              </div>
              <div className={classNames('date-input')}>
                <input type="date" id="visitDate" value={visitDate} onChange={handleVisitDateChange} />
                <input type="time" id="visitTime" value={visitTime} onChange={handleVisitTimeChange} />
              </div>
            </div>

            <div className="form-group">
              <div className={classNames('form-group__label')}>
                <label htmlFor="visitTime">픽업 시각</label>
              </div>
              <div className={classNames('date-input')}>
                <input type="date" value={pickupDate} onChange={handlePickupDateChange} />
                <input type="time" value={pickupTime} onChange={handlePickupTimeChange} />
              </div>
            </div>

            <div className="form-group">
              <div className={classNames('form-group__label')}>
                <label htmlFor="phone">연락처</label>
              </div>
              <div className={classNames('phone-number-container')}>
                <input type="text" value={phoneFirst} onChange={handlePhoneFirstChange} maxLength="3" />
                <input type="text" value={phoneMiddle} onChange={handlePhoneMiddleChange} maxLength="4" />
                <input type="text" value={phoneLast} onChange={handlePhoneLastChange} maxLength="4" />
              </div>
            </div>

            <div className={classNames('car-info')}>
              <h1>차량 정보</h1>
            </div>

            <div className={classNames('form-group')}>
              <div className={classNames('form-group__label')}>
                <label htmlFor="serviceType">내 차 정보 자동 입력</label>
              </div>
              <button
                type="button"
                id="button1"
                className={buttonClass('button1')}
                onClick={() => handleButtonClick('button1')}
              >
                Genesis G80 (12가 3456)
              </button>
              <button
                type="button"
                id="button2"
                className={buttonClass('button2')}
                onClick={() => handleButtonClick('button2')}
              >
                Genesis G90 (12나 3456)
              </button>
            </div>

            <div className={classNames('form-group')}>
              <div className={classNames('form-group__label')}>
                <label htmlFor="serviceType">차종</label>
              </div>
              <select value={carType} onChange={handleCarTypeChange}>
                <option value="type1">Genesis G80</option>
                <option value="type2">Genesis G90</option>
              </select>
            </div>

            <div className="form-group">
              <div className={classNames('form-group__label')}>
                <label htmlFor="phone">차량 번호</label>
              </div>
              <div className={classNames('car-number-container')}>
                <input type="text" value={carNumber} onChange={handleCarNumberChange} maxLength={8} />
              </div>
            </div>

            <div className={classNames('car-info')}>
              <h1>서비스 선택</h1>
            </div>

            <div class="form-group">
              <div class="form-group__label">
                <label>점검 목록</label>
              </div>
              <div class="service-list">
                <div class="list-line">
                  <button class="service-item" type="button" onClick={toggleServiceClass}>
                    각종 오일 및 호스 상태
                  </button>
                  <button class="service-item" type="button" onClick={toggleServiceClass}>
                    배터리 상태 및 충전 전압
                  </button>
                </div>
                <div class="list-line">
                  <button class="service-item" type="button" onClick={toggleServiceClass}>
                    엔진 구동 상태
                  </button>
                  <button class="service-item" type="button" onClick={toggleServiceClass}>
                    엔진 냉각수
                  </button>
                  <button class="service-item" type="button" onClick={toggleServiceClass}>
                    에어클리너 점검
                  </button>
                </div>
                <div class="list-line">
                  <button class="service-item" type="button" onClick={toggleServiceClass}>
                    하체충격 및 손상 여부
                  </button>
                  <button class="service-item" type="button" onClick={toggleServiceClass}>
                    브레이크 패드 및 라이닝 마모
                  </button>
                </div>
                <div class="list-line">
                  <button class="service-item" type="button" onClick={toggleServiceClass}>
                    각종 등화 장치 점검
                  </button>
                  <button class="service-item" type="button" onClick={toggleServiceClass}>
                    엔진 미션 마운트
                  </button>
                  <button class="service-item" type="button" onClick={toggleServiceClass}>
                    서스펜션 뉴계토우 점검
                  </button>
                </div>
                <div class="list-line">
                  <button class="service-item" type="button" onClick={toggleServiceClass}>
                    스테빌라이저 및 드라이브 샤프트
                  </button>
                  <button class="service-item" type="button" onClick={toggleServiceClass}>
                    스캐너 고장코드 진단
                  </button>
                </div>
                <div class="list-line">
                  <button class="service-item" type="button" onClick={toggleServiceClass}>
                    에어컨 및 히터 작동 상태
                  </button>
                  <button class="service-item" type="button" onClick={toggleServiceClass}>
                    타이어 공기압 및 마모도
                  </button>
                  <button class="service-item" type="button" onClick={toggleServiceClass}>
                    에어컨 필터
                  </button>
                </div>
              </div>
            </div>

            <div className="form-group">
              <div className={classNames('form-group__label')}>
                <label htmlFor="phone">쿠폰 번호</label>
              </div>
              <div className={classNames('coupon-number-container')}>
                <input type="text" value={couponNumber} onChange={handleCouponNumberChange} maxLength={12} />
              </div>
              <button type="submit">쿠폰번호 확인</button>
            </div>
            <div className={classNames('submit-button')}>
              <button type="submit" class="button">
                예약하기
              </button>
              <button type="submit" class="button">
                취소
              </button>
            </div>
          </form>
        </div>
      </div>
      <Footer />
    </React.Fragment>
  );
}