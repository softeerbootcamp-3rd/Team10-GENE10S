import classNames from 'classnames';
import Footer from '../components/Footer';
import Header from '../components/Header';

export default function Mypage() {
  return (
    <>
      <Header />

      <div className={classNames('page')}>
        <div className={classNames('title-area')}>
          <div className={classNames('image')}/>
          <div className={classNames('title-fixed')}>
              <div className={classNames('title-text')}>
                My Page
              </div>
            </div>
        </div>

        {/* 페이지 내용 */}
        <div className={classNames('body')}>
          <div className={classNames('menu-frame')}>
            {/* 마이페이지 메뉴 */}
            <div className={classNames('side-menu')}>
              <div className={classNames('menu-item-active')}>
                <div className={classNames('menu-name')}>
                  내 정보
                </div>
              </div>
              <div className={classNames('menu-item-deactive')}>
                <div className={classNames('menu-name')}>
                  예약 내역
                </div>
              </div>
            </div>
            <div className={classNames('content')}>
              <div className={classNames('profile-view')}>
                <div className={classNames('profile-image')} />
                <div className={classNames('profile-info')}>
                  <div className={classNames('name')}>
                    <div className={classNames('text')}>
                      김주환
                    </div>
                    <div className={classNames('setting')} />
                  </div>
                  <div className={classNames('email')}>rlawnghks99@gmail.com</div>
                  <div className={classNames('cars')}>
                    {/* 차량 리스트 불러와서 컴포넌트로 출력해야함 */}
                    <div className={classNames('car-name')}>
                      <div className={classNames('text')}>Genesis G80</div>
                    </div>
                    <div className={classNames('car-name')}>
                      <div className={classNames('text')}>Genesis GV80</div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <Footer />
    </>
  );
}
