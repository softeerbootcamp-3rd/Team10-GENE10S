import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Main from './pages/Main';
import './styles/reset.scss';
import NotFound from './pages/NotFound';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <BrowserRouter>
    <Routes>
      <Route path="/" element={<Main />} />
      <Route path="*" element={<NotFound />} />
    </Routes>
  </BrowserRouter>,
);
