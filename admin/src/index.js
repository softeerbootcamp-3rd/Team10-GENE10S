import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import './styles/styles.scss';
import Main from './pages/Main';
import NotFound from './pages/NotFound';
import { RecoilRoot } from 'recoil';
import ReservationList from './pages/ReservationList';
import ReservationDetail from './pages/RservationDetail';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <RecoilRoot>
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Main />} />
        <Route path="/reservation/:reservationId/detail" element={<ReservationDetail />}/>
        <Route path="reservation" element={<ReservationList />}/>
        <Route path="*" element={<NotFound />} />
      </Routes>
    </BrowserRouter>
  </RecoilRoot>
);
