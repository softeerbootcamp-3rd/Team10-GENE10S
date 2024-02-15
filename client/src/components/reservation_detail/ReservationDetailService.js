import classNames from 'classnames';

export default function ReservationDetailService() {
  
  return (
    <>
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
    </>
  );
}
