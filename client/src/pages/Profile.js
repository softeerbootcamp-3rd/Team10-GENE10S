import classNames from 'classnames';
import Footer from '../components/Footer';
import Header from '../components/Header';
import Modal from '../components/Modal';
import { useEffect, useState } from 'react';
import axios from '../api/Settings';
import image from '../assets/image.png';
import { deleteCar } from '../api/CarApi';

export default function Profile() {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [profileInfo, setProfileInfo] = useState({});
  const [carList, setCarList] = useState([]);

  const [userName, setUserName] = useState('');
  const [birthdate, setBirthdate] = useState('');
  const [phoneNumber, setPhoneNumber] = useState('');

  const handleUserNameChange = event => {
    setUserName(event.target.value);
  };

  const handleBirthdateChange = event => {
    setBirthdate(event.target.value);
  };

  const handlePhoneNumberChange = event => {
    setPhoneNumber(event.target.value);
  };

  const handleDeleteCarClick = carId => {
    deleteCar(carId)
      .then(result => {
        alert('차량이 성공적으로 삭제되었습니다.');
        window.location.reload();
      })
      .catch(error => {
        console.error('Error deleting car:', error);
      });
  };

  const handleUpdateClick = () => {
    const requestBody = {
      name: userName,
      birthdate: birthdate,
      phoneNumber: phoneNumber,
    };

    try {
      axios.patch('v1/user/info', requestBody, {
        headers: {
          'Content-Type': 'application/json',
        },
      });

      window.location.href = '/mypage';
    } catch (error) {
      console.error('Error updating user information:', error);
    }
  };

  useEffect(() => {
    axios
      .get('v1/user/info')
      .then(response => setProfileInfo(response.data.data))
      .catch(error => console.log('Error message :', error));

    axios.get('v1/reservation/car-list').then(response => setCarList(response.data.data));
  }, []);

  const carElements = carList.map((car, index) => (
    <div className={classNames('car-sub-frame')} key={index}>
      <div className={classNames('car')}>
        <img className={classNames('car-image')} src={image} alt="차량 이미지" />
        <div className={classNames('car-info')}>
          <span className={classNames('car-name')}>{car.sellName}</span>
          <span className={classNames('car-number')}>{car.plateNumber}</span>
        </div>
        <button className={classNames('icon')} onClick={() => handleDeleteCarClick(car.carId)}>
          {' '}
        </button>
      </div>
    </div>
  ));

  const openModal = () => {
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
  };

  return (
    <>
      <div className={classNames('page')}>
        <Header />
        <div className={classNames('body')}>
          <div className={classNames('title')}>
            <span className={classNames('title-mini')}>마이페이지 &gt; 내 정보</span>
            <span className={classNames('title-big')}>내 정보 수정</span>
          </div>
          <div className={classNames('profile-info')}>
            <div className={classNames('profile-row')}>
              <span className={classNames('key')}>이름</span>
              <input
                className={classNames('input')}
                placeholder={profileInfo.name}
                value={userName}
                onChange={handleUserNameChange}
              />
            </div>
            <div className={classNames('profile-row')}>
              <span className={classNames('key')}>이메일</span>
              <span className={classNames('value')}>{profileInfo.email}</span>
            </div>
            <div className={classNames('profile-row')}>
              <span className={classNames('key')}>생년월일</span>
              <input
                className={classNames('input')}
                placeholder={profileInfo.birthdate}
                value={birthdate}
                onChange={handleBirthdateChange}
              />
            </div>
            <div className={classNames('profile-row')}>
              <span className={classNames('key')}>연락처</span>
              <input
                className={classNames('input')}
                placeholder={profileInfo.phoneNumber}
                value={phoneNumber}
                onChange={handlePhoneNumberChange}
              />
            </div>
            <div className={classNames('car-list')}>
              <span className={classNames('key')}>보유 차량</span>
              <div className={classNames('car-frame')}>
                {carElements}
                <div className={classNames('add')} onClick={openModal}>
                  <span className={classNames('text')}>차량 추가하기</span>
                </div>
              </div>
            </div>
          </div>
          <div className={classNames('buttons')}>
            <div className={classNames('btn', 'custom')} onClick={() => (window.location.href = '/mypage')}>
              <span className={classNames('text')}>취소하기</span>
            </div>
            <div className={classNames('btn', 'custom')} onClick={handleUpdateClick}>
              <span className={classNames('text')}>저장하기</span>
            </div>
          </div>
        </div>
        <Footer />
      </div>
      <Modal onClose={closeModal} visible={isModalOpen} />
    </>
  );
}
