import classNames from 'classnames';
import Footer from '../components/Footer';
import Header from '../components/Header';
import axios from 'axios';
import { useEffect, useState } from 'react';

export default function Mypage() {
  const image_url = "https://genesis-airport.s3.ap-northeast-2.amazonaws.com/car/g80.png"

  const [profileInfo, setProfileInfo] = useState({});
  const [carList, setCarList] = useState([]);

  useEffect(() => {
    axios.get('v1/user/profile')
      .then ((response) => {
        console.log("Received Profile Data: ", response.data.data);
        setProfileInfo(response.data.data);
      })
      .catch ((error) => console.log("Error message :", error))

    axios.get('v1/reservation/car-list')
      .then ((response) => {
        console.log("Received car-list : ", response.data.data);
        setCarList(response.data.data);
      })
  }, [])
  
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
                      {profileInfo.name}
                    </div>
                    <div className={classNames('setting')} />
                  </div>
                  <div className={classNames('email')}>{profileInfo.imageUrl}</div>
                  <div className={classNames('cars')}>
                    {carList.map((car, index) => (
                    <div key={index} className={classNames('car-name')}>
                      <div className={classNames('text')}>{car.sellName}</div>
                    </div>
                  ))}
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
