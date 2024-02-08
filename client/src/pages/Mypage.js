import classNames from 'classnames';
import Footer from '../components/Footer';
import Header from '../components/Header';

export default function Mypage() {
  const image_url = "https://genesis-airport.s3.ap-northeast-2.amazonaws.com/car/g80.png"

  return (
    <>
      <Header />
      <div className={classNames('content', 'reservation-complete', 'page')}>
        <div className={classNames('title')}>
          <span className={classNames('title--text')}>My Page</span>
        </div>

        <div className={classNames('row-view')}>
          <div className={classNames('profile')}>
            <div className={classNames('profile__view')}>
              <div className={classNames('profile__view__image')}>
                <div className={classNames('profile__view__image__object')}/>
              </div>
              <div className={classNames('profile__view__info')}>
                <span className={classNames('profile__view__name')}>김희진</span>
                <span className={classNames('profile__view__email')}>insiderhj@gmail.com</span>
                <div className={classNames('profile__view__cars')}>
                  <div className={classNames('profile__view__car')}>
                    <span className={classNames('profile__view__car__name')}>Genesis G80</span>
                  </div>
                  <div className={classNames('profile__view__car')}>
                    <span className={classNames('profile__view__car__name')}>Genesis G90</span>
                  </div>
                </div>
              </div>
            </div>
            <div className={classNames('profile__view__setting')}></div>
          </div>

          <div className={classNames('row--seperator')} />

          <div className={classNames('row-view')}>
            <span className={classNames('row-view__title')}>예약 내역</span>
            <div className={classNames('row-view')}>
              <div className={classNames('reservation-abstract')}>
                <div className={classNames('reservation-abstract__id')}>
                  <span className={classNames('reservation-abstract__id__text')}>7</span>
                </div>
                <div className={classNames('reservation-abstract__info')}>
                  <div className={classNames('reservation-abstract__info__row')}>
                    <span className={classNames('reservation-abstract__info__title')}>예약 시간</span>
                    <span className={classNames('reservation-abstract__info__content')}>2024.03.01 14:00 ~ 2024.03.07 20:00</span>
                  </div>
                  <div className={classNames('reservation-abstract__info__row')}>
                    <span className={classNames('reservation-abstract__info__title')}>차량 정보</span>
                    <span className={classNames('reservation-abstract__info__content')}>Genesis GV80</span>
                  </div>
                  <div className={classNames('reservation-abstract__info__row')}>
                    <span className={classNames('reservation-abstract__info__title')}>차량 정보 상세</span>
                    <span className={classNames('reservation-abstract__info__content')}>블루핸즈 김포공항점</span>
                  </div>
                </div>
                <div class={classNames('progress-info')}>
                  <div class={classNames('progress-info__content')}>
                    <span class={classNames('progress-info__text')}>
                      정비중
                    </span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
{/* 
        <div className={classNames('row')}>
          <div className={classNames('row--title')}>에약일</div>
          <div className={classNames('row__content')}>
            <div className={classNames('content__detail')}>
              <span className={classNames('content__detail--name', 'outline')}>센터 방문</span>
              <span>{`${reservDate} ${reservTime}`}</span>
            </div>
            <div className={classNames('content__detail')}>
              <span className={classNames('content__detail--name', 'outline')}>픽업 시간</span>
              <span>{`${pickupDate} ${pickupTime}`}</span>
            </div>
          </div>
        </div>

        <div className={classNames('row--seperator')} />

        <div className={classNames('row')}>
          <div className={classNames('row--title')}>에약 차량</div>
          <div className={classNames('row__content')}>
            <div className={classNames('content__detail')}>
              <span className={classNames('content__detail--name', 'outline')}>차종</span>
              <span>{carSellName}</span>
            </div>
            <div className={classNames('content__detail')}>
              <span className={classNames('content__detail--name', 'outline')}>차량 번호</span>
              <span>{carNumber}</span>
            </div>
          </div>
        </div>

        <div className={classNames('row--seperator')} />

        <div className={classNames('row')}>
          <div className={classNames('row--title')}>방문 센터</div>
          <div className={classNames('row__content')}>
            <div className={classNames('content__detail')}>
              <span className={classNames('content__detail--name', 'outline')}>지점명</span>
              <span>{shopName}</span>
            </div>
            <div className={classNames('content__detail')}>
              <span className={classNames('content__detail--name', 'outline')}>주소</span>
              <span>{shopAddress}</span>
            </div>
            <div className={classNames('content__detail')}>
              <span className={classNames('content__detail--name', 'outline')}>연락처</span>
              <span>{shopPhoneNumber}</span>
            </div>
          </div>
        </div>

        <div className={classNames('row--seperator')} />

        <div className={classNames('row')}>
          <div className={classNames('row--title')}>선택 서비스</div>
          <div className={classNames('row__content')}>
            <div className={classNames('content__line')}>
              <span>각종 오일 및 호스 상태</span>
              <span className={classNames({ notSelected })}>배터리 상태 및 충전 전압</span>
            </div>
            <div className={classNames('content__line')}>
              <span className={classNames({ notSelected })}>엔진 구동 상태</span>
              <span>엔진 냉각수</span>
              <span className={classNames({ notSelected })}>에어클리너 점검</span>
            </div>
            <div className={classNames('content__line')}>
              <span>하체충격 및 손상 여부</span>
              <span className={classNames({ notSelected })}>브레이크패드 및 라이닝 마모</span>
            </div>
            <div className={classNames('content__line')}>
              <span>각종 등화 장치 점검</span>
              <span>엔진 미션 마운트</span>
              <span>서스펜션 뉴계토우 점검</span>
            </div>
            <div className={classNames('content__line')}>
              <span className={classNames({ notSelected })}>스테빌라이저 및 드라이버 샤프트</span>
              <span>스캐너 고장코드 진단</span>
            </div>
            <div className={classNames('content__line')}>
              <span className={classNames({ notSelected })}>에어컨 및 히터 작동 생태</span>
              <span>타이어 공기압 및 마모도</span>
              <span className={classNames({ notSelected })}>에어컨 필터</span>
            </div>
          </div>
        </div>

        <div className={classNames('row')}>
          <div className={classNames('row--title')}>요청 사항</div>
          <span>{request}</span>
        </div>

        <button className={classNames('button')}>예약내역 확인하기</button> */}
      </div>
      <Footer />
    </>
  );
}
