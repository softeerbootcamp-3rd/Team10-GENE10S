import { useEffect, useState, useRef } from "react";
import { getAccountList } from "../api/AccountApi";
import BtnDark from "../components/button/BtnDark";
import AdminPage from "../components/common/AdminPage";
import SearchArea from "../components/list/SearchArea";
import SearchRow from "../components/list/SearchRow";
import SearchInput from "../components/list/SearchInput";
import Table from "../components/list/Table";
import TableRow from "../components/list/TableRow";
import Pagination from "../components/list/Pagination";

export default function AccountList() {
  const [accountList, setAccountList] = useState([]);

  const userIdRef = useRef();
  const userNameRef = useRef();
  const phoneNumberRef = useRef();
  const [sortColumn, setSortColumn] = useState("id");
  const [sortDirection, setSortDirection] = useState("desc");

  const [page, setPage] = useState(1);
  const [totalPages, setTotalPages] = useState(1);

  const headerInfo = [
    {
      label: "No",
      name: "id",
      width: 100,
      sortable: true,
    },
    { label: "아이디", name: "adminId", width: 250 },
    { label: "이름", name: "adminName", width: 300 },
    { label: "전화번호", name: "phoneNumber", width: 200 },
    {
      label: "가입일",
      name: "createDatetime",
      width: 350,
      sortable: true,
    },
    { label: "삭제", name: "deleteButton", width: 150 },
  ];

  const search = (pageNumber = 1) => {
    getAccountList({
      adminId: userIdRef.current.value,
      adminName: userNameRef.current.value,
      phoneNumber: phoneNumberRef.current.value,
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
  }, [sortDirection, sortColumn]);

  const handleSort = (columnName) => {
    if (sortColumn === columnName) {
      setSortDirection(sortDirection === "desc" ? "asc" : "desc");
    } else {
      setSortColumn(columnName);
      setSortDirection("desc");
    }
  };

  const deleteAccount = (id) => {
    console.log("deleteAccount", id);
  };

  useEffect(() => {
    search();
  }, []);

  return (
    <AdminPage pageName='관리자 계정 관리'>
      <SearchArea>
        <SearchRow>
          <SearchInput label='아이디' inputRef={userIdRef} />
          <SearchInput label='이름' inputRef={userNameRef} />
          <SearchInput label='전화번호' inputRef={phoneNumberRef} />
          <BtnDark onClick={() => search()}>검색</BtnDark>
        </SearchRow>
      </SearchArea>
      <Table
        headerInfo={headerInfo}
        onSort={handleSort}
        sortColumn={sortColumn}
        sortDirection={sortDirection}
      >
        {accountList.map((account) => (
          <TableRow
            key={`row_${account.id}`}
            headerInfo={headerInfo}
            data={{
              key: account.id,
              id: { text: account.id, type: "text" },
              adminId: { text: account.adminId, type: "text" },
              adminName: { text: account.adminName, type: "text" },
              phoneNumber: { text: account.phoneNumber, type: "text" },
              createDatetime: { text: account.createDateTime, type: "text" },
              deleteButton: {
                text: "삭제",
                type: "button",
                onClick: deleteAccount,
              },
            }}
          ></TableRow>
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
