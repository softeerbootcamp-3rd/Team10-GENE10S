import classNames from 'classnames';
import Footer from '../components/Footer';
import Header from '../components/Header';
import { useLocation } from 'react-router-dom';
import { Link } from 'react-router-dom';

export default function ReservationComplete() {
  const location = useLocation();
  const reservInfo = location.state;

  return (
    <>
      <Header />
      <div className={classNames('content', 'reservation-complete')}>
        <div className={classNames('title')}>
          <span className={classNames('title--text')}>Reservation</span>
        </div>

        <div className={classNames('info')}>
          <div>
            <span className={classNames('info--name')}>{reservInfo ? reservInfo.username : '사용자명'}</span>
            <span>님, 다음 내용으로 예약 되었습니다.</span>
          </div>
          <div>
            <span>예약일 </span>
            <span className={classNames('outline')}>{reservInfo ? reservInfo.visitDate : '예약날짜'}</span>
            <span>까지 센터로 방문해주세요.</span>
          </div>
        </div>

        <div className={classNames('row')}>
          <div className={classNames('row--title')}>에약일</div>
          <div className={classNames('row__content')}>
            <div className={classNames('content__detail')}>
              <span className={classNames('content__detail--name', 'outline')}>센터 방문</span>
              <span>{reservInfo ? `${reservInfo.visitDate} ${reservInfo.visitTime}` : `예약 날짜 및 시간`}</span>
            </div>
            <div className={classNames('content__detail')}>
              <span className={classNames('content__detail--name', 'outline')}>픽업 시간</span>
              <span>{reservInfo ? `${reservInfo.pickupDate} ${reservInfo.pickupTime}` : '픽업 날짜 및 시간'}</span>
            </div>
          </div>
        </div>

        <div className={classNames('row--seperator')} />

        <div className={classNames('row')}>
          <div className={classNames('row--title')}>에약 차량</div>
          <div className={classNames('row__content')}>
            <div className={classNames('content__detail')}>
              <span className={classNames('content__detail--name', 'outline')}>차종</span>
              <span>{reservInfo ? reservInfo.carType : '차종'}</span>
            </div>
            <div className={classNames('content__detail')}>
              <span className={classNames('content__detail--name', 'outline')}>차량 번호</span>
              <span>{reservInfo ? reservInfo.carNumber : '차량 번호'}</span>
            </div>
          </div>
        </div>

        <div className={classNames('row--seperator')} />

        <div className={classNames('row')}>
          <div className={classNames('row--title')}>방문 센터</div>
          <div className={classNames('row__content')}>
            <div className={classNames('content__detail')}>
              <span className={classNames('content__detail--name', 'outline')}>지점명</span>
              <span>{reservInfo ? reservInfo.shopName : '지점명'}</span>
            </div>
            <div className={classNames('content__detail')}>
              <span className={classNames('content__detail--name', 'outline')}>주소</span>
              <span>{reservInfo ? reservInfo.shopAddress : '지점 주소'}</span>
            </div>
            <div className={classNames('content__detail')}>
              <span className={classNames('content__detail--name', 'outline')}>연락처</span>
              <span>{reservInfo ? reservInfo.shopPhoneNumber : '032-710-7436'}</span>
            </div>
          </div>
        </div>

        <div className={classNames('row--seperator')} />

        <div className={classNames('row')}>
          <div className={classNames('row--title')}>선택 서비스</div>
          <div className={classNames('row__content')}>
            <div className={classNames('content__line')}>
              <span className={classNames({ selected: reservInfo ? reservInfo.serviceList.includes('oil') : false })}>
                각종 오일 및 호스 상태
              </span>
              <span
                className={classNames({ selected: reservInfo ? reservInfo.serviceList.includes('battery') : false })}
              >
                배터리 상태 및 충전 전압
              </span>
            </div>
            <div className={classNames('content__line')}>
              <span
                className={classNames({
                  selected: reservInfo ? reservInfo.serviceList.includes('engine-run') : false,
                })}
              >
                엔진 구동 상태
              </span>
              <span
                className={classNames({
                  selected: reservInfo ? reservInfo.serviceList.includes('engine-cooler') : false,
                })}
              >
                엔진 냉각수
              </span>
              <span
                className={classNames({
                  selected: reservInfo ? reservInfo.serviceList.includes('air-cleaner') : false,
                })}
              >
                에어클리너 점검
              </span>
            </div>
            <div className={classNames('content__line')}>
              <span
                className={classNames({ selected: reservInfo ? reservInfo.serviceList.includes('bottom') : false })}
              >
                하체충격 및 손상 여부
              </span>
              <span
                className={classNames({ selected: reservInfo ? reservInfo.serviceList.includes('breakpad') : false })}
              >
                브레이크패드 및 라이닝 마모
              </span>
            </div>
            <div className={classNames('content__line')}>
              <span className={classNames({ selected: reservInfo ? reservInfo.serviceList.includes('lamp') : false })}>
                각종 등화 장치 점검
              </span>
              <span
                className={classNames({
                  selected: reservInfo ? reservInfo.serviceList.includes('engine-mount') : false,
                })}
              >
                엔진 미션 마운트
              </span>
              <span
                className={classNames({
                  selected: reservInfo ? reservInfo.serviceList.includes('suspension') : false,
                })}
              >
                서스펜션 뉴계토우 점검
              </span>
            </div>
            <div className={classNames('content__line')}>
              <span className={classNames({ selected: reservInfo ? reservInfo.serviceList.includes('shaft') : false })}>
                스테빌라이저 및 드라이버 샤프트
              </span>
              <span
                className={classNames({ selected: reservInfo ? reservInfo.serviceList.includes('scanner') : false })}
              >
                스캐너 고장코드 진단
              </span>
            </div>
            <div className={classNames('content__line')}>
              <span
                className={classNames({ selected: reservInfo ? reservInfo.serviceList.includes('heater') : false })}
              >
                에어컨 및 히터 작동 생태
              </span>
              <span className={classNames({ selected: reservInfo ? reservInfo.serviceList.includes('tire') : false })}>
                타이어 공기압 및 마모도
              </span>
              <span
                className={classNames({ selected: reservInfo ? reservInfo.serviceList.includes('filter') : false })}
              >
                에어컨 필터
              </span>
            </div>
          </div>
        </div>

        <div className={classNames('row')}>
          <div className={classNames('row--title')}>요청 사항</div>
          <span>{reservInfo ? reservInfo.request : '에어컨이 고장났어요'}</span>
        </div>

        <Link to="/mypage" className={classNames('button')}>
          예약내역 확인하기
        </Link>
      </div>
      <Footer />
    </>
  );
}
