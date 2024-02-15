import classNames from 'classnames';
import { useEffect } from 'react';

export default function ReservationDetailStep({ progressStage }) {
  useEffect(() => {
    const paths = document.querySelectorAll('.line-9 path');
    paths.forEach(path => {
      const divHeight = path.closest('.stage-completed').clientHeight - 60;
      path.setAttribute('d', `M2 0L2 ${divHeight}`);
      path.closest('svg').setAttribute('height', divHeight);
      path.closest('svg').setAttribute('viewBox', `0 0 4 ${divHeight}`);
    });
  }, [progressStage]);

  // 배열이 정의되어 있는지 확인
  if (!progressStage || progressStage.length === 0) {
    return null; // 배열이 정의되어 있지 않거나 빈 배열이면 아무것도 렌더링하지 않음
  }

  return (
    <>
      <div className={classNames('step')}>

        {/* category */}
        <div className={classNames('category')}>
          <div className={classNames('text')}>
            진행 단계
          </div>
        </div>

        {/* step-detail */}
        <div className={classNames('step-detail')}>
          <div className={classNames('progress-bar')}>
            {progressStage.map((stage, index) => (
              <div key={index} className={classNames({ 'stage-completed': index < progressStage.length - 1, 'stage-inprogress': index == progressStage.length - 1 })}>
                <div className={classNames('icon')}>
                  <svg className={classNames('ellipse-2')} xmlns="http://www.w3.org/2000/svg" width="60" height="60" viewBox="0 0 60 60" fill="none">
                    <circle cx="30" cy="30" r="28.25" stroke={index < progressStage.length - 1 ? '#B6B0A8' : '#F3AB39'} strokeWidth="3.5"/>
                  </svg>
                  <svg className={classNames('ellipse-3')} xmlns="http://www.w3.org/2000/svg" width="25" height="25" viewBox="0 0 25 25" fill="none">
                    <path d="M25 12.5C25 19.4036 19.4036 25 12.5 25C5.59644 25 0 19.4036 0 12.5C0 5.59644 5.59644 0 12.5 0C19.4036 0 25 5.59644 25 12.5Z" fill={index < progressStage.length - 1 ? '#B6B0A8' : '#CF8D26'}/>
                  </svg>
                  {index !== progressStage.length - 1 && (
                    <svg className={classNames('line-9')} xmlns="http://www.w3.org/2000/svg" width="4" height="101" viewBox="0 0 4 101" fill="none">
                      <path d={`M2 0L2 101`} stroke={index < progressStage.length - 2 ? '#B6B0A8' : '#F3AB39'} strokeWidth="3.5" strokeDasharray="4 4"/>
                    </svg>
                  )}
                </div>
                <div className={classNames('text')}>
                  <div className={classNames('text-1')}>
                    {stage.date}
                  </div>
                  <div className={classNames('text-2')}>
                    {stage.step}
                  </div>
                  {index !== 0 && stage.detail && (
                    <div className={classNames('detail')}>
                      <div className={classNames('text-1')}>
                        세부 사항
                      </div>
                      <div className={classNames('text-2')}>
                        {stage.detail}
                      </div>
                    </div>
                  )}
                </div>
              </div>
            ))}
          </div>
        </div>
      </div>
    </>
  );
}
