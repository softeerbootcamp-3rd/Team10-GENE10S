import { ProgressArrow200 } from './Arrow';
import logo_facebook from '../assets/logo-facebook.png';
import logo_x from '../assets/logo-x.png';
import logo_link from '../assets/logo-link.png';
import logo_footer from '../assets/logo-footer.png';

export default function Footer() {
  return (
    <div className="footer">
      <div className="footer-bar">
        <div className="footer-btn">
          <div className="text-area">
            <span className="footer-text">공유하기</span>
          </div>
          <img className="link" src={logo_facebook} alt="" />
          <img className="link" src={logo_x} alt="" />
          <img className="link" src={logo_link} alt="" />
        </div>
        <div className="footer-btn">
          <div className="text-area">
            <span className="footer-text">TOP</span>
          </div>
          <div className="arrow-up">
            <ProgressArrow200 />
          </div>
        </div>
      </div>
      <div className="footer-copyright">
        <img className="link" src={logo_footer} alt="" />
        <div className="image" />
        <div className="text">
          <span>© Copyright 2024 Softeer Bootcamp Gene10s.</span>
          <span>All Rights Reserved.</span>
        </div>
      </div>
    </div>
  );
}
