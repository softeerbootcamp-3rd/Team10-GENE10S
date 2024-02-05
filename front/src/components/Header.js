import { Link } from 'react-router-dom';

export default function Header() {
  return (
    <>
      <div>Header</div>
      <Link to="/login">login</Link>
    </>
  );
}
