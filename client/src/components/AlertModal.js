import classNames from 'classnames';

export default function AlertModal({ onClose, showText, visible }) {
  return (
    <div className={classNames('modal-frame', {'visible': visible})}>
      <div className={classNames('modal')}>
        <div className={classNames('modal-header')}>
          <span>알림</span>
        </div>
        <div className={classNames('body')}>
          <span className={classNames('alert-text')}>{showText}</span>
        </div>
        <div className={classNames('bottom')}>
          <div className={classNames('btn-cancel')} onClick={onClose}>
            <span>닫기</span>
          </div>
        </div>
      </div>
    </div>
  );
}
