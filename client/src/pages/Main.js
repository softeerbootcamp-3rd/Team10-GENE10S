import Header from '../components/Header';
import Footer from '../components/Footer';
import classNames from 'classnames';
import React from 'react';

function runOnScroll() {
  const observer = new IntersectionObserver(
    (entries, observer) => {
      entries.forEach(entry => {
        if (entry.isIntersecting) {
          entry.target.style.opacity = 1;
          entry.target.style.visibility = 'visible';
          entry.target.style.transform = 'translateY(0)';
          entry.target.style.transfrom = 'translateX(0)';
          observer.unobserve(entry.target);
        }
      });
    },
    {
      threshold: 0.1,
    },
  );

  const sectionsToObserve = [
    '.main .body .info-area-1 .title',
    '.main .body .info-area-1 .contents',
    '.main .body .info-area-2 .title',
    '.main .body .info-area-2 .progress-group',
    '.main .body .info-area-3 .title',
    '.main .body .info-area-3 .info-card-1',
    '.main .body .info-area-3 .info-card-2',
  ];

  sectionsToObserve.forEach(selector => {
    const element = document.querySelector(selector);
    if (element) {
      observer.observe(element);
    }
  });
}

// 페이지 로드 시 함수 실행
document.addEventListener('DOMContentLoaded', runOnScroll);

window.addEventListener('scroll', runOnScroll);

document.addEventListener('DOMContentLoaded', function () {
  setTimeout(function () {
    let text = document.querySelector('.btn-main');
    text.style.opacity = 1;
    text.style.visibility = 'visible';
  }, 1600);

  setTimeout(function () {
    let text = document.querySelector('.title-main');
    text.style.opacity = 1;
    text.style.visibility = 'visible';
  }, 600);

  setTimeout(function () {
    let text = document.querySelector('.title-sub');
    text.style.opacity = 1;
    text.style.visibility = 'visible';
  }, 900);

  setTimeout(function () {
    let text = document.querySelector('.content');
    text.style.opacity = 1;
    text.style.visibility = 'visible';
  }, 1100);
});

