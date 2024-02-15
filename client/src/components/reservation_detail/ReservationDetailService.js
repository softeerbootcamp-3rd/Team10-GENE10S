import classNames from 'classnames';
export default function ReservationDetailService({ serviceType }) {
  const serviceEntries = serviceType ? Object.entries(serviceType) : [];
  const serviceMap = {
    oil: '각종 오일 및 호스 상태',
    battery: '배터리 상태 및 충전 전압',
    'engine-run': '엔진 구동 상태',
    'engine-cooler': '엔진 냉각수',
    'air-cleaner': '에어클리너 점검',
    bottom: '하체충격 및 손상 여부',
    breakpad: '브레이크 패드 및 라이닝 마모',
    lamp: '각종 등화 장치 점검',
    'engine-mount': '엔진 미션 마운트',
    suspension: '서스펜션 뉴계토우 점검',
    shaft: '스테빌라이저 및 드라이버 샤프트',
    scanner: '스캐너 고장코드 진단',
    heater: '에어컨 및 히터 작동 생태',
    tire: '타이어 공기압 및 마모도',
    filter: '에어컨 필터',
  };
  return (
    <div className={classNames('service')}>
      {/* category */}
      <div className={classNames('category')}>
        <div className={classNames('text')}>선택 서비스</div>
      </div>
      {/* row-content */}
      <div className={classNames('row-content')}>
        <div className={classNames('grid-row')}>
          {serviceEntries.map(([service, active]) => (
            <div key={service} className={classNames({ 'text-active': active, 'text-deactive': !active })}>
              {serviceMap[service]}
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}
