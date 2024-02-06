import classNames from 'classnames';
import Footer from '../components/Footer';
import Header from '../components/Header';

export default function Mypage() {
  return (
    <>
      <Header />
      <div className={classNames('content')}>Mypage</div>
      <Footer />
    </>
  );
}
