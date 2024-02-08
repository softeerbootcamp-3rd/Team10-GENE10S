import { Link } from 'react-router-dom';
import classNames from 'classnames';
import logo from '../assets/logo.png';
import Cookies from 'js-cookie';
import { useEffect, useState } from 'react';

export default function Header() {
  const [isLogin, setIsLogin] = useState(false);
  const [uuid, setUuid] = useState('');
  const clientId = process.env.REACT_APP_CLIENT_ID;
  const host = process.env.REACT_APP_REDIRECT_URI;

  console.log('clientId:', clientId);
  console.log('host:', host);

  // RFC4122 version 4 UUID
  useEffect(() => {
    setUuid(createUUID());
    setIsLogin(!!Cookies.get('sid'));
  }, []);

  function createUUID() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(
      /[xy]/g,
      function (c) {
        let r = (Math.random() * 16) | 0,
          v = c === 'x' ? r : (r & 0x3) | 0x8;
        return v.toString(16);
      },
    );
  }

  return (
    <div className={classNames('header')}>
      <div className={classNames('header-logo')}>
        <img src={logo} alt="logo" />
      </div>
      <div className={classNames('header-button')}>
        {isLogin ? (
          <>
            <Link to="/logout" className={classNames('header-button__button')}>
              로그아웃
            </Link>
            <Link to="/mypage" className={classNames('header-button__button')}>
              마이페이지
            </Link>
          </>
        ) : (
          <>
            <a
              href={`https://accounts.genesis.com/api/authorize/ccsp/oauth?clientId=${clientId}&host=${host}&state=${uuid}`}
              className={classNames('header-button__button')}
            >
              로그인
            </a>

            <Link to="/join" className={classNames('header-button__button')}>
              회원가입
            </Link>
          </>
        )}
      </div>
    </div>
  );
}
