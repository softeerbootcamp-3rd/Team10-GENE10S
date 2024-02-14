import classNames from 'classnames';

export default function ReservationDetailStep() {
  
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

            {/* step-1 */}
            <div className={classNames('step-1')}>
              <div className={classNames('icon')}>
                <svg className={classNames('ellipse-2')} xmlns="http://www.w3.org/2000/svg" width="60" height="60" viewBox="0 0 60 60" fill="none">
                  <circle cx="30" cy="30" r="28.25" stroke="#B6B0A8" strokeWidth="3.5"/>
                </svg>
                <svg className={classNames('ellipse-3')} xmlns="http://www.w3.org/2000/svg" width="25" height="25" viewBox="0 0 25 25" fill="none">
                  <path d="M25 12.5C25 19.4036 19.4036 25 12.5 25C5.59644 25 0 19.4036 0 12.5C0 5.59644 5.59644 0 12.5 0C19.4036 0 25 5.59644 25 12.5Z" fill="#B6B0A8"/>
                </svg>
                <svg className={classNames('line-9')} xmlns="http://www.w3.org/2000/svg" width="4" height="101" viewBox="0 0 4 101" fill="none">
                  <path d="M2 0L2 101.008" stroke="#B6B0A8" strokeWidth="3.5" strokeDasharray="4 4"/>
                </svg>
              </div>
              <div className={classNames('text')}>
                <div className={classNames('text-1')}>
                  2024년 2월 6일 PM 02:30
                </div>
                <div className={classNames('text-2')}>
                  예약 완료
                </div>
              </div>
            </div>

            {/* step-2 */}
            <div className={classNames('step-2')}>
              <div className={classNames('icon')}>
                <svg className={classNames('ellipse-2')} xmlns="http://www.w3.org/2000/svg" width="60" height="60" viewBox="0 0 60 60" fill="none">
                  <circle cx="30" cy="30.0078" r="28.25" stroke="#B6B0A8" strokeWidth="3.5"/>
                </svg>
                <svg className={classNames('ellipse-3')} xmlns="http://www.w3.org/2000/svg" width="25" height="25" viewBox="0 0 25 25" fill="none">
                  <path d="M25 12.5078C25 19.4114 19.4036 25.0078 12.5 25.0078C5.59644 25.0078 0 19.4114 0 12.5078C0 5.60425 5.59644 0.0078125 12.5 0.0078125C19.4036 0.0078125 25 5.60425 25 12.5078Z" fill="#B6B0A8"/>
                </svg>
                <svg className={classNames('line-9')} xmlns="http://www.w3.org/2000/svg" width="4" height="286" viewBox="0 0 4 325" fill="none">
                  {/* 길이 조절하고싶으면 태그의 각 수를 조절한다. 현재: 325 */}
                  <path d="M2 0.0078125L2 325.008" stroke="#F3AB39" strokeWidth="3.5" strokeDasharray="4 4"/>
                </svg>            
              </div>
              <div className={classNames('text')}>
                <div className={classNames('text-1')}>
                  2024년 3월 25일 AM 10:22
                </div>
                <div className={classNames('text-2')}>
                  입고
                </div>
                <div className={classNames('frame-37222')}>
                  <div className={classNames('text-1')}>
                    세부 사항
                  </div>
                  <div className={classNames('text-2')}>
                    Genesis G80 [ 12가 3456 ] 블루핸드 인천공항점 입고 완료
                  </div>
                </div>
              </div>
            </div>

            {/* step-3 */}
            <div className={classNames('step-3')}>
              <div className={classNames('icon')}>
                <svg className={classNames('ellipse-2')} xmlns="http://www.w3.org/2000/svg" width="60" height="60" viewBox="0 0 60 60" fill="none">
                  <circle cx="30" cy="30.0078" r="28.25" stroke="#F3AB39" strokeWidth="3.5"/>
                </svg>
                <svg className={classNames('ellipse-3')} xmlns="http://www.w3.org/2000/svg" width="25" height="25" viewBox="0 0 25 25" fill="none">
                  <path d="M25 12.5078C25 19.4114 19.4036 25.0078 12.5 25.0078C5.59644 25.0078 0 19.4114 0 12.5078C0 5.60425 5.59644 0.0078125 12.5 0.0078125C19.4036 0.0078125 25 5.60425 25 12.5078Z" fill="#CF8D26"/>
                </svg>
              </div>
              <div className={classNames('text')}>
                <div className={classNames('text-1')}>
                  2024년 3월 25일 PM 06:43
                </div>
                <div className={classNames('text-2')}>
                  점검 중
                </div>
                <div className={classNames('frame-37222')}>
                  <div className={classNames('text-1')}>
                    세부 사항
                  </div>
                  <div className={classNames('text-2')}>
                    [ 각종 오일 및 호스 상태 ] 확인 중 ..
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
