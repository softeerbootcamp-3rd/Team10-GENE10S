import classNames from 'classnames';

export default function ModalHeader() {

  return (
    <div className={classNames('modal-header')}>
      <div className={classNames('shop-name')}>
        <span className={classNames('text')}>
          블루핸즈 인천공항점
        </span>
        <div className={classNames('dropdown')}>
          <div className={classNames('progress-arrow-200')}>
            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="12" viewBox="0 0 20 12" fill="none">
              <path d="M1.5 1.66406L10.4048 10.3375L19 1.66406" stroke="#DDD8D2" strokeWidth="2"/>
            </svg>
          </div>
        </div>
      </div>

      <div className={classNames('steps')}>
        <div className={classNames('menu', 'active')}>
          <span className={classNames('text')}>
            날짜 선택
          </span>
        </div>
        <div className={classNames('progress-arrow-500')}>
          <svg xmlns="http://www.w3.org/2000/svg" width="12" height="20" viewBox="0 0 12 20" fill="none">
            <path d="M1 18.75L9.67346 9.84524L0.999999 1.25" stroke="#726E69" strokeWidth="2"/>
          </svg>
        </div>
        <div className={classNames('menu')}>
          <span className={classNames('text')}>
            예약 정보
          </span>
        </div>
        <div className={classNames('progress-arrow-500')}>
          <svg xmlns="http://www.w3.org/2000/svg" width="12" height="20" viewBox="0 0 12 20" fill="none">
            <path d="M1 18.75L9.67346 9.84524L0.999999 1.25" stroke="#726E69" strokeWidth="2"/>
          </svg>
        </div>
        <div className={classNames('menu')}>
          <span className={classNames('text')}>
            서비스 선택
          </span>
        </div>
      </div>

      <div className={classNames('exit')}>
        <span className={classNames('text')}>
          X
        </span>
      </div>
    </div>
  );
}
