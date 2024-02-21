import React from "react";
import ReactDOM from "react-dom/client";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import "./styles/styles.scss";
import Main from "./pages/Main";
import NotFound from "./pages/NotFound";
import { RecoilRoot } from "recoil";
import TestPage from "./pages/TestPage";
import ReservationDetail from "./pages/RservationDetail";
import ReservationList from "./pages/ReservationList";
import Account from "./pages/Account";

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <RecoilRoot>
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Main />} />
        <Route path="/test" element={<TestPage />} />
        <Route path="reservation" element={<ReservationList />} />
        <Route path="/reservation/detail" element={<ReservationDetail />} />
        <Route path="*" element={<NotFound />} />
        <Route path="/admin/account" element={<Account />} />
      </Routes>
    </BrowserRouter>
  </RecoilRoot>
);
