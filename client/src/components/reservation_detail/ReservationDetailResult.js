import classNames from 'classnames';

import { ProgressArrow200 } from '../Arrow';


import before1 from '../../assets/before-1.png';
import before2 from '../../assets/before-2.png';
import before3 from '../../assets/before-3.png';
import before4 from '../../assets/before-4.png';

import after1 from '../../assets/after-1.png';
import after2 from '../../assets/after-2.png';
import after3 from '../../assets/after-3.png';
import after4 from '../../assets/after-4.png';

export default function ReservationDetailResult() {
  
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
            <div className={classNames('arrow-left')}>
              <ProgressArrow200/>
            </div>
            <div className={classNames('images')}>
              <img src={before1}/>
              <img src={before2}/>
              <img src={before3}/>
              <img src={before4}/>
            </div>
            <div className={classNames('arrow-right')}>
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
            <div className={classNames('arrow-left')}>
              <ProgressArrow200/>
            </div>
            <div className={classNames('images')}>
              <img src={after1}/>
              <img src={after2}/>
              <img src={after3}/>
              <img src={after4}/>
            </div>
            <div className={classNames('arrow-right')}>
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
