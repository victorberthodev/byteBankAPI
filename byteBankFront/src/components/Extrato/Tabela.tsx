import "./Tabela.css";
import React, { useState } from 'react';

type Transacao = {
  idTransacao: string;
  dataTransacao: string;
  tipoTransacao: string;
  valor: number;
  saldoAposTransacao: number;
  contaOrigemId?: string;
  contaDestinoId?: string;
  status: string;
};

type TabelaProps = {
  transacoes: Transacao[];
};

const TabelaExtratos: React.FC<TabelaProps> = ({ transacoes }) => {
  const [currentPage, setCurrentPage] = useState(1);
  const [itemsPerPage] = useState(5);

  const lastIndex = currentPage * itemsPerPage;
  const firstIndex = lastIndex - itemsPerPage;
  const currentItems = transacoes.slice(firstIndex, lastIndex);

  const totalPages = Math.ceil(transacoes.length / itemsPerPage);

  const nextPage = () => {
    if (currentPage < totalPages) setCurrentPage(currentPage + 1);
  };

  const prevPage = () => {
    if (currentPage > 1) setCurrentPage(currentPage - 1);
  };

  return (
    <>
      <table className="tabelaExtratos">
        <thead>
          <tr>
            <th>ID Transação</th>
            <th>Data</th>
            <th>Tipo</th>
            <th>Valor</th>
            <th>Saldo</th>
            <th>Conta Origem</th>
            <th>Conta Destino</th>
            <th>Status</th>
          </tr>
        </thead>
        <tbody>
          {currentItems.map((transacao) => (
            <tr key={transacao.idTransacao}>
              <td>{transacao.idTransacao}</td>
              <td>{transacao.dataTransacao}</td>
              <td>{transacao.tipoTransacao}</td>
              <td>{transacao.valor}</td>
              <td>{transacao.saldoAposTransacao}</td>
              <td>{transacao.contaOrigemId}</td>
              <td>{transacao.contaDestinoId}</td>
              <td>{transacao.status}</td>
            </tr>
          ))}
        </tbody>
      </table>
      <div className="pagination">
        <button onClick={prevPage} disabled={currentPage === 1}>&laquo; Anterior</button>
        <span> Página {currentPage} de {totalPages} </span>
        <button onClick={nextPage} disabled={currentPage === totalPages}>Próxima &raquo;</button>
      </div>
    </>
  );
};

export default TabelaExtratos;
