import classNames from 'classnames';
import Footer from '../components/Footer';
import Header from '../components/Header';

export default function NotFound() {
  return (
    <>
      <Header />
      <div className={classNames('content')}>Not Found</div>
      <Footer />
    </>
  );
}
