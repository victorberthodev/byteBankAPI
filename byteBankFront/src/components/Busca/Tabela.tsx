import React, { useState, useMemo } from 'react';
import './Tabela.css';

type Usuario = {
  idConta: string;
  nomeTitular: string;
  tipoConta: string;
  numeroConta: string;
  agencia: string;
  saldo: number;
  status: string;
};

type TabelaProps = {
  usuarios: Usuario[];
};

const Tabela: React.FC<TabelaProps> = ({ usuarios }) => {
  const [sortConfig, setSortConfig] = useState<{ key: keyof Usuario; direction: 'ascending' | 'descending' } | null>(null);
  const [currentPage, setCurrentPage] = useState(1);
  const [itemsPerPage] = useState(5);

  const sortedUsuarios = useMemo(() => {
    let sortableItems = [...usuarios];
    if (sortConfig !== null) {
      sortableItems.sort((a, b) => {
        if (a[sortConfig.key] < b[sortConfig.key]) {
          return sortConfig.direction === 'ascending' ? -1 : 1;
        }
        if (a[sortConfig.key] > b[sortConfig.key]) {
          return sortConfig.direction === 'ascending' ? 1 : -1;
        }
        return 0;
      });
    }
    return sortableItems.slice((currentPage - 1) * itemsPerPage, currentPage * itemsPerPage);
  }, [usuarios, sortConfig, currentPage, itemsPerPage]);

  const totalPages = Math.ceil(usuarios.length / itemsPerPage);

  const requestSort = (key: keyof Usuario) => {
    let direction: 'ascending' | 'descending' = 'ascending';
    if (sortConfig && sortConfig.key === key && sortConfig.direction === 'ascending') {
      direction = 'descending';
    }
    setSortConfig({ key, direction });
  };

  const nextPage = () => {
    setCurrentPage((prev) => (prev < totalPages ? prev + 1 : prev));
  };

  const prevPage = () => {
    setCurrentPage((prev) => (prev > 1 ? prev - 1 : prev));
  };

  return (
    <>
      <table className="tabelaUsuarios">
        <thead>
          <tr>
            <th onClick={() => requestSort('idConta')}>ID</th>
            <th onClick={() => requestSort('nomeTitular')}>Nome</th>
            <th onClick={() => requestSort('tipoConta')}>Conta</th>
            <th onClick={() => requestSort('numeroConta')}>NumConta</th>
            <th onClick={() => requestSort('agencia')}>Agência</th>
            <th onClick={() => requestSort('saldo')}>Saldo</th>
            <th onClick={() => requestSort('status')}>Status</th>
          </tr>
        </thead>
        <tbody>
          {sortedUsuarios.map((usuario) => (
            <tr key={usuario.idConta}>
              <td>{usuario.idConta}</td>
              <td>{usuario.nomeTitular}</td>
              <td>{usuario.tipoConta}</td>
              <td>{usuario.numeroConta}</td>
              <td>{usuario.agencia}</td>
              <td>{usuario.saldo}</td>
              <td>{usuario.status}</td>
            </tr>
          ))}
        </tbody>
      </table>
      <div className="pagination">
        <button onClick={prevPage} disabled={currentPage === 1}>Anterior</button>
        <span> Página {currentPage} de {totalPages} </span>
        <button onClick={nextPage} disabled={currentPage === totalPages}>Próxima</button>
      </div>
    </>
  );
};

export default Tabela;
