import classNames from 'classnames';
import { Link } from 'react-router-dom';
import { ProgressArrow200, ProgressArrow500 } from './Arrow';

export default function ModalHeader({ shopName, currentStep }) {
  return (
    <div className={classNames('modal-header')}>
      <div className={classNames('shop-name')}>
        <span>{shopName}</span>
        <div className={classNames('dropdown')}>
          <ProgressArrow200 />
        </div>
      </div>
      <div className={classNames('steps')}>
        <div className={classNames('menu', { active: currentStep === 'date' })}>
          <span>날짜 선택</span>
        </div>
        <ProgressArrow500 />
        <div className={classNames('menu', { active: currentStep === 'info' })}>
          <span>예약 정보</span>
        </div>
        <ProgressArrow500 />
        <div className={classNames('menu', { active: currentStep === 'service' })}>
          <span>서비스 선택</span>
        </div>
      </div>
      <div className={classNames('exit')}>
        <Link to="/" replace={true} className={classNames('button')}>
          X
        </Link>
      </div>
    </div>
  );
}
