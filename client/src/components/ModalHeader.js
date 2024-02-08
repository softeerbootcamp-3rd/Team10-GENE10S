import classNames from 'classnames';
import progressArrow from '../assets/progress-arrow.svg';
import { Link } from 'react-router-dom';

export default function ModalHeader({ shopName, currentStep }) {
  return (
    <div className={classNames('modal_header')}>
      <div className={classNames('shop-name')}>
        <span>{shopName}</span>
        {/* <div className={classNames('empty-square')} /> */}
      </div>
      <div className={classNames('steps')}>
        <div className={classNames('menu', { currentStep: currentStep === 'date' })}>
          <span>날짜 선택</span>
        </div>
        <img src={progressArrow} alt="arrow" />
        <div className={classNames('menu', { currentStep: currentStep === 'info' })}>
          <span>예약 정보</span>
        </div>
        <img src={progressArrow} alt="arrow" />
        <div className={classNames('menu', { currentStep: currentStep === 'service' })}>
          <span>서비스 선택</span>
        </div>
      </div>
      <div className={classNames('exit')}>
        <Link to="/" replace={true} className={classNames('button')}>
          X
        </Link>
        {/* <span>X</span> */}
      </div>
    </div>
  );
}
