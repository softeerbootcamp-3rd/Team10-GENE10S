import classNames from 'classnames';
import { useEffect, useState } from 'react';

export default function ModalHeader() {
  const [isLogin, setIsLogin] = useState(false);
  const [uuid, setUuid] = useState('');

  return (
    <div className={classNames('modal-header')}>
      <div className={classNames('shop-name')}>
        <span className={classNames('text')}>
          블루핸즈 인천공항점
        </span>
        <div className={classNames('dropdown')}>
          <div className={classNames('progress-arrow-200')}>
            {/* todo */}
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
          {/* todo */}
        </div>
        <div className={classNames('menu')}>
          <span className={classNames('text')}>
            예약 정보
          </span>
        </div>
        <div className={classNames('progress-arrow-500')}>
          {/* todo */}
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
