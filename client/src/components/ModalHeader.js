import classNames from 'classnames';
import { ProgressArrow200, ProgressArrow500 } from './Arrow';

export default function ModalHeader({ shopName, currentStep }) {
  return (
    <div className={classNames('modal-header')}>
      <div className={classNames('shop-name')}>
        <span className={classNames('text')}>{shopName}</span>
        <div className={classNames('dropdown')}>
          <ProgressArrow200 />
        </div>
      </div>

      <div className={classNames('steps')}>
        <div className={classNames('menu', { active: currentStep === 'date' })}>
          <span className={classNames('text')}>날짜 선택</span>
        </div>
        <ProgressArrow500 />
        <div className={classNames('menu', { active: currentStep === 'info' })}>
          <span className={classNames('text')}>예약 정보</span>
        </div>
        <ProgressArrow500 />
        <div className={classNames('menu', { active: currentStep === 'service' })}>
          <span className={classNames('text')}>서비스 선택</span>
        </div>
      </div>

      <div className={classNames('exit')}>
        <span className={classNames('text')}>X</span>
      </div>
    </div>
  );
}
