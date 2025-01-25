import React, { useState } from 'react';
import './BotaoAjuda.css';

type ModalProps = {
  children: React.ReactNode;
  onClose: () => void;
};

const Modal: React.FC<ModalProps> = ({ children, onClose }) => {
  return (
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal-content" onClick={e => e.stopPropagation()}>
        <button className="close-button" onClick={onClose}>×</button>
        {children}
      </div>
    </div>
  );
};

const BotaoAjuda: React.FC = () => {
  const [isOpen, setIsOpen] = useState(false);

  const toggleHelpModal = () => {
    setIsOpen(!isOpen);
  };

  return (
    <>
      <button className="botaoAjuda" onClick={toggleHelpModal}>
      Ajuda
      </button>
      {isOpen && (
        <Modal onClose={toggleHelpModal}>
          <h2>Dicas de Utilização</h2>
          <ul>
            <h3>Menu Lateral</h3>
            <li>Navegue utilizando o menu lateral para acessar diferentes funcionalidades.</li>
            <h3>Cadastro</h3>
            <li>Na aba de cadastro você pode adicionar, atualizar ou deletar um usuário. Tenha cuidado e confirme o ID correto do usuário antes de realizar alguma dessas ações.</li>
            
            <h3>Meu Perfil</h3>
            <li>Na aba de meu perfil você consegue obter seus dados pessoais da conta.</li>
          </ul>
        </Modal>
      )}
    </>
  );
};

export default BotaoAjuda;

