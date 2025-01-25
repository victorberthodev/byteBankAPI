import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Cadastro from '../components/Cadastro/Cadastro';
import Extrato from '../components/Extrato/Extrato';
import Transferencias from '../components/Transferencias/Transferencias';
import Home from '../components/Home/Home';

const AppRoutes: React.FC = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/cadastro" element={<Cadastro />} />
        <Route path="/transferencias" element={<Transferencias />} />
        <Route path="/extrato" element={<Extrato />} />
      </Routes>
    </BrowserRouter>
  );
};

export default AppRoutes;
