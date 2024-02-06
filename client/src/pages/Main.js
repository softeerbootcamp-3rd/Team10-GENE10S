import { Link } from 'react-router-dom';
import Header from '../components/Header';
import Footer from '../components/Footer';
import homeBanner from '../assets/home-banner.png';
import classNames from 'classnames';

export default function Main() {
  return (
    <>
      <Header />
      <div className={classNames('content')}>
        <img src={homeBanner} alt="banner" />
        <div className={classNames('banner-button')}>
          <Link to="/bot" className={classNames('banner-button__button-left')}>
            예약하기
          </Link>
          <Link
            to="/reservation"
            className={classNames('banner-button__button-right')}
          >
            예약봇
          </Link>
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
