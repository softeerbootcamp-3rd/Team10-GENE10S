import classNames from 'classnames';
import { useEffect, useState } from 'react';

import { ProgressArrow200 } from '../Arrow';


import before1 from '../../assets/before-1.png';
import before2 from '../../assets/before-2.png';
import before3 from '../../assets/before-3.png';
import before4 from '../../assets/sample-model.png'

import after1 from '../../assets/after-1.png';
import after2 from '../../assets/after-2.png';
import after3 from '../../assets/after-3.png';

export default function ReservationDetailResult() {
  const [currentBeforeIndex, setCurrentBeforeIndex] = useState(0);
  const [currentAfterIndex, setCurrentAfterIndex] = useState(0);

  const beforeImages = [before1, before4, before3, before2];
  const afterImages = [after1, after2, after3];

  const [cumBeforeSizes, setCumBeforeSizes] = useState([]);
  const [cumAfterSizes, setCumAfterSizes] = useState([]);

  useEffect(() => {
    const loadImageWidths = async () => {
      const beforeSizes = await Promise.all(beforeImages.map(getImageSize));
      const afterSizes = await Promise.all(afterImages.map(getImageSize));

      const cumulativeBeforeWidths = beforeSizes.reduce((acc, curr) => {
        const total = acc.length === 0 ? curr : acc[acc.length - 1] + curr.width + 30;
        return [...acc, total];
      }, [0]);
      const cumulativeAfterWidths = afterSizes.reduce((acc, curr) => {
        const total = acc.length === 0 ? curr : acc[acc.length - 1] + curr.width + 30;
        return [...acc, total];
      }, [0]);

      setCumBeforeSizes(cumulativeBeforeWidths);
      setCumAfterSizes(cumulativeAfterWidths);
    };

    loadImageWidths();
  }, []);

  const getImageSize = (src) => {
    return new Promise((resolve, reject) => {
      const img = new Image();
      img.onload = () => resolve({ width: img.width * (400 / img.height), height: 400 });
      img.onerror = reject;
      img.src = src;
    });
  };

  const handleBeforePrev = () => {
    setCurrentBeforeIndex((prevIndex) => (prevIndex === 0 ? 0 : prevIndex - 1));
  };

  const handleBeforeNext = () => {
    setCurrentBeforeIndex((prevIndex) => (prevIndex === beforeImages.length - 1 ? beforeImages.length - 1 : prevIndex + 1));
  };

  const handleAfterPrev = () => {
    setCurrentAfterIndex((prevIndex) => (prevIndex === 0 ? 0 : prevIndex - 1));
  };

  const handleAfterNext = () => {
    setCurrentAfterIndex((prevIndex) => (prevIndex === afterImages.length - 1 ? afterImages.length - 1 : prevIndex + 1));
  };

  return (
    <>
      <div className={classNames('result')}>

        {/* category */}
        <div className={classNames('category')}>
          <div className={classNames('text')}>
            점검 결과
          </div>
        </div>

        {/* before */}
        <div className={classNames('before')}>

          {/* subtitle */}
          <div className={classNames('subtitle')}>
            <div className={classNames('text')}>
              점검 전
            </div>
          </div>

          {/* image */}
          <div className={classNames('image')}>
            <div className={classNames('arrow-left')} onClick={handleBeforePrev}>
              <ProgressArrow200/>
            </div>
            <div className={classNames('image-wrapper')}>
              <div  className={classNames('images')} style={{ width: `${100}%`, transform: `translateX(-${cumBeforeSizes[currentBeforeIndex]}px)`, transition: 'transform 0.5s ease-in-out' }}>
                {beforeImages.map((image, index) => (
                  <img key={index} src={image} style={{ height: `400px`}}/>
                ))}
              </div>
            </div>
            <div className={classNames('arrow-right')} onClick={handleBeforeNext}>
              <ProgressArrow200/>
            </div>
          </div>
        </div>

        {/* after */}
        <div className={classNames('after')}>
          
          {/* subtitle */}
          <div className={classNames('subtitle')}>
            <div className={classNames('text')}>
              점검 후
            </div>
          </div>

          {/* image */}
          <div className={classNames('image')}>
            <div className={classNames('arrow-left')} onClick={handleAfterPrev}>
              <ProgressArrow200/>
            </div>
            <div className={classNames('image-wrapper')}>
              <div  className={classNames('images')} style={{ width: `${100}%`, transform: `translateX(-${cumAfterSizes[currentAfterIndex]}px)`, transition: 'transform 0.5s ease-in-out' }}>
                {afterImages.map((image, index) => (
                  <img key={index} src={image} style={{ height: `400px`}}/>
                ))}
              </div>
            </div>
            <div className={classNames('arrow-right')} onClick={handleAfterNext}>
              <ProgressArrow200/>
            </div>
          </div>
        </div>

        {/* comment */}
        <div className={classNames('comment')}>

          {/* subtitle */}
          <div className={classNames('subtitle')}>
            <div className={classNames('text')}>
              관리자 코멘트
            </div>
          </div>

          {/* content */}
          <div className={classNames('content')}>
            <div className={classNames('text')}>
              에어컨 안 망가졌네요.<br/>
              다음 방문은 2026년 2월 1일 이전에 하시면 되겠습니다.<br/>
              근데 차가 정말 멋있네요. 저도 타고 싶습니다 G80.<br/>
              정비만 몇 년째 하고 있는데 내 차는 못 사네...<br/>
              감사합니다.
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
