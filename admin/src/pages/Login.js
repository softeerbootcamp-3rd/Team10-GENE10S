import classNames from "classnames";
import BtnDark from "../components/button/BtnDark";
import axios from "../api/Settings";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useRecoilState } from "recoil";
import { userNameState } from "../util/states";
import { callLogin } from "../api/Common";

export default function Login() {
  const [userName, setUserName] = useRecoilState(userNameState);

  const [adminId, setAdminId] = useState("");
  const [adminPwd, setAdminPwd] = useState("");
  const [loginFailed, setLoginFailed] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    checkSession();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const checkSession = () => {
    axios
      .get("/v2/admin/account/session-validation")
      .then((response) => {
        if (response.data && response.data.data) {
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
    callLogin(adminId, adminPwd).then((response) => {
      if (response.success) {
        setUserName(response.data.adminName);
        navigate("/reservation");
      } else {
        setLoginFailed(true);
      }
    });
  };

  return (
    <div className={classNames("page")}>
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
                type='text'
                className={classNames("input-area")}
                onChange={handleAdminIdChange}
              />
            </div>
            <div className={classNames("input-container")}>
              <div className={classNames("text")}>비밀번호</div>
              <input
                type='password'
                className={classNames("input-area")}
                onChange={handleAdminPasswordChange}
              />
            </div>
            <BtnDark onClick={handleLoginClick}>로그인</BtnDark>
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
