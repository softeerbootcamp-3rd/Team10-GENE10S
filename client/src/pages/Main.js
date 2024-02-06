import { Link } from 'react-router-dom';
import Header from '../components/Header';
import Footer from '../components/Footer';
import classNames from 'classnames';

export default function Main() {
  return (
    <>
      <Header />
      <div className={classNames('content')}>
        <div className={classNames('banner')}>
          <div className={classNames('banner-button-block')}>
            <div className={classNames('banner-button-block__inner-block')}>
              <Link
                to="/reservation"
                className={classNames('banner-button-block__button-left')}
              >
                예약하기
              </Link>
              <Link
                to="/bot"
                className={classNames('banner-button-block__button-right')}
              >
                예약봇
              </Link>
            </div>
          </div>
        </div>
      </div>
      <Footer />
    </>
  );
}

// function test() {
//   testConnectionApi();
// }

// test();
