import classNames from "classnames";
import SideBar from "../components/SideBar";
import { useEffect, useState } from "react";
import { getAccountList } from "../api/AccountApi";
import Pagination from "../components/Pagination";
import { useLocation } from "react-router-dom";

export default function Account() {
  const location = useLocation();
  const [accountList, setAccountList] = useState([]);

  const [userId, setUserId] = useState("");
  const [userName, setUserName] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");

  const [page, setPage] = useState(1);
  const [size, setSize] = useState(10);
  const [totalElements, setTotalElements] = useState(100);
  const [totalPages, setTotalPages] = useState(10); // dummy

  useEffect(() => {
    if (location.state && location.state.page) {
      setPage(location.state.page);
    }
  }, [location]);

  const search = () => {
    getAccountList({
      adminId: userId,
      adminName: userName,
      phoneNumber: phoneNumber,
    }).then((response) => {
      setAccountList(response.data.data);

      setPage(response.data.pageInfoDto.page);
      setSize(response.data.pageInfoDto.size);
      setTotalElements(response.data.pageInfoDto.totalElements);
      setTotalPages(response.data.pageInfoDto.totalPages);
    });
  };

  const accountElements = accountList.map((account) => (
    <div className={classNames("tr")}>
      <div className={classNames("td w-100")}>
        <span>{account.id}</span>
      </div>
      <div className={classNames("td w-250")}>
        <span>{account.adminId}</span>
      </div>
      <div className={classNames("td w-300")}>
        <span>{account.adminName}</span>
      </div>
      <div className={classNames("td w-200")}>
        <span>{account.phoneNumber}</span>
      </div>
      <div className={classNames("td w-350")}>
        <span>{account.createDateTime}</span>
      </div>
      <div className={classNames("delete-button")}>
        <div className={classNames("btn-light")}>
          <a>삭제</a>
        </div>
      </div>
    </div>
  ));

  return (
    <div className={classNames("page")}>
      <SideBar currentPage={"reservation"} />
      <div className={classNames("body")}>
        <div className={classNames("title")}>
          <span>관리자 관리</span>
        </div>
        <div className={classNames("content")}>
          <div className={classNames("search-area")}>
            <div className={classNames("search-row")}>
              <div className={classNames("search-item")}>
                <span>아이디</span>
                <input type="text" onChange={setUserId} />
              </div>
              <div className={classNames("search-item")}>
                <span>이름</span>
                <input type="text" onChange={setUserName} />
              </div>
              <div className={classNames("search-item")}>
                <span>전화번호</span>
                <input type="text" onChange={setPhoneNumber} />
              </div>
              <div className={classNames("btn-dark")}>
                <a onClick={() => search()}>검색</a>
              </div>
            </div>
          </div>

          <div className={classNames("register-area")}>
            <div className={classNames("btn-dark")}>
              <a>등록</a>
            </div>
          </div>

          <div className={classNames("table")}>
            <div className="th">
              <div className="td w-100">
                <span>No</span>
              </div>
              <div className="td w-250">
                <span>아이디</span>
              </div>
              <div className="td w-300">
                <span>이름</span>
              </div>
              <div className="td w-200">
                <span>전화번호</span>
              </div>
              <div className="td w-350">
                <span>가입일</span>
              </div>
              <div className="td w-150 h-52">
                <span></span>
              </div>
            </div>
            {accountElements}
          </div>

          <div className={classNames("paginate")}>
            <Pagination
              PageInfo={{ page, size, totalElements, totalPages }}
              paginationSize={5}
            />
          </div>
        </div>
      </div>
    </div>
  );
}
