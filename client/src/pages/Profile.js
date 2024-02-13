import classNames from 'classnames';
import Footer from '../components/Footer';
import Header from '../components/Header';
import Modal from '../components/Modal';
import { useState } from 'react';

export default function Profile() {

  const [isModalOpen, setIsModalOpen] = useState(false);

  const openModal = () => {
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
  };

  return (
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
            <span className={classNames('value')}>한수아</span>
          </div>
          <div className={classNames('profile-row')}>
            <span className={classNames('key')}>이메일</span>
            <span className={classNames('value')}>s2sooey@gmail.com</span>
          </div>
          <div className={classNames('profile-row')}>
            <span className={classNames('key')}>생년월일</span>
            <span className={classNames('value')}>1999.05.26</span>
          </div>
          <div className={classNames('profile-row')}>
            <span className={classNames('key')}>연락처</span>
            <input className={classNames('input')}></input>
          </div>
          <div className={classNames('profile-row')}>
            <span className={classNames('key')}>보유 차량</span>
            <div className={classNames('car-list')}>
              <div className={classNames('car')}>
                <div className={classNames('car-frame')}>
                  <span className={classNames('car-name')}>Genesis G80</span>
                  <span className={classNames('car-number')}>12가 3456</span>
                </div>
              </div>
              <div className={classNames('car')}>
                <div className={classNames('car-frame')}>
                  <span className={classNames('car-name')}>Genesis G80</span>
                  <span className={classNames('car-number')}>12가 3456</span>
                </div>
              </div>
              <div className={classNames('add')} onClick={openModal}>
                <span className={classNames('text')}>차량 추가하기</span>
              </div>
            </div>
          </div>
        </div>
        <div className={classNames('buttons')}>
          <div className={classNames('btn-white')}>
            <span className={classNames('text')}>취소하기</span>
          </div>
          <div className={classNames('btn-orange')}>
            <span className={classNames('text')}>저장하기</span>
          </div>
        </div>
      </div>
      <Footer />

      {isModalOpen && <Modal onClose={closeModal}/>}
    </div>
  );
}
