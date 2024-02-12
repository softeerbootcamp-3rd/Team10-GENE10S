import classNames from 'classnames';

export function BtnBlack({ text, onClick }) {
  return (
    <div className={classNames('btn-black')} onClick={onClick}>
      <span className={classNames('btn-text')}>{text}</span>
    </div>
  );
}