export default function Main() {

  return (
    <div className={classNames('main')}>
      <Header />
      <div className={classNames('title-area')}>
        <div className={classNames('image')}>
          <div className={classNames('btn-main')}>
            <a className={classNames('reservation')} href="/Reservation" style={{ textDecoration: 'none' }}>
              예약하기
            </a>
          </div>
          <div className={classNames('info-main')}>
            <span className={classNames('title-main')}>GENESIS</span>
            <span className={classNames('title-sub')}>AIRPORT SERVICE</span>
            <span className={classNames('content')}>제네시스가 제공하는 특별한 카라이프</span>
          </div>
        </div>
      </div>

      <div className={classNames('body')}>
        <div className={classNames('info-area-1')}>
          <div className={classNames('title')}>
            <span className={classNames('title-main')}>차별화된 모빌리티 서비스</span>
          </div>
          <div className={classNames('contents')}>
            <div className={classNames('image-group')}>
              <div className={classNames('image-1')} />
              <div className={classNames('info')}>
                <span className={classNames('title-sub')}>안심 보관</span>
                <span className={classNames('content')}>제네시스 고객만을 위한 전용 주차장에 차량 안심 보관</span>
              </div>
            </div>
            <div className={classNames('image-group')}>
              <div className={classNames('image-2')} />
              <div className={classNames('info')}>
                <span className={classNames('title-sub')}>공항 픽업</span>
                <span className={classNames('content')}>제네시스 에어포트 서비스 고객만을 위한 공항 픽업 서비스</span>
              </div>
            </div>
            <div className={classNames('image-group')}>
              <div className={classNames('image-3')} />
              <div className={classNames('info')}>
                <span className={classNames('title-sub')}>차량 점검</span>
                <span className={classNames('content')}>기본 점검 15종, 소모품 교환, SW 업데이트 지원</span>
              </div>
            </div>
          </div>
        </div>

        <div className={classNames('info-area-2')}>
          <div className={classNames('title')}>예약 과정</div>
          <div className={classNames('progress-group')}>
            <div className={classNames('progress-step')}>
              <div className={classNames('circle-area')}>
                <div className={classNames('left')} />
                <div className={classNames('center')}>
                  <div className={classNames('circle')} />
                  <div className={classNames('step-number')}>1</div>
                </div>
                <div className={classNames('right')}>
                  <div className={classNames('line')} />
                </div>
              </div>
              <div className={classNames('info-area')}>
                <div className={classNames('info-title')}>이용 예약</div>
                <div className={classNames('info-content')}>
                  이용일 전 사전예약 필수 <br />
                  (당일 예약 불가)
                </div>
              </div>
            </div>
            <div className={classNames('progress-step')}>
              <div className={classNames('circle-area')}>
                <div className={classNames('left')}>
                  <div className={classNames('line')} />
                </div>
                <div className={classNames('center')}>
                  <div className={classNames('circle')} />
                  <div className={classNames('step-number')}>2</div>
                </div>
                <div className={classNames('right')}>
                  <div className={classNames('line')} />
                </div>
              </div>
              <div className={classNames('info-area')}>
                <div className={classNames('info-title')}>차량 인도</div>
                <div className={classNames('info-content')}>
                  공항 도착 20분 전 <br />
                  발렛 업체로부터 전화 수신
                  <br />
                  지정 장소에서 차량 인도
                </div>
              </div>
            </div>
            <div className={classNames('progress-step')}>
              <div className={classNames('circle-area')}>
                <div className={classNames('left')}>
                  <div className={classNames('line')} />
                </div>
                <div className={classNames('center')}>
                  <div className={classNames('circle')} />
                  <div className={classNames('step-number')}>3</div>
                </div>
                <div className={classNames('right')}>
                  <div className={classNames('line')} />
                </div>
              </div>
              <div className={classNames('info-area')}>
                <div className={classNames('info-title')}>안심 차량 관리</div>
                <div className={classNames('info-content')}>
                  기본 점검 15종 <br />
                  소모품 교환 (모빌리티케어) <br />
                  사전점검 (SW 업데이트 항목) <br />
                  차량 안심 보관 (전용 주차장)
                </div>
              </div>
            </div>
            <div className={classNames('progress-step')}>
              <div className={classNames('circle-area')}>
                <div className={classNames('left')}>
                  <div className={classNames('line')} />
                </div>
                <div className={classNames('center')}>
                  <div className={classNames('circle')} />
                  <div className={classNames('step-number')}>4</div>
                </div>
                <div className={classNames('right')} />
              </div>
              <div className={classNames('info-area')}>
                <div className={classNames('info-title')}>차량 인수</div>
                <div className={classNames('info-content')}>
                  인수 희망 20분 전 연락 <br />
                  지정 장소에서 차량 인수 <br />
                  인수일 2일 내 점검/교환 내역 업로드
                </div>
              </div>
            </div>
          </div>
        </div>

        <div className={classNames('info-area-3')}>
          <div className={classNames('title')}>서비스 안내</div>
          <div className={classNames('info-card-1')}>
            <div className={classNames('image-1')} />
            <div className={classNames('info-text-1')}>
              <div className={classNames('title')}>
                <span className={classNames('title-text')}>김포공항 에어포트 서비스</span>
              </div>
              <div className={classNames('content')}>
                <span className={classNames('content-text')}>
                  안전과 신뢰를 바탕으로 김포공항에서의 여정을 편안하게 안내합니다.
                </span>
              </div>
              <div className={classNames('btn-info')}>
                <a className={classNames('detail')} href="/Reservation" style={{ textDecoration: 'none' }}>
                  자세히 보기
                </a>
              </div>
            </div>
          </div>

          <div className={classNames('info-card-2')}>
            <div className={classNames('info-text-2')}>
              <div className={classNames('title')}>
                <span className={classNames('title-text')}>인천공항 에어포트 서비스</span>
              </div>
              <div className={classNames('content')}>
                <span className={classNames('content-text')}>
                  최상의 편의성과 서비스로 인천공항 여정을 완벽하게 지원합니다.
                </span>
              </div>
              <div className={classNames('btn-info')}>
                <a className={classNames('detail')} href="/Reservation" style={{ textDecoration: 'none' }}>
                  자세히 보기
                </a>
              </div>
            </div>
            <div className={classNames('image-2')} />
          </div>
        </div>
      </div>
      <Footer />
    </div>
  );
}
