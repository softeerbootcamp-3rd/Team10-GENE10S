import Header from '../components/Header';
import Footer from '../components/Footer';
import classNames from 'classnames';
import { useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import axios from '../api/Settings';
import Cookies from 'js-cookie';
import React from 'react';

export default function Main() {
  const location = useLocation();

  useEffect(() => {
    async function fetchTokenAndUserData(code) {
      try {
        const { data } = await axios.post('v1/oauth/token', {
          grantType: 'authorization_code',
          code,
          redirectUri: process.env.REACT_APP_REDIRECT_URI,
          clientId: process.env.REACT_APP_CLIENT_ID,
          clientSecret: process.env.REACT_APP_CLIENT_SECRET,
        });

        const sid = data.sid;
        const expires = new Date();
        expires.setTime(expires.getTime() + 24 * 60 * 60 * 1000);

        Cookies.set('sid', sid, { expires });

        window.location.href = 'http://localhost:3000';
      } catch (error) {
        console.error('Error during authentication or fetching user data:', error);
      }
    }

    const queryParams = new URLSearchParams(location.search);
    const code = queryParams.get('code');

    if (code) {
      fetchTokenAndUserData(code);
    }
  }, [location]);

  return (
    <React.Fragment>
      <Header />
      <div className={classNames('title-area')}>
        <div className={classNames('image')}>
          <div className={classNames('btn-main')}>
            <div className={classNames('title-text')}>예약하기</div>
            <div className={classNames('progress-arrow-big')}>
              <div className={classNames('image')} />
            </div>
          </div>
        </div>
      </div>
      <div className={classNames('body')}>
        <div className={classNames('info-area')}>
          <div className={classNames('area-title')}>차별화된 모빌리티 서비스</div>
          <div className={classNames('image-group')}>
            <div className={classNames('image-1')} />
            <div className={classNames('image-2')} />
          </div>
        </div>
        <div className={classNames('info-area')}>
          <div className={classNames('area-title')}>예약 과정</div>
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
        <div className={classNames('info-area')}>
          <div className={classNames('area-title')}>서비스 안내</div>
          <div className={classNames('info-card')}>
            <div className={classNames('half-frame-image-1')} />
            <div className={classNames('half-frame')}>
              <div className={classNames('title')}>
                <span className={classNames('head-text')}>김포 에어포트 서비스</span>
              </div>
              <div className={classNames('content')}>
                <div className={classNames('information')}>
                  <span className={classNames('head-text')}>서비스 내용</span>
                  <span className={classNames('content-text')}>
                    - 김포공항 무상 발레 서비스 <br />
                    - 제네시스 전용 안심 주차 공간 <br />
                    - 소모품 교환 서비스 <br />
                    (엔진오일세트, 에어컨 필터, 와이퍼 블레이드 中 택) <br />
                    <br />
                    ※ 안심 주차 비용 <br />
                    7천 원/일, 6일 째부터 1만 원/일, vat제외 *00시 기준
                  </span>
                </div>
                <div className={classNames('target')}>
                  <span className={classNames('head-text')}>서비스 대상</span>
                  <span className={classNames('content-text')}>
                    김포공항 이용 제네시스 고객 중 <br />
                    소모품 교환 쿠폰 및 홈투홈 서비스 쿠폰 보유 고객
                  </span>
                </div>
                <div className={classNames('coupon')}>
                  <span className={classNames('head-text')}>홈투홈 쿠폰 사용 안내</span>
                  <span className={classNames('content-text')}>
                    이용기간 내 소모품 교환 쿠폰 및 홈투홈 서비스 쿠폰 사용 가능 <br />※ 서비스 이용 후 쿠폰 차감
                  </span>
                </div>
                <div className={classNames('usage')}>
                  <span className={classNames('head-text')}>서비스 이용 안내</span>
                  <span className={classNames('content-text')}>
                    제네시스 홈페이지에서 최대 두 달~최소 2주일 전에 예약 <br />※ 이용객이 많은 경우 조기 마감될 수
                    있습니다.
                  </span>
                </div>
              </div>
            </div>
          </div>

          <div className={classNames('info-card')}>
            <div className={classNames('half-frame')}>
              <div className={classNames('title')}>
                <span className={classNames('head-text')}>인천 에어포트 서비스</span>
              </div>
              <div className={classNames('content')}>
                <div className={classNames('information')}>
                  <span className={classNames('head-text')}>서비스 내용</span>
                  <span className={classNames('content-text')}>
                    - 공항 셔틀 서비스 <br />
                    - 기본 점검 15종 <br />
                    - 차량 클리닝 서비스 <br />
                    - 제네시스 전용 안심 주차 공간 <br /> <br />
                    ※ 서비스 비용 <br />
                    7만 원(4박 5일 이내 기준), 6일 째부터 1만 원/일 추가, <br />
                    vat제외 *00시 기준
                  </span>
                </div>
                <div className={classNames('target')}>
                  <span className={classNames('head-text')}>서비스 대상</span>
                  <span className={classNames('content-text')}>
                    인천공항 이용 제네시스 고객 중 홈투홈 서비스 쿠폰 보유 고객
                  </span>
                </div>
                <div className={classNames('coupon')}>
                  <span className={classNames('head-text')}>홈투홈 쿠폰 사용 안내</span>
                  <span className={classNames('content-text')}>
                    이용기간 내 소모품 교환 쿠폰 및 홈투홈 서비스 쿠폰 사용 가능 <br />※ 서비스 이용 후 쿠폰 차감
                  </span>
                </div>
                <div className={classNames('usage')}>
                  <span className={classNames('head-text')}>서비스 이용 안내</span>
                  <span className={classNames('content-text')}>
                    제네시스 홈페이지에서 최대 두 달~최소 2주일 전에 예약 <br />※ 이용객이 많은 경우 조기 마감될 수
                    있습니다.
                  </span>
                </div>
              </div>
            </div>
            <div className={classNames('half-frame-image-2')} />
          </div>
        </div>
      </div>
      <Footer />
    </React.Fragment>
  );
}
