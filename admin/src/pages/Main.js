import classNames from 'classnames';
import BtnDark from '../components/Button';

export default function Main() {
  return (
    <div className={classNames('login-page')}>
      <div className={classNames('login-frame')}>
        <div className={classNames('title')}>
          <div className={classNames('text')}>
            Genesis Airport
          </div>
          <div className={classNames('text')}>
            Admin
          </div>
        </div>

        <div className={classNames('body')}>
          <div className={classNames('content')}>
            <div className={classNames('input-container')}>
              <div className={classNames('text')}>
                아이디
              </div>
              <input type='text' className={classNames('input-area')}/>
            </div>
            <div className={classNames('input-container')}>
              <div className={classNames('text')}>
                비밀번호
              </div>
              <input type='password' className={classNames('input-area')}/>
            </div>
            <div className={classNames('btn-dark')}>
              <BtnDark text='로그인'/>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
