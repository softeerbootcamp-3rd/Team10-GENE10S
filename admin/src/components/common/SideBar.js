import classNames from "classnames";
import ProfileImage from "../svg/ProfileImage";
import MenuImage from "../svg/MenuImage";
import { Link, useNavigate } from "react-router-dom";
import LogoutImage from "../svg/LogoutImage";
import axios from "../../api/Settings";
import { useState } from "react";

// currentPage: 'reservation', 'shop', 'account' 중 한 개로 설정해야 함
export default function SideBar({ currentPage }) {
  const navigate = useNavigate();
  const [userName] = useState(localStorage.getItem("userName") || "");

  const handleLogoutClick = async () => {
    try {
      await axios.post("/v2/admin/account/logout").then(() => {
        navigate("/");
      });
    } catch (error) {
      console.error("Error calling logout API:", error);
    }
  };

  return (
    <div className={classNames("side-menu")}>
      <Link to='/reservation' className={classNames("title")}>
        <span>Genesis Airport</span>
      </Link>
      <div className={classNames("profile")}>
        <div className={classNames("profile-info")}>
          <ProfileImage />
          <span>{userName}</span>
        </div>
        <Link
          to='/'
          className={classNames("logout")}
          onClick={handleLogoutClick}
        >
          <LogoutImage />
        </Link>
      </div>
      <div className={classNames("menu-list")}>
        <Link
          to='/reservation'
          className={classNames("menu", {
            active: currentPage === "reservation",
          })}
        >
          <MenuImage />
          <span>예약 관리</span>
        </Link>
        <Link
          to='/shop'
          className={classNames("menu", { active: currentPage === "shop" })}
        >
          <MenuImage />
          <span>정비소 관리</span>
        </Link>
        <Link
          to='/account'
          className={classNames("menu", { active: currentPage === "account" })}
        >
          <MenuImage />
          <span>관리자 계정 관리</span>
        </Link>
      </div>
    </div>
  );
}
