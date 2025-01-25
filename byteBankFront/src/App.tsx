import React from 'react';
import Sidebar from './components/Sidebar/Sidebar';
import './App.css';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Home from './components/Home/Home'; 
import Cadastro from './components/Cadastro/Cadastro';
import Extrato from './components/Extrato/Extrato';
import Transferencias from './components/Transferencias/Transferencias';
import Busca from './components/Busca/Busca';
import Conta from './components/Conta/Conta'; 
import BotaoAjuda from './components/BotaoAjuda/BotaoAjuda';
import { Link } from 'react-router-dom';
import { FaRegUserCircle } from "react-icons/fa";

const App: React.FC = () => {
  return (
    <BrowserRouter>
      <div className="appContainer">
      <header className='header'>
        <span className='headerTitle'>

          <h1><Link to="/conta" className="botaoConta">
          <FaRegUserCircle />
       </Link></h1>
        </span>
      </header>
          
        <div className="sidebarContainer">
          <Sidebar />
        </div>
        <div className="mainContent">
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/cadastro" element={<Cadastro />} />
            <Route path="/transferencias" element={<Transferencias />} />
            <Route path="/extrato" element={<Extrato />} />
            <Route path="/conta" element={<Conta />} />
            <Route path="/busca" element={<Busca />} />
            <Route path='*' element={<h1>Pagina não encontrada</h1>}/>
            {}
          </Routes>
        </div>
        <BotaoAjuda /> 
      </div>
    </BrowserRouter>
  );
};

export default App;


