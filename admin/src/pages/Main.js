import classNames from "classnames";
import BtnDark from "../components/BtnDark";
import axios from "../api/Settings";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

export default function Main() {
  const [adminId, setAdminId] = useState("");
  const [adminPwd, setAdminPwd] = useState("");
  const [loginFailed, setLoginFailed] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    checkSession();
  }, []);

  const checkSession = async () => {
    axios
      .get("/v2/admin/account/session-validation")
      .then((response) => {
        if (response.data && response.data.success) {
          navigate("/reservation");
        }
      })
      .catch((error) => {
        console.error("Error checking session:", error);
      });
  };

  const handleAdminIdChange = (event) => {
    setAdminId(event.target.value);
  };

  const handleAdminPasswordChange = (event) => {
    setAdminPwd(event.target.value);
  };

  const handleLoginClick = () => {
    axios
      .post("/v2/admin/account/login", { adminId, adminPwd })
      .then((response) => {
        if (response.data.success) {
          navigate("/reservation");
        } else {
          setLoginFailed(true);
        }
      })
      .catch((error) => {
        console.error("로그인 오류: ", error);
      });
  };

  return (
    <div className={classNames("login-page")}>
      <div className={classNames("login-frame")}>
        <div className={classNames("title")}>
          <div className={classNames("text")}>Genesis Airport</div>
          <div className={classNames("text")}>Admin</div>
        </div>

        <div className={classNames("body")}>
          <div className={classNames("content")}>
            <div className={classNames("input-container")}>
              <div className={classNames("text")}>아이디</div>
              <input
                type="text"
                className={classNames("input-area")}
                onChange={handleAdminIdChange}
              />
            </div>
            <div className={classNames("input-container")}>
              <div className={classNames("text")}>비밀번호</div>
              <input
                type="password"
                className={classNames("input-area")}
                onChange={handleAdminPasswordChange}
              />
            </div>
            <BtnDark text="로그인" onClick={handleLoginClick} />
            {loginFailed && (
              <div style={{ color: "red" }}>로그인에 실패했습니다.</div>
            )}{" "}
            {/* 에러 메시지 표시 */}
          </div>
        </div>
      </div>
    </div>
  );
}
