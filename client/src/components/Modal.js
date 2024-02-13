import classNames from 'classnames';

export default function Modal({ onClose }) {
  const handleRegisterClick = () => {
    onClose();
  };

  return (
    <div className={classNames('modal-frame')}>
      <div className={classNames('modal')}>
        <div className={classNames('modal-header')}>
          <span>차량 등록하기</span>
        </div>
        <div className={classNames('body')}>
          <div className={classNames('info-row')}>
            <span className={classNames('key')}>차종</span>
            <input className={classNames('input')}/>
          </div>
          <div className={classNames('info-row')}>
            <span className={classNames('key')}>차량 번호</span>
            <input className={classNames('input')}/>
          </div>
        </div>
        <div className={classNames('bottom')}>
          <div className={classNames('btn-cancel')} onClick={handleRegisterClick}>
            <span>취소하기</span>
          </div>
          <div className={classNames('btn-submit')} onClick={handleRegisterClick}>
            <span>등록하기</span>
          </div>
        </div>
      </div>
    </div>
  );
}
