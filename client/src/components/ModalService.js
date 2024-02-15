import classNames from 'classnames';
import { BtnBlack } from './Button';
import { useState } from 'react';
import { validCoupon } from '../api/ReservationApi';

export default function ModalService({ props, prevStep, submit }) {
  const [services, setServices] = useState(props.services);
  const [customerRequest, setCustomerRequest] = useState(props.customerRequest);
  const [coupon, setCoupon] = useState(props.coupon);
  const [couponValid, setCouponValid] = useState(false);
  const [couponInvalid, setCouponInvalid] = useState(false);

  function handleServiceClick(event) {
    const target = event.currentTarget.id;
    setServices(prevServices => ({
      ...prevServices,
      [target]: !prevServices[target]
    }));
  }

  function handlePrev() {
    prevStep(services, customerRequest, coupon);
  }

  function handleSubmit() {
    submit(services, customerRequest, coupon, couponValid);
  }

  function handleCouponValid() {
    validCoupon(coupon).then(result => {
      setCouponValid(result.valid);
      setCouponInvalid(!result.valid);
    });
  }

  return (
    <>
      <div className={classNames('body')}>
        <div className={classNames('frame-left')}>
          <img className={classNames('image')} src={require(`../assets/sample-service.png`)} alt="" key="image" />
        </div>
        <div className={classNames('frame-right')}>
          <div className={classNames('category-row')}>
            <span className={classNames('title')}>5. 서비스선택</span>
            <div className={classNames('content')}>
              <div
                id="oil"
                className={classNames('select', { active: services.oil })}
                onClick={e => {
                  handleServiceClick(e);
                }}
              >
                각종 오일 및 호스 상태
              </div>
              <div
                id="battery"
                className={classNames('select', { active: services.battery })}
                onClick={e => handleServiceClick(e)}
              >
                배터리 상태 및 충전 전압
              </div>
              <div
                id="engineRun"
                className={classNames('select', { active: services.engineRun })}
                onClick={e => handleServiceClick(e)}
              >
                엔진 구동 상태
              </div>
              <div
                id="engineCooler"
                className={classNames('select', { active: services.engineCooler })}
                onClick={e => handleServiceClick(e)}
              >
                엔진 냉각수
              </div>
              <div
                id="airCleaner"
                className={classNames('select', { active: services.airCleaner })}
                onClick={e => handleServiceClick(e)}
              >
                에어클리너
              </div>
              <div
                id="bottom"
                className={classNames('select', { active: services.bottom })}
                onClick={e => handleServiceClick(e)}
              >
                하체충격 및 손상 여부
              </div>
              <div
                id="breakpad"
                className={classNames('select', { active: services.breakpad })}
                onClick={e => handleServiceClick(e)}
              >
                브레이크 패드 및 라이닝 마모
              </div>
              <div
                id="lamp"
                className={classNames('select', { active: services.lamp })}
                onClick={e => handleServiceClick(e)}
              >
                각종 등화 장치 점검
              </div>
              <div
                id="engineMount"
                className={classNames('select', { active: services.engineMount })}
                onClick={e => handleServiceClick(e)}
              >
                엔진 미션 마운트
              </div>
              <div
                id="suspension"
                className={classNames('select', { active: services.suspension })}
                onClick={e => handleServiceClick(e)}
              >
                서스펜션 뉴계토우 점검
              </div>
              <div
                id="shaft"
                className={classNames('select', { active: services.shaft })}
                onClick={e => handleServiceClick(e)}
              >
                스테빌라이저 및 드라이버 샤프트
              </div>
              <div
                id="scanner"
                className={classNames('select', { active: services.scanner })}
                onClick={e => handleServiceClick(e)}
              >
                스캐너 고장코드 진단
              </div>
              <div
                id="heater"
                className={classNames('select', { active: services.heater })}
                onClick={e => handleServiceClick(e)}
              >
                에어컨 및 히터 작동 생태
              </div>
              <div
                id="tire"
                className={classNames('select', { active: services.tire })}
                onClick={e => handleServiceClick(e)}
              >
                타이어 공기압 및 마모드
              </div>
              <div
                id="filter"
                className={classNames('select', { active: services.filter })}
                onClick={e => handleServiceClick(e)}
              >
                에어컨 필터
              </div>
            </div>
          </div>
          <div className={classNames('category-row')}>
            <span className={classNames('title')}>6. 추가 요청 사항</span>
            <div className={classNames('content')}>
              <input
                type="text"
                value={customerRequest}
                className={classNames('input-left', 'w-max', 'input-text')}
                onChange={e => {
                  setCustomerRequest(e.target.value);
                }}
              />
            </div>
          </div>
          <div className={classNames('category-row', 'last-child')}>
            <span className={classNames('title')}>7. 쿠폰번호 입력</span>
            <div className={classNames('content')}>
              <input
                type="text"
                value={coupon}
                className={classNames('input-left', 'w-400', 'input-text',
                  {'coupon-valid': couponValid, 'coupon-invalid': couponInvalid})}
                onChange={e => {
                  setCoupon(e.target.value);
                  setCouponValid(false);
                  setCouponInvalid(false);
                }}
              />
              <div className={classNames('btn-check')} onClick={handleCouponValid}>
                <span className={classNames('btn-text')}>쿠폰번호 확인하기</span>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div className={classNames('btn-group')}>
        <BtnBlack text="이전" onClick={handlePrev} />
        <BtnBlack text="예약하기" onClick={handleSubmit} />
      </div>
    </>
  );
}
