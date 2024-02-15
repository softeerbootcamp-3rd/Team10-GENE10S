import classNames from 'classnames';
import { ProgressArrow200, ProgressArrow500 } from './Arrow';
import { Link } from 'react-router-dom';

export default function ModalHeader({ shopName, currentStep, changeShop }) {
  function handleDrop() {
    const shopChangeButton = document.querySelector('.shop-selector');
    if (shopChangeButton.classList.value.includes('dropped')) {
      shopChangeButton.classList.remove('dropped');
    } else {
      shopChangeButton.classList.add('dropped');
    }
  }

  return (
    <div className={classNames('modal-header')}>
      <div className={classNames('shop-name')}>
        <span className={classNames('text')}>{shopName}</span>
        <div className={classNames('dropdown')} onClick={handleDrop}>
          <ProgressArrow200 />
        </div>
      </div>
      <div className={classNames('shop-name', 'shop-selector')}>
        <span className={classNames('text')} onClick={changeShop}>
          {shopName === '블루핸즈 인천공항점' ? '블루핸즈 김포공항점' : '블루핸즈 인천공항점'}
        </span>
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

      <Link to="/reservation/intro" className={classNames('exit')}>
        <span className={classNames('text')}>X</span>
      </Link>
    </div>
  );
}
