import React, { useState, useEffect, useRef } from "react";
import { getReservationList } from "../api/ReservationApi";
import BtnDark from "../components/button/BtnDark";
import Pagination from "../components/list/Pagination";
import SearchSelect from "../components/list/SearchSelect";
import shopNames from "../constants/shopNames";
import SearchInput from "../components/list/SearchInput";
import SearchDate from "../components/list/SearchDate";
import carSellNames from "../constants/carSellNames";
import serviceSteps from "../constants/serviceSteps";
import Table from "../components/list/Table";
import AdminPage from "../components/common/AdminPage";
import SearchArea from "../components/list/SearchArea";
import SearchRow from "../components/list/SearchRow";
import TableRow from "../components/list/TableRow";

export default function ReservationList() {
  const shopNameRef = useRef();
  const [startPickUpDateTime, setStartPickUpDateTime] = useState("");
  const [endPickUpDateTime, setEndPickUpDateTime] = useState("");
  const [startReturnDateTime, setStartReturnDateTime] = useState("");
  const [endReturnDateTime, setEndReturnDateTime] = useState("");
  const customerNameRef = useRef();
  const sellNameRef = useRef();
  const stageRef = useRef();

  const [sortColumn, setSortColumn] = useState("id");
  const [sortDirection, setSortDirection] = useState("desc");

  const [page, setPage] = useState(1);
  const [totalPages, setTotalPages] = useState(1);

  const [responseData, setResponseData] = useState([]);

  const headerInfo = [
    { label: "No", name: "id", width: 100, sortable: true },
    { label: "정비소", name: "shopName", width: 250 },
    { label: "고객명", name: "customerName", width: 150 },
    { label: "차종", name: "sellName", width: 200 },
    { label: "센터 방문", name: "departureTime", width: 250, sortable: true },
    { label: "픽업", name: "arrivalTime", width: 250, sortable: true },
    { label: "현재 단계", name: "stage", width: 150 },
  ];

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

  const handleSort = (columnName) => {
    if (sortColumn === columnName) {
      setSortDirection(sortDirection === "asc" ? "desc" : "asc");
    } else {
      setSortColumn(columnName);
      setSortDirection("desc");
    }
  };

  function handleSearchReservations() {
    search();
  }

  const search = (pageNumber = 1) => {
    getReservationList({
      shopName: shopNameRef.current.value,
      startPickUpDateTime: startPickUpDateTime,
      endPickUpDateTime: endPickUpDateTime,
      startReturnDateTime: startReturnDateTime,
      endReturnDateTime: endReturnDateTime,
      customerName: customerNameRef.current.value,
      sellName: sellNameRef.current.value,
      stage: stageRef.current.value,
      sortColumn: sortColumn,
      sortDirection: sortDirection,
      page: pageNumber - 1,
    }).then((response) => {
      setResponseData(response.data.data);
      setPage(response.data.pageInfo.page + 1);
      setTotalPages(response.data.pageInfo.totalPages);
    });
  };

  useEffect(() => {
    search(page);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [sortDirection, sortColumn]);

  return (
    <AdminPage pageName='예약 관리'>
      <SearchArea>
        <SearchRow>
          <SearchSelect
            label='정비소'
            selectRef={shopNameRef}
            options={shopNames}
          />
        </SearchRow>
        <SearchRow>
          <SearchDate
            label='센터 방문'
            startValue={startPickUpDateTime}
            onStartChange={handleStartPickUpDateTimeChange}
            endValue={endPickUpDateTime}
            onEndChange={handleEndPickUpDateTimeChange}
          />
          <SearchDate
            label='픽업'
            startValue={startReturnDateTime}
            onStartChange={handleStartReturnDateTimeChange}
            endValue={endReturnDateTime}
            onEndChange={handleEndReturnDateTimeChange}
          />
        </SearchRow>
        <SearchRow>
          <SearchInput label='고객명' inputRef={customerNameRef} />
          <SearchSelect
            label='차종'
            selectRef={sellNameRef}
            options={carSellNames}
          />
          <SearchSelect
            label='진행 단계'
            selectRef={stageRef}
            options={serviceSteps}
          />
          <BtnDark onClick={handleSearchReservations}>검색</BtnDark>
        </SearchRow>
      </SearchArea>
      <Table
        headerInfo={headerInfo}
        onSort={handleSort}
        sortColumn={sortColumn}
        sortDirection={sortDirection}
      >
        {responseData &&
          responseData.map((reservation) => (
            <TableRow
              key={`row_${reservation.reservationId}`}
              headerInfo={headerInfo}
              data={{
                key: reservation.reservationId,
                id: { text: reservation.reservationId, type: "text" },
                shopName: { text: reservation.shopName, type: "text" },
                customerName: { text: reservation.customerName, type: "text" },
                sellName: { text: reservation.sellName, type: "text" },
                departureTime: {
                  text: reservation.departureTime,
                  type: "text",
                },
                arrivalTime: { text: reservation.arrivalTime, type: "text" },
                stage: { text: reservation.stage, type: "text" },
              }}
            />
          ))}
      </Table>
      <Pagination
        page={page}
        totalPages={totalPages}
        paginationSize={5}
        onChange={search}
      />
    </AdminPage>
  );
}
