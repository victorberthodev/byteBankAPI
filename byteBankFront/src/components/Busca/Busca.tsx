import React, { useState, useMemo } from 'react';
import axios from 'axios';
import './Busca.css'; 
import Tabela from './Tabela';
import { IoIosSearch } from "react-icons/io";

type Usuario = {
  idConta: string;
  nomeTitular: string;
  cpfTitular: string;
  tipoConta: string;
  numeroConta: string;
  agencia: string;
  saldo: number;
  status: string;
};

  const Busca: React.FC = () => {
    const [idUsuario, setIdUsuario] = useState('');
    const [usuarios, setUsuarios] = useState<Usuario[]>([]);
    const [erro, setErro] = useState('');
    const [filtroNome, setFiltroNome] = useState('');

  const usuariosFiltrados = useMemo(() => {
    let filtrados = usuarios;
    if (filtroNome) {
      filtrados = filtrados.filter(u => u.nomeTitular.toLowerCase().includes(filtroNome.toLowerCase()));
    }
    return filtrados;
  }, [usuarios, filtroNome]);

  const buscarUsuario = async () => {
    try {
      const response = await axios.get<Usuario>(`http://localhost:8080/contas/${idUsuario}`);
      setUsuarios([response.data]);
      setErro('');
    } catch (error) {
      setErro('Usuário não encontrado');
      console.error(error);
    }
  };

  const buscarTodosUsuarios = async () => {
    try {
      const response = await axios.get<Usuario[]>(`http://localhost:8080/contas`);
      setUsuarios(response.data);
      setErro('');
    } catch (error) {
      setErro('Erro ao buscar usuários');
      console.error(error);
    }
  };

  return (
    <div className="buscaContainer">
      <h1>Usuários</h1>
      <div>
        <label htmlFor="idUsuario">Usuário:</label>
        <input type="text" placeholder="Pesquisar por ID "id="idUsuario" value={idUsuario} onChange={(e) => setIdUsuario(e.target.value)} />
        <input type="text" placeholder="Filtrar por nome" value={filtroNome} onChange={(e) => setFiltroNome(e.target.value)} />
        <button onClick={buscarUsuario}><IoIosSearch /> Pesquisar Usuários</button>
        <button onClick={buscarTodosUsuarios}><IoIosSearch /> Todos Usuários</button>
        
      </div>
      {erro && <p className="erro">{erro}</p>}
      <Tabela usuarios={usuariosFiltrados} />  {}
    </div>
  );
};

export default Busca;
