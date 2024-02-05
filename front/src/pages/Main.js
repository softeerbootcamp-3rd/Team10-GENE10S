import Header from '../components/Header';
import { connenctionTest } from '../services/connectionTest';

export default function Main() {
  return (
    <>
      <Header />
      <div>메인 페이지</div>
    </>
  );
}

function test() {
  connenctionTest();
}

test();
