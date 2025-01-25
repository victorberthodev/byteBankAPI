import React, { useState } from 'react';
import axios from 'axios';
import Modal from 'react-modal';
import './Transferencias.css';
import { GrTransaction } from "react-icons/gr";

Modal.setAppElement('#root'); 

const Transferencias: React.FC = () => {
  const [operacao, setOperacao] = useState('');
  const [id, setId] = useState('');
  const [valor, setValor] = useState('');
  const [idDestino, setIdDestino] = useState('');
  const [feedback, setFeedback] = useState({ message: '', type: '' });
  const [modalIsOpen, setModalIsOpen] = useState(false);

  const showFeedback = (message: string, type: 'success' | 'error') => {
    setFeedback({ message, type });
  };

  const executeTransaction = async () => {
    try {
      let url = '';
      const payload = {};

      switch (operacao) {
        case 'saque':
          url = `http://localhost:8080/contas/${id}/sacar`;
          Object.assign(payload, { valor: parseFloat(valor) });
          break;
        case 'deposito':
          url = `http://localhost:8080/contas/${id}/depositar`;
          Object.assign(payload, { valor: parseFloat(valor) });
          break;
        case 'transferencia':
          url = `http://localhost:8080/contas/${id}/transferir`;
          Object.assign(payload, {
            idContaDestino: idDestino,
            valor: parseFloat(valor)
          });
          break;
        case 'cancelamento':
          url = `http://localhost:8080/contas/${id}/cancelar`;
          break;
        default:
          return; 
      }

      const response = operacao === 'cancelamento' ? 
        await axios.delete(url) :  
        await axios.post(url, payload);

      showFeedback(`Operação realizada com sucesso`, 'success');
    } catch (error) {
      if (axios.isAxiosError(error) && error.response) {
        showFeedback(error.response.data, 'error');
      } else {
        showFeedback('Falha na operação. Tente novamente mais tarde.', 'error');
      }
    }
    setModalIsOpen(false);
  };

  const handleTransacao = () => {
    if (['transferencia', 'cancelamento', 'saque', 'deposito'].includes(operacao)) {
      setModalIsOpen(true); 
    } else {
      executeTransaction();
    }
  };

  const handleSubmit = (event: React.FormEvent) => {
    event.preventDefault();
    handleTransacao();
  };

  return (
    <div className="transferenciasContainer">
      <h1>Área de Transações</h1>
      <form onSubmit={handleSubmit}> 
        <select value={operacao} onChange={(e) => setOperacao(e.target.value)}>
          <option value="">Selecione a operação</option>
          <option value="saque">Saque</option>
          <option value="deposito">Depósito</option>
          <option value="transferencia">Transferência</option>
          <option value="cancelamento">Cancelar Transação</option> 
        </select>

        {operacao === 'saque' && (
          <>
            <input 
              type="text"
              placeholder="Seu ID"
              value={id}
              onChange={(e) => setId(e.target.value)}
            />
            <input 
              type="number"
              placeholder="Valor a sacar"
              value={valor}
              onChange={(e) => setValor(e.target.value)}
            />
          </>
        )}

        {(operacao === 'deposito' || operacao === 'transferencia') && (
          <>
            <input 
              type="text"
              placeholder="Seu ID"
              value={id}
              onChange={(e) => setId(e.target.value)}
            />
            {operacao === 'transferencia' && (
              <input 
                type="text"
                placeholder="ID de destino"
                value={idDestino}
                onChange={(e) => setIdDestino(e.target.value)}
              />
            )}
            <input 
              type="number"
              placeholder="Valor a depositar"
              value={valor}
              onChange={(e) => setValor(e.target.value)}
            />
          </>
        )}

        {operacao === 'cancelamento' && (
          <>
            <input 
              type="text"
              placeholder="ID da Transação"
              value={id}
              onChange={(e) => setId(e.target.value)}
            />
          </>
        )}

        <button type="submit"><GrTransaction /> Realizar Operação</button>
      </form>
      <Modal
        isOpen={modalIsOpen}
        onRequestClose={() => setModalIsOpen(false)}
        contentLabel="Confirmação de Transação"
        className="modal-content"
        overlayClassName="modal-overlay"
      >
        <h2>Confirmação</h2>
        <p>Tem certeza de que deseja proceder com esta operação?</p>
        <button onClick={executeTransaction}>Confirmar</button>
        <button onClick={() => setModalIsOpen(false)}>Cancelar</button>
      </Modal>
      {feedback.message && <p className={`feedback ${feedback.type}`}>{feedback.message}</p>}
    </div>
  );
};

export default Transferencias;
