import { Link } from 'react-router-dom';
import classNames from 'classnames';
import logo from '../assets/logo.png';

export default function Header() {
  return (
    <div className={classNames('header')}>
      <div className={classNames('header-logo')}>
        <img src={logo} alt="logo" />
      </div>
      <div className={classNames('header-button')}>
        <Link to="/login" className={classNames('header-button__button')}>
          로그인
        </Link>
        <Link to="/join" className={classNames('header-button__button')}>
          회원가입
        </Link>
      </div>
    </div>
  );
}
