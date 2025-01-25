import React, { useState } from 'react';
import axios from 'axios';
import './Extrato.css'; 
import Tabela from './Tabela';
import { IoIosSearch } from "react-icons/io";

type Transacao = {
  idTransacao: string;
  dataTransacao: string; 
  tipoTransacao: string;
  valor: number;
  saldoAposTransacao: number;
  contaOrigemId?: string; 
  contaDestinoId?: string; 
  status: string;
}

const Extrato: React.FC = () => {
  const [idUsuario, setIdUsuario] = useState('');
  const [transacoes, setTransacoes] = useState<Transacao[]>([]);
  const [erro, setErro] = useState('');

  const buscarTransacoes = async () => {
    try {
      const response = await axios.get<Transacao[]>(`http://localhost:8080/contas/${idUsuario}/extrato`);
      setTransacoes(response.data);
      setErro('');
    } catch (error) {
      setErro('Erro ao buscar transações');
      console.error(error);
    }
  };

  return (
    <div className="extratoContainer">
      <h1>Extrato</h1>
      <div>
        <label htmlFor="idUsuario">Usuário:</label>
        <input placeholder="Pesquisar por ID "
          type="text"
          id="idUsuario"
          value={idUsuario}
          onChange={(e) => setIdUsuario(e.target.value)}
        />
        <button onClick={buscarTransacoes}><IoIosSearch /> Buscar</button>
      </div>
      {erro && <p className="erro">{erro}</p>}
      <Tabela transacoes={transacoes} />
    </div>
  );
};

export default Extrato;


