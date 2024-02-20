import classNames from 'classnames';
import { ProfileImage } from './svg/ProfileImage';
import { MenuImage } from './svg/MenuImage';
import { userNameState } from '../util/states';
import { useRecoilState } from 'recoil';

// currentPage: 'reservation', 'shop', 'account' 중 한 개로 설정해야 함
export default function SideBar({ currentPage }) {

  const [userName, setUserName] = useRecoilState(userNameState);

  return (
    <div className={classNames('side-menu')}>
      <div className={classNames('title')}>
        <span>Genesis Airport</span>
      </div>
      <div className={classNames('profile')}>
        <ProfileImage/>
        <span>{userName}</span>
      </div>
      <div className={classNames('menu-list')}>
        <Link to="/reservation" className={(classNames('menu', {'active': currentPage === 'reservation'}))}>
          <MenuImage />
          <span>예약 관리</span>
        </Link>
        <Link to="/shop" className={(classNames('menu', {'active': currentPage === 'shop'}))}>
          <MenuImage />
          <span>정비소 관리</span>
        </Link>
        <Link to="/account" className={(classNames('menu', {'active': currentPage === 'account'}))}>
          <MenuImage />
          <span>관리자 계정관리</span>
        </Link>
      </div>
    </div>
  );
}
