import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import './styles/styles.scss';
import Main from './pages/Main';
import NotFound from './pages/NotFound';
import ReservationIntro from './pages/ReservationIntro';
import Bot from './pages/Bot';
import Mypage from './pages/Mypage';
import ReservationComplete from './pages/ReservationComplete';
import ReservationModal from './pages/ReservationModal';
import Profile from './pages/Profile';
import ReservationDetail from './pages/ReservationDetail';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <BrowserRouter>
    <Routes>
      <Route path="/" element={<Main />} />
      <Route path="/reservation/intro" element={<ReservationIntro />} />
      <Route path="/reservation/complete" element={<ReservationComplete />} />
      <Route path="/reservation/modal" element={<ReservationModal />} />
      <Route path="/reservation/detail" element={<ReservationDetail />} />
      <Route path="/bot" element={<Bot />} />
      <Route path="/mypage" element={<Mypage />} />
      <Route path="/profile" element={<Profile />} />
      <Route path="*" element={<NotFound />} />
    </Routes>
  </BrowserRouter>,
);
