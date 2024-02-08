import classNames from 'classnames';

export default function ModalDate() {
  const date = '2024-02-01';
  const time = '8:00';

  return (
    <div className={classNames('modal_body')}>
      <div className={classNames('frame_left')}>
        <div className={classNames('calender')} />
        <div className={classNames('available-times')} />
      </div>
      <div className={classNames('frame_right')}>
        <div className={classNames('category_row')}>
          <span>1. 센터 방문</span>
          <span>{`${date} / ${time}`}</span>
        </div>
        <div className={classNames('category_row_hover')}>
          <span>2. 픽업 시간</span>
          <span>픽업 시간을 선택해주세요</span>
        </div>
        <div className={classNames('button')}>
          <span>다음</span>
        </div>
      </div>
    </div>
  );
}
