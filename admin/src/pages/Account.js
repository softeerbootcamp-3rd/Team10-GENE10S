import classNames from "classnames";
import SideBar from "../components/SideBar";
import { useEffect, useState } from "react";
import { getAccountList } from "../api/AccountApi";
import Pagination from "../components/Pagination";
import BtnDark from "../components/BtnDark";

export default function Account() {
  const [accountList, setAccountList] = useState([]);

  const [userId, setUserId] = useState("");
  const [userName, setUserName] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");
  const [sortColumn, setSortColumn] = useState("id");
  const [sortDirection, setSortDirection] = useState("desc");

  const [page, setPage] = useState(1);
  const [totalPages, setTotalPages] = useState(1);

  const search = (pageNumber = 1) => {
    console.log("sortColumn", sortColumn);
    console.log("sortDirection", sortDirection);
    getAccountList({
      adminId: userId,
      adminName: userName,
      phoneNumber: phoneNumber,
      sortColumn: sortColumn,
      sortDirection: sortDirection,
      page: pageNumber - 1,
    }).then((response) => {
      setAccountList(response.data.data);
      setPage(response.data.pageInfo.page + 1);
      setTotalPages(response.data.pageInfo.totalPages);
    });
  };

  useEffect(() => {
    search(page);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [sortDirection, sortColumn]);

  const handleSort = (columnName) => {
    if (sortColumn === columnName) {
      setSortDirection(sortDirection === "desc" ? "asc" : "desc");
    } else {
      setSortColumn(columnName);
      setSortDirection("asc");
    }
  };

  const renderArrow = (columnName) => {
    if (sortColumn === columnName) {
      return sortDirection === "asc" ? (
        <svg
          width="14"
          height="11"
          viewBox="0 0 10 10"
          fill="#000000"
          xmlns="http://www.w3.org/2000/svg"
        >
          <path d="M5 1L1 9H9L5 1Z" fill="#000000" />
        </svg>
      ) : (
        <svg
          width="14"
          height="11"
          viewBox="0 0 10 10"
          fill="#000000"
          xmlns="http://www.w3.org/2000/svg"
        >
          <path d="M5 9L9 1H1L5 9Z" fill="#000000" />
        </svg>
      );
    }
    return (
      <svg
        width="14"
        height="11"
        viewBox="0 0 10 10"
        fill="#999999"
        xmlns="http://www.w3.org/2000/svg"
      >
        <path d="M5 9L9 1H1L5 9Z" fill="#999999" />
      </svg>
    );
  };

  useEffect(() => {
    search();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const accountElements = accountList.map((account, index) => (
    <div className={classNames("tr")} key={index}>
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
          <span>삭제</span>
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
                <input
                  type="text"
                  onChange={(event) => setUserId(event.target.value)}
                />
              </div>
              <div className={classNames("search-item")}>
                <span>이름</span>
                <input
                  type="text"
                  onChange={(event) => setUserName(event.target.value)}
                />
              </div>
              <div className={classNames("search-item")}>
                <span>전화번호</span>
                <input
                  type="text"
                  onChange={(event) => setPhoneNumber(event.target.value)}
                />
              </div>
              <BtnDark onClick={() => search()} text={"검색"} />
            </div>
          </div>

          <div className={classNames("register-area")}>
            <div className={classNames("btn-dark")}>
              <span>등록</span>
            </div>
          </div>

          <div className={classNames("table")}>
            <div className="th">
              <div className="td w-100" onClick={() => handleSort("id")}>
                <span>No {renderArrow("id")}</span>
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
              <div
                className="td w-350"
                onClick={() => handleSort("createDatetime")}
              >
                <span>가입일 {renderArrow("createDatetime")}</span>
              </div>
              <div className="td w-150 h-52">
                <span></span>
              </div>
            </div>
            {accountElements}
          </div>

          <div className={classNames("paginate")}>
            <Pagination
              page={page}
              totalPages={totalPages}
              paginationSize={5}
              onChange={search}
            />
          </div>
        </div>
      </div>
    </div>
  );
}
