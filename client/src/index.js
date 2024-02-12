import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import './styles/styles.scss';
import Main from './pages/Main';
import NotFound from './pages/NotFound';
import Reservation from './pages/Reservation';
import ReservationIntro from './pages/ReservationIntro';
import Bot from './pages/Bot';
import Mypage from './pages/Mypage';
import ReservationComplete from './pages/ReservationComplete';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <BrowserRouter>
    <Routes>
      <Route path="/" element={<Main />} />
      <Route path="/reservation" element={<Reservation />} />
      <Route path="/reservation-intro" element={<ReservationIntro />} />
      <Route path="/reservation/complete" element={<ReservationComplete />} />
      <Route path="/bot" element={<Bot />} />
      <Route path="/mypage" element={<Mypage />} />
      <Route path="*" element={<NotFound />} />
    </Routes>
  </BrowserRouter>,
);
