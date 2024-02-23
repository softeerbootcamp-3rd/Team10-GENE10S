import React from "react";
import ReactDOM from "react-dom/client";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import "./styles/styles.scss";
import NotFound from "./pages/NotFound";
import { RecoilRoot } from "recoil";
import ReservationList from "./pages/ReservationList";
import ReservationDetail from "./pages/ReservationDetail";
import Account from "./pages/Account";
import ShopManage from "./pages/ShopManage"
import Login from "./pages/Login";

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <RecoilRoot>
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route
          path="/reservation/:reservationId/detail"
          element={<ReservationDetail />}
        />
        <Route path="reservation" element={<ReservationList />} />
        <Route path="/shop/manage" element={<ShopManage />}/>
        <Route path="*" element={<NotFound />} />
        <Route path="/admin/account" element={<Account />} />
      </Routes>
    </BrowserRouter>
  </RecoilRoot>
);
// Github Action 배포 자동화 테스트를 위한 변경
