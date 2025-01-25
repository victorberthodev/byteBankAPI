import React from 'react';
import './Home.css'; 
import { AiFillBank } from "react-icons/ai";
 
const Home: React.FC = () => {
  return (
    <div className="homeContainer">
      <AiFillBank className="homeIcon" />
      <h1>Bem-vindo ao ByteBank</h1>
      <p className="intro">Um sistema bancário 100% digital.</p>
      <p className="intro">Navegue por todas as abas e desfrute do ByteBank!</p>
      <div className="featureCards">
        <div className="card">
          <h3>Saldo</h3>
          <p>Verifique seu saldo atualizado em tempo real.</p>
        </div>
        <div className="card">
          <h3>Transações</h3>
          <p>Faça transações de forma rápida e simples.</p>
        </div>
        <div className="card">
          <h3>Extrato</h3>
          <p>Seu extrato completo com apenas um clique.</p>
        </div>
      </div>
    </div>
  );
};

export default Home;
