import React, { useState } from 'react';
import axios from 'axios';
import Modal from 'react-modal';
import './Cadastro.css';

Modal.setAppElement('#root'); 

const Cadastro: React.FC = () => {
  const [nome, setNome] = useState('');
  const [cpf, setCpf] = useState('');
  const [tipoConta, setTipoConta] = useState('');
  const [id, setId] = useState('');
  const [status, setStatus] = useState('');
  const [feedback, setFeedback] = useState({ message: '', type: '' }); 
  const [acao, setAcao] = useState('cadastrar');
  const [modalIsOpen, setModalIsOpen] = useState(false);

  const showFeedback = (message: string, type: 'success' | 'error') => {
    setFeedback({ message, type });
    setTimeout(() => {
        setFeedback({ message: '', type: '' });
    }, 3000);
  };

  const handleAction = async () => {
    try {
      let response;
      switch (acao) {
        case 'cadastrar':
          response = await axios.post('http://localhost:8080/contas', {
            nomeTitular: nome,
            cpfTitular: cpf,
            tipoConta
          });
          break;
        case 'atualizar':
          response = await axios.put(`http://localhost:8080/contas/${id}`, {
            nomeTitular: nome,
            status
          });
          break;
        case 'deletar':
          response = await axios.delete(`http://localhost:8080/contas/${id}`);
          break;
        default:
          throw new Error('Ação inválida');
      }
      showFeedback(`Operação realizada com sucesso! ID: ${response.data.idConta}`, 'success');
      setNome('');
      setCpf('');
      setTipoConta('');
      setId('');
      setStatus('');
      if (acao === 'deletar') setModalIsOpen(false);
    } catch (error) {
      if (axios.isAxiosError(error)) {
        showFeedback(`Erro: ${error.response?.data || 'Erro desconhecido'}`, 'error');
      } else if (error instanceof Error) {
        showFeedback(`Erro: ${error.message}`, 'error');
      } else {
        showFeedback('Erro desconhecido', 'error');
      }
      if (acao === 'deletar') setModalIsOpen(false);
    }
  };


  const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    if (acao === 'deletar') {
      setModalIsOpen(true);
    } else {
      handleAction();
    }
  };

  return (
    <div className="cadastroContainer">
      <h2>Área do Cliente</h2>
      <div className={`feedback ${feedback.type}`}>{feedback.message}</div>
      <div className="acoes">
        <select value={acao} onChange={(e) => setAcao(e.target.value)} className="actionSelect">
          <option value="cadastrar">Cadastrar</option>
          <option value="atualizar">Atualizar</option>
          <option value="deletar">Deletar</option>
        </select>
      </div>
      <form onSubmit={handleSubmit}>
        {acao === 'cadastrar' && (
          <>
            <label htmlFor="nome">Nome</label>
            <input type="text" id="nome" value={nome} onChange={(e) => setNome(e.target.value)} required />
            <label htmlFor="cpf">CPF</label>
            <input type="text" id="cpf" value={cpf} onChange={(e) => setCpf(e.target.value)} required />
            <label htmlFor="tipoConta">Tipo de Conta</label>
            <select id="tipoConta" value={tipoConta} onChange={(e) => setTipoConta(e.target.value)} required>
              <option value="">Selecione o tipo de conta</option>
              <option value="corrente">Conta Corrente</option>
              <option value="poupanca">Conta Poupança</option>
              <option value="salario">Conta Salário</option>
            </select>
          </>
        )}
        {acao !== 'cadastrar' && (
          <>
            <label htmlFor="id">ID da Conta</label>
            <input type="text" id="id" value={id} onChange={(e) => setId(e.target.value)} required />
          </>
        )}
        {acao === 'atualizar' && (
          <>
            <label htmlFor="nome">Novo Nome (opcional)</label>
            <input type="text" id="nome" value={nome} onChange={(e) => setNome(e.target.value)} />
            <label htmlFor="status">Status</label>
            <select id="status" value={status} onChange={(e) => setStatus(e.target.value)} required>
              <option value="">Selecione o status</option>
              <option value="ativa">Ativa</option>
              <option value="suspensa">Suspensa</option>
            </select>
          </>
        )}
        <button type="submit" className='criarUsuario'>{acao.charAt(0).toUpperCase() + acao.slice(1)}</button>
      </form>
      <Modal
        isOpen={modalIsOpen}
        onRequestClose={() => setModalIsOpen(false)}
        contentLabel="Confirmação de Exclusão"
        className="modal-content"
        overlayClassName="modal-overlay"
      >
        <h2>Confirmação</h2>
        <p>Tem certeza de que deseja deletar esta conta?</p>
        <button onClick={handleAction}>Confirmar</button>
        <button onClick={() => setModalIsOpen(false)}>Cancelar</button>
      </Modal>
    </div>
  );
};

export default Cadastro;
