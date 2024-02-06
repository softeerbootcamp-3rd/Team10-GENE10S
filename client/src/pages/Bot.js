import classNames from 'classnames';
import Footer from '../components/Footer';
import Header from '../components/Header';

export default function Bot() {
  return (
    <>
      <Header />
      <div className={classNames('content')}>Bot</div>
      <Footer />
    </>
  );
}
