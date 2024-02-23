import classNames from "classnames";
import SideBar from "../components/SideBar";
import CustomCalendar from "../components/CustomCalendar";
import React, { useState, useEffect } from "react";
import { getReservationList } from "../api/ReservationApi";
import BtnDark from "../components/BtnDark";
import Pagination from "../components/Pagination";

export default function ReservationList() {
  const [shopName, setShopName] = useState("");
  const [startPickUpDateTime, setStartPickUpDateTime] = useState("");
  const [endPickUpDateTime, setEndPickUpDateTime] = useState("");
  const [startReturnDateTime, setStartReturnDateTime] = useState("");
  const [endReturnDateTime, setEndReturnDateTime] = useState("");
  const [customerName, setCustomerName] = useState("");
  const [sellName, setSellName] = useState("");
  const [stage, setStage] = useState("");

  const [sortColumn, setSortColumn] = useState("");
  const [sortDirection, setSortDirection] = useState("");

  const [page, setPage] = useState(1); 
  const [totalPages, setTotalPages] = useState(1); 

  const [responseData, setResponseData] = useState([]);

  const handleShopNameChange = (event) => {
    setShopName(event.target.value);
  };

  const handleStartPickUpDateTimeChange = (date) => {
    setStartPickUpDateTime(date.toISOString().split("T")[0] + " 00:00:00");
  };

  const handleEndPickUpDateTimeChange = (date) => {
    setEndPickUpDateTime(date.toISOString().split("T")[0] + " 23:59:59");
  };

  const handleStartReturnDateTimeChange = (date) => {
    setStartReturnDateTime(date.toISOString().split("T")[0] + " 00:00:00");
  };

  const handleEndReturnDateTimeChange = (date) => {
    setEndReturnDateTime(date.toISOString().split("T")[0] + " 23:59:59");
  };

  const handleCustomerNameChange = (event) => {
    setCustomerName(event.target.value);
  };

  const handleSellNameChange = (event) => {
    setSellName(event.target.value);
  };

  const handleStageChange = (event) => {
    setStage(event.target.value);
  };

  const handleSort = (columnName) => {
    if (sortColumn === columnName) {
      setSortDirection(sortDirection === "asc" ? "desc" : "asc");
    } else {
      setSortColumn(columnName);
      setSortDirection("desc");
    }

  };

  const renderArrow = (columnName) => {
    if (sortColumn === columnName) {
      return sortDirection === "desc" ? (
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

  function handleSearchReservations() {
    getReservationList( {
        shopName: shopName,
        startPickUpDateTime: startPickUpDateTime,
        endPickUpDateTime: endPickUpDateTime,
        startReturnDateTime: startReturnDateTime,
        endReturnDateTime: endReturnDateTime,
        customerName: customerName,
        sellName: sellName,
        stage: stage,
        sortColumn: sortColumn,
        sortDirection: sortDirection,
      }
    ).then((response) => {
      setResponseData(response.data.data);
      setPage(response.data.pageInfo.page + 1);
      setTotalPages(response.data.pageInfo.totalPages);
    });
  }


  const search = (pageNumber = 1) => {

    getReservationList( {
        shopName: shopName,
        startPickUpDateTime: startPickUpDateTime,
        endPickUpDateTime: endPickUpDateTime,
        startReturnDateTime: startReturnDateTime,
        endReturnDateTime: endReturnDateTime,
        customerName: customerName,
        sellName: sellName,
        stage: stage,
        sortColumn: sortColumn,
        sortDirection: sortDirection,
        page: pageNumber - 1
      }
    ).then((response) => {
      setResponseData(response.data.data);
      setPage(response.data.pageInfo.page + 1);
      setTotalPages(response.data.pageInfo.totalPages);
    });

  };


  useEffect(() => {
    search(page);
  }, [sortDirection, sortColumn]);

  return (
    <div className={classNames("page")}>
      <SideBar currentPage={"reservation"} />
      <div className={classNames("body")}>
        <div className={classNames("title")}>
          <div>예약 관리</div>
        </div>
        <div className={classNames("content")}>
          <div className={classNames("search-area")}>
            <div className={classNames("search-row")}>
              <div className={classNames("search-item")}>
                <div>정비소</div>
                <select value={shopName} onChange={handleShopNameChange}>
                  <option value="">정비소를 선택해주세요.</option>
                  <option value="블루핸즈 인천공항점">
                    블루핸즈 인천공항점
                  </option>
                  <option value="블루핸즈 김포공항점">
                    블루핸즈 김포공항점
                  </option>
                </select>
              </div>
            </div>
            <div className={classNames("search-row")}>
              <div className={classNames("search-item")}>
                <div>센터 방문</div>
                <CustomCalendar
                  value={startPickUpDateTime}
                  onChange={handleStartPickUpDateTimeChange}
                />
                <div>~</div>
                <CustomCalendar
                  value={endPickUpDateTime}
                  onChange={handleEndPickUpDateTimeChange}
                />
              </div>
              <div className={classNames("search-item")}>
                <div>픽업</div>
                <CustomCalendar
                  value={startReturnDateTime}
                  onChange={handleStartReturnDateTimeChange}
                />
                <div>~</div>
                <CustomCalendar
                  value={endReturnDateTime}
                  onChange={handleEndReturnDateTimeChange}
                />
              </div>
            </div>
            <div className={classNames("search-row")}>
              <div className={classNames("search-item")}>
                <div>고객명</div>
                <input
                  type="text"
                  value={customerName}
                  onChange={handleCustomerNameChange}
                />
              </div>
              <div className={classNames("search-item")}>
                <div>차종</div>
                <input
                  type="text"
                  value={sellName}
                  onChange={handleSellNameChange}
                />
              </div>
              <div className={classNames("search-item")}>
                <div>진행 단계</div>
                <select value={stage} onChange={handleStageChange}>
                  <option value="">진행 단계를 선택해주세요.</option>
                  <option value="예약완료">예약 완료</option>
                  <option value="차량인수">차량 인수</option>
                  <option value="정비중">정비 중</option>
                  <option value="보관중">보관 중</option>
                  <option value="차량인계">차량 인계</option>
                </select>
              </div>
              <BtnDark text={"검색"} onClick={handleSearchReservations} />
            </div>
          </div>
          <div className={classNames("table")}>
            <div className={classNames("th")}>
              <div
                className={classNames("td", "w-100")}
                onClick={() => handleSort("no")}
              >
                <div>No {renderArrow("no")}</div>
              </div>
              <div className={classNames("td", "w-250")}>
                <div>정비소 </div>
              </div>
              <div className={classNames("td", "w-150")}>
                <div>고객명 </div>
              </div>
              <div className={classNames("td", "w-200")}>
                <div>차종 </div>
              </div>
              <div
                className={classNames("td", "w-250")}
                onClick={() => handleSort("departureTime")}
              >
                <div>센터 방문 {renderArrow("departureTime")}</div>
              </div>
              <div
                className={classNames("td", "w-250")}
                onClick={() => handleSort("arrivalTime")}
              >
                <div>픽업 {renderArrow("arrivalTime")}</div>
              </div>
              <div className={classNames("td", "w-150")}>
                <div>현재 단계 </div>
              </div>
            </div>
            {responseData &&
              responseData.map((item) => (
                <div key={item.reservationId} className={classNames("tr")}>
                  <div className={classNames("td", "w-100")}>
                    <div>{item.reservationId}</div>
                  </div>
                  <div className={classNames("td", "w-250")}>
                    <div>{item.shopName}</div>
                  </div>
                  <div className={classNames("td", "w-150")}>
                    <div>{item.customerName}</div>
                  </div>
                  <div className={classNames("td", "w-200")}>
                    <div>{item.sellName}</div>
                  </div>
                  <div className={classNames("td", "w-250")}>
                    <div>{item.arrivalTime}</div>
                  </div>
                  <div className={classNames("td", "w-250")}>
                    <div>{item.departureTime}</div>
                  </div>
                  <div className={classNames("td", "w-150")}>
                    <div>{item.stage}</div>
                  </div>
                </div>
              ))}
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
    </div>
  );
}
