import React from "react";
import ReactDOM from "react-dom/client";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import "./styles/styles.scss";
import Main from "./pages/Main";
import NotFound from "./pages/NotFound";
import { RecoilRoot } from "recoil";
import TestPage from "./pages/TestPage";
import Account from "./pages/Account";

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <RecoilRoot>
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Main />} />
        <Route path="/test" element={<TestPage />} />
        <Route path="*" element={<NotFound />} />
        <Route path="/admin/account" element={<Account />} />
      </Routes>
    </BrowserRouter>
  </RecoilRoot>
);
