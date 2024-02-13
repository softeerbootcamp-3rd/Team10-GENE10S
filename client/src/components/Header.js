import { Link } from 'react-router-dom';
import classNames from 'classnames';
import logo_header from '../assets/logo-header.png';
import Cookies from 'js-cookie';
import { useEffect, useState } from 'react';

export default function Header() {
  const [isLogin, setIsLogin] = useState(false);
  const [uuid, setUuid] = useState('');
  const clientId = process.env.REACT_APP_CLIENT_ID;
  const host = process.env.REACT_APP_REDIRECT_URI;

  // RFC4122 version 4 UUID
  useEffect(() => {
    setUuid(createUUID());
    setIsLogin(!!Cookies.get('sid'));

    const header = document.querySelector('.header');
    window.addEventListener('scroll', () => {
      if (window.scrollY === 0 && !header.classList.value.includes('on-top')) {
        header.classList.add('on-top');
      } else if (window.scrollY !== 0 && header.classList.value.includes('on-top')) {
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

  return (
    <div className={classNames('header', 'on-top')}>
      <Link to="/">
        <img className="logo" src={logo_header} alt="logo" />
      </Link>
      <div className="btn-group">
        {isLogin ? (
          <>
            <Link to="/logout" className={classNames('btn')}>
              로그아웃
            </Link>
            <Link to="/mypage" className={classNames('btn')}>
              마이페이지
            </Link>
          </>
        ) : (
          <>
            <a
              href={`https://accounts.genesis.com/api/authorize/ccsp/oauth?clientId=${clientId}&host=${host}&state=${uuid}`}
              className={classNames('btn')}
            >
              로그인
            </a>

            <Link to="/join" className={classNames('btn')}>
              회원가입
            </Link>
          </>
        )}
      </div>
    </div>
  );
}
