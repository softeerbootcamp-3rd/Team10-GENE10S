import classNames from 'classnames';
import Footer from '../components/Footer';
import Header from '../components/Header';

export default function Reservation() {
  return (
    <>
      <Header />
      <div className={classNames('content')}>Reservation</div>
      <Footer />
    </>
  );
}
