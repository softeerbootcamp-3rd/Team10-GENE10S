import classNames from 'classnames';
import { useEffect, useState } from 'react';
import { ProgressArrow200 } from '../Arrow';
export default function ReservationDetailResult({ reservationDetail }) {
  const [currentBeforeIndex, setCurrentBeforeIndex] = useState(0);
  const [currentAfterIndex, setCurrentAfterIndex] = useState(0);
  const [beforeImages, setBeforeImages] = useState([]);
  const [afterImages, setAfterImages] = useState([]);
  const [cumBeforeSizes, setCumBeforeSizes] = useState([]);
  const [cumAfterSizes, setCumAfterSizes] = useState([]);
  useEffect(() => {
    if (reservationDetail && reservationDetail.beforeImages) {
      setBeforeImages(reservationDetail.beforeImages);
    } else {
      setBeforeImages([]);
    }
    if (reservationDetail && reservationDetail.afterImages) {
      setAfterImages(reservationDetail.afterImages);
    } else {
      setAfterImages([]);
    }
  }, [reservationDetail]);
  useEffect(() => {
    const loadImageWidths = async () => {
      const beforeSizes = await Promise.all(beforeImages.map(getImageSize));
      const afterSizes = await Promise.all(afterImages.map(getImageSize));
      const cumulativeBeforeWidths = beforeSizes.reduce(
        (acc, curr) => {
          const total = acc.length === 0 ? curr : acc[acc.length - 1] + curr.width + 30;
          return [...acc, total];
        },
        [0],
      );
      const cumulativeAfterWidths = afterSizes.reduce(
        (acc, curr) => {
          const total = acc.length === 0 ? curr : acc[acc.length - 1] + curr.width + 30;
          return [...acc, total];
        },
        [0],
      );
      setCumBeforeSizes(cumulativeBeforeWidths);
      setCumAfterSizes(cumulativeAfterWidths);
    };
    loadImageWidths();
  }, [beforeImages, afterImages]);
  const getImageSize = src => {
    return new Promise((resolve, reject) => {
      const img = new Image();
      img.onload = () => resolve({ width: img.width * (400 / img.height), height: 400 });
      img.onerror = reject;
      img.src = src;
    });
  };
  const handleBeforePrev = () => {
    setCurrentBeforeIndex(prevIndex => (prevIndex === 0 ? 0 : prevIndex - 1));
  };
  const handleBeforeNext = () => {
    setCurrentBeforeIndex(prevIndex =>
      prevIndex === beforeImages.length - 1 ? beforeImages.length - 1 : prevIndex + 1,
    );
  };
  const handleAfterPrev = () => {
    setCurrentAfterIndex(prevIndex => (prevIndex === 0 ? 0 : prevIndex - 1));
  };
  const handleAfterNext = () => {
    setCurrentAfterIndex(prevIndex => (prevIndex === afterImages.length - 1 ? afterImages.length - 1 : prevIndex + 1));
  };
  return (
    <div className={classNames('result')}>
      {/* category */}
      <div className={classNames('category')}>
        <div className={classNames('text')}>점검 결과</div>
      </div>
      {/* before */}
      <div className={classNames('before')}>
        {/* subtitle */}
        <div className={classNames('subtitle')}>
          <div className={classNames('text')}>점검 전</div>
        </div>
        {/* image */}
        <div className={classNames('image')}>
          <div className={classNames('arrow-left', { 'disabled': currentBeforeIndex === 0 })} onClick={handleBeforePrev}>
            <ProgressArrow200 />
          </div>
          <div className={classNames('image-wrapper')}>
            { beforeImages.length === 0 ? (
              <div className="no-image">점검 전 이미지가 없습니다.</div>
            ) : (
              <div
              className={classNames('images')}
              style={{
                width: `${100}%`,
                transform: `translateX(-${cumBeforeSizes[currentBeforeIndex]}px)`,
                transition: 'transform 0.5s ease-in-out',
              }}
            >
              {beforeImages.map((image, index) => (
                <img key={index} src={image} style={{ height: `400px` }} />
              ))}
            </div>
            )}
          </div>
          <div className={classNames('arrow-right', { 'disabled': currentBeforeIndex === beforeImages.length - 1 || beforeImages.length === 0 })} onClick={handleBeforeNext}>
            <ProgressArrow200 />
          </div>
        </div>
      </div>
      {/* after */}
      <div className={classNames('after')}>
        {/* subtitle */}
        <div className={classNames('subtitle')}>
          <div className={classNames('text')}>점검 후</div>
        </div>
        {/* image */}
        <div className={classNames('image')}>
          <div className={classNames('arrow-left', { 'disabled': currentAfterIndex === 0 })} onClick={handleAfterPrev}>
            <ProgressArrow200 />
          </div>
          <div className={classNames('image-wrapper')}>
            { afterImages.length === 0 ? (
              <div className="no-image">점검 후 이미지가 없습니다.</div>
            ) : (
              <div
              className={classNames('images')}
              style={{
                width: `${100}%`,
                transform: `translateX(-${cumAfterSizes[currentAfterIndex]}px)`,
                transition: 'transform 0.5s ease-in-out',
              }}
            >
              {afterImages.map((image, index) => (
                <img key={index} src={image} style={{ height: `400px` }} />
              ))}
            </div>
            )}
          </div>
          <div className={classNames('arrow-right', { 'disabled': currentAfterIndex === afterImages.length - 1 || afterImages.length === 0 })} onClick={handleAfterNext}>
            <ProgressArrow200 />
          </div>
        </div>
      </div>
      {/* comment */}
      <div className={classNames('comment')}>
        {/* subtitle */}
        <div className={classNames('subtitle')}>
          <div className={classNames('text')}>관리자 코멘트</div>
        </div>
        {/* content */}
        <div className={classNames('content')}>
          <div className={classNames('text')}>{reservationDetail.checkupResult}</div>
        </div>
      </div>
    </div>
  );
}
