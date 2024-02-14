import classNames from 'classnames';
import Footer from '../components/Footer';
import Header from '../components/Header';
import axios from 'axios';
import { useEffect, useState } from 'react';
import ProfileCard from '../components/ProfileCard';
import ReservationCard from '../components/ReservationCard';

export default function Mypage() {
  const [profileInfo, setProfileInfo] = useState({});
  const [carList, setCarList] = useState([]);
  const [reservationInfo, setReservationList] = useState([]);

  const [currentMenu, setCurrentMenu] = useState(true); // true: 내정보, false: 예약내역
  const [isProfileHovered, setIsProfileHovered] = useState(false);
  const [isReservationHovered, setIsReservationHovered] = useState(false);


  useEffect(() => {
    axios.get('v1/user/profile')
      .then ((response) => setProfileInfo(response.data.data))
      .catch ((error) => console.log("Error message :", error))

    axios.get('v1/reservation/car-list')
      .then ((response) => setCarList(response.data.data))

    axios.get('v1/reservation/list')
      .then ((response) => setReservationList(response.data.data))
  }, [])

  const handleMenuClick = (menu) => {
    setCurrentMenu(menu);
    window.scrollTo(0, 0); // 메뉴를 선택할 때마다 페이지의 맨 위로 스크롤 이동
  };
  
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
            <div className={classNames({
              'menu-item-active': currentMenu || isProfileHovered,
              'menu-item-deactive': !currentMenu && !isProfileHovered
              })}>
                <div className={classNames('menu-name')}
                  onClick={() => handleMenuClick(true)} // 내 정보 메뉴 클릭 시 currentMenu를 true로 설정
                  onMouseEnter={() => setIsProfileHovered(true)}
                  onMouseLeave={() => setIsProfileHovered(false)}
                >
                  내 정보
                </div>
              </div>
              <div className={classNames({
                'menu-item-active': !currentMenu || isReservationHovered,
                'menu-item-deactive': currentMenu && !isReservationHovered
                })}>
                <div 
                  className={classNames('menu-name')}
                  onClick={() => handleMenuClick(false)} // 예약 내역 메뉴 클릭 시 currentMenu를 false로 설정
                  onMouseEnter={() => setIsReservationHovered(true)}
                  onMouseLeave={() => setIsReservationHovered(false)}
                >
                  예약 내역
                </div>
              </div>
            </div>
            { currentMenu ? <ProfileCard profileInfo={profileInfo} carList={carList} /> : <ReservationCard reservationInfo={reservationInfo}/> }
          </div>
        </div>
      </div>
      <Footer />
    </>
  );
}
