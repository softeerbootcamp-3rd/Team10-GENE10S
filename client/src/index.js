import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import './styles/styles.scss';
import Main from './pages/Main';
import NotFound from './pages/NotFound';
import ReservationDate from './pages/ReservationDate';
import Bot from './pages/Bot';
import Mypage from './pages/Mypage';
import ReservationComplete from './pages/ReservationComplete';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <BrowserRouter>
    <Routes>
      <Route path="/" element={<Main />} />
      <Route path="/reservation" element={<ReservationDate />} />
      <Route path="/reservation/complete" element={<ReservationComplete />} />
      <Route path="/bot" element={<Bot />} />
      <Route path="/mypage" element={<Mypage />} />
      <Route path="*" element={<NotFound />} />
    </Routes>
  </BrowserRouter>,
);
