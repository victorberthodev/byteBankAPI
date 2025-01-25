import React from 'react';
import { FaUser, FaIdCard, FaMoneyCheckAlt, FaUniversity, FaBalanceScale, FaRegMoneyBillAlt } from 'react-icons/fa';
import { MdAccountBalanceWallet, MdOutlineAccountBalance } from 'react-icons/md';
import './Conta.css';

const Conta: React.FC = () => {
  const infoUsuario = {
    idConta: '000123',
    nome: 'Victor Bertho',
    cpf: '123.456.789-00',
    tipoConta: 'Conta Corrente',
    numeroConta: '32164587',
    agencia: '0000-1',
    saldo: 'R$ 500.000,00',
    status: 'Ativo'
  };

  return (
    <div className="contaContainer">
      <h2>Informações da Conta</h2>
      <div className="info">
        <div className="infoItem">
          <FaIdCard />
          <span>ID da Conta: {infoUsuario.idConta}</span>
        </div>
        <div className="infoItem">
          <FaUser />
          <span>{infoUsuario.nome}</span>
        </div>
        <div className="infoItem">
          <FaRegMoneyBillAlt />
          <span>CPF: {infoUsuario.cpf}</span>
        </div>
        <div className="infoItem">
          <MdAccountBalanceWallet />
          <span>{infoUsuario.tipoConta}</span>
        </div>
        <div className="infoItem">
          <FaMoneyCheckAlt />
          <span>Conta: {infoUsuario.numeroConta}</span>
        </div>
        <div className="infoItem">
          <MdOutlineAccountBalance />
          <span>Agência: {infoUsuario.agencia}</span>
        </div>
        <div className="infoItem">
          <FaUniversity />
          <span>Saldo: {infoUsuario.saldo}</span>
        </div>
        <div className="infoItem">
          <FaBalanceScale />
          <span>Status: {infoUsuario.status}</span>
        </div>
      </div>
    </div>
  );
};

export default Conta;
 