import classNames from 'classnames';
import { useEffect } from 'react';

export default function ReservationDetailStep() {

  const progressData = {
    "progress_stage": [
      {
        "name": "예약완료",
        "date": "2024년 2월 6일 PM 02:30",
        "detail": ""
      }, {
        "name": "입고",
        "date": "2024년 3월 25일 AM 10:22",
        "detail": "Genesis G80 [ 12가 3456 ] 블루핸드 인천공항점 입고 완료"
      }, {
        "name": "점검중",
        "date": "2024년 3월 25일 PM 06:43",
        "detail": "[ 각종 오일 및 호스 상태 ] 확인 중 ..."
      }
    ]
  };

  useEffect(() => {
    const paths = document.querySelectorAll('.line-9 path');
    paths.forEach(path => {
      const divHeight = path.closest('.icon').clientHeight - 60;
      path.setAttribute('d', `M2 0L2 ${divHeight}`);
      path.closest('svg').setAttribute('height', divHeight);
      path.closest('svg').setAttribute('viewBox', `0 0 4 ${divHeight}`);
    });
  }, []);

  return (
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
          {progressData.progress_stage.map((stage, index) => (
            <div key={index} className={classNames(`step-${index + 1}`, { 'completed': index < progressData.progress_stage.length - 1 })}>
              <div className={classNames('icon')}>
                <svg className={classNames('ellipse-2')} xmlns="http://www.w3.org/2000/svg" width="60" height="60" viewBox="0 0 60 60" fill="none">
                  <circle cx="30" cy="30" r="28.25" stroke={index < progressData.progress_stage.length - 1 ? '#B6B0A8' : '#F3AB39'} strokeWidth="3.5"/>
                </svg>
                <svg className={classNames('ellipse-3')} xmlns="http://www.w3.org/2000/svg" width="25" height="25" viewBox="0 0 25 25" fill="none">
                  <path d="M25 12.5C25 19.4036 19.4036 25 12.5 25C5.59644 25 0 19.4036 0 12.5C0 5.59644 5.59644 0 12.5 0C19.4036 0 25 5.59644 25 12.5Z" fill={index < progressData.progress_stage.length - 1 ? '#B6B0A8' : '#CF8D26'}/>
                </svg>
                {index !== progressData.progress_stage.length - 1 && (
                  <svg className={classNames('line-9')} xmlns="http://www.w3.org/2000/svg" width="4" height="101" viewBox="0 0 4 101" fill="none">
                    <path d='M2 0L2 101' stroke={index < progressData.progress_stage.length - 2 ? '#B6B0A8' : '#F3AB39'} strokeWidth="3.5" strokeDasharray="4 4"/>
                  </svg>
                )}
              </div>
              <div className={classNames('text')}>
                <div className={classNames('text-1')}>
                  {stage.date}
                </div>
                <div className={classNames('text-2')}>
                  {stage.name}
                </div>
                {stage.detail && (
                  <div className={classNames('frame-37222')}>
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
  );
}
