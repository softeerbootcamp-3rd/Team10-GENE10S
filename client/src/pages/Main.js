import { Link } from 'react-router-dom';
import Header from '../components/Header';
import Footer from '../components/Footer';
import homeBanner from '../assets/home-banner.png';
import classNames from 'classnames';
import { useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import axios from '../api/Settings';
import Cookies from 'js-cookie';

export default function Main() {
  const location = useLocation();

  useEffect(() => {
    async function fetchTokenAndUserData(code, state) {
      try {
        const { data } = await axios.post('v1/oauth/token', {
          grantType: 'authorization_code',
          code,
          redirectUri: process.env.REACT_APP_REDIRECT_URI,
          clientId: process.env.REACT_APP_CLIENT_ID,
          clientSecret: process.env.REACT_APP_CLIENT_SECRET,
        });

        const sid = data.sid;
        const expires = new Date();
        expires.setTime(expires.getTime() + 10 * 60 * 1000);

        Cookies.set('sid', sid, { expires });

        const userInfoResponse = await axios.post('v1/oauth/user', { sid });
        console.log('Resource server response:', userInfoResponse.data);

        //window.location.href = 'http://localhost:3000';
      } catch (error) {
        console.error(
          'Error during authentication or fetching user data:',
          error,
        );
      }
    }

    const queryParams = new URLSearchParams(location.search);
    const code = queryParams.get('code');
    const state = queryParams.get('state');

    if (code && state) {
      fetchTokenAndUserData(code, state);
    }
  }, [location]);

  return (
    <>
      <Header />
      <div className={classNames('content')}>
        <img src={homeBanner} alt="Home banner" />
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
