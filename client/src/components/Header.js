import { Link } from 'react-router-dom';
import classNames from 'classnames';
import logo_header from '../assets/logo-header.png';
import Cookies from 'js-cookie';
import { useEffect, useState } from 'react';
import axios from 'axios';

export default function Header() {
  const [isLogin, setIsLogin] = useState(false);
  const [uuid, setUuid] = useState('');
  const clientId = process.env.REACT_APP_CLIENT_ID;
  const host = process.env.REACT_APP_REDIRECT_URI;

  // RFC4122 version 4 UUID
  useEffect(() => {
    setUuid(createUUID());

    const sid = Cookies.get('SID');
    if (sid) {
      setIsLogin(true);
    }

    const header = document.querySelector('.header');
    const page = document.querySelector('#root').querySelector('div');
    page.addEventListener('scroll', () => {
      if (page.scrollTop === 0 && !header.classList.value.includes('on-top')) {
        header.classList.add('on-top');
      } else if (page.scrollTop !== 0 && header.classList.value.includes('on-top')) {
        header.classList.remove('on-top');
      }
    });
  }, []);

  function createUUID() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
      let r = (Math.random() * 16) | 0,
        v = c === 'x' ? r : (r & 0x3) | 0x8;
      return v.toString(16);
    });
  }

  // TODO 로그아웃 클릭 시 윈도우 재로딩 필요
  const handleLogoutClick = async () => {
    try {
      await axios.post('/v1/logout');
      window.location.reload();
    } catch (error) {
      console.error('Error calling logout API:', error);
    }
  };

  return (
    <div className={classNames('header', 'on-top')}>
      <Link to="/">
        <img className="logo" src={logo_header} alt="logo" />
      </Link>
      <div className="btn-group">
        {isLogin ? (
          <>
            <Link to="/" className={classNames('btn')} onClick={handleLogoutClick}>
              로그아웃
            </Link>
            <Link to="/mypage" className={classNames('btn')}>
              마이페이지
            </Link>
          </>
        ) : (
          <a
            href={`https://accounts.genesis.com/api/authorize/ccsp/oauth?clientId=${clientId}&host=${host}&state=${uuid}`}
            className={classNames('btn')}
          >
            로그인
          </a>
        )}
      </div>
    </div>
  );
}
