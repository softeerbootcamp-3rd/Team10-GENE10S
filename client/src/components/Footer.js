import classNames from 'classnames';

export default function Footer() {
  return (
    <div className={classNames('footer')}>
      <div className={classNames('share')}>
        <div className={classNames('share-group')}>
          <div className={classNames('share-left')}>
            <div className={classNames('share-left__text')}>예약하기</div>
            <div className={classNames('share-left__facebook')} />
            <div className={classNames('share-left__x')} />
            <div className={classNames('share-left__url')} />
          </div>
          <div className={classNames('share-right')}>
            <div className={classNames('share-right__text')}>TOP</div>
            <div className={classNames('share-right__arrow')}> &gt;</div>
          </div>
        </div>
      </div>
      <div className={classNames('copyright')}>
        <div className={classNames('copyright__image')} />
        <div className={classNames('copyright__phrase')}>
          <span>© Copyright 2024 Softeer Bootcamp Gene10s.</span>
          <span>All Rights Reserved.</span>
        </div>
      </div>
    </div>
  );
}
