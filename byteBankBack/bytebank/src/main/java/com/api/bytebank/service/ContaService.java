package com.api.bytebank.service;

import com.api.bytebank.dto.AtualizarContaDTO;
import com.api.bytebank.dto.ContaDTO;
import com.api.bytebank.model.Conta;
import com.api.bytebank.model.StatusConta;
import com.api.bytebank.model.TipoConta;
import com.api.bytebank.repository.ContaRepository;
import com.api.bytebank.utils.CpfValidator;

import org.springframework.stereotype.Service;
import com.api.bytebank.exception.ServiceException;
import com.api.bytebank.repository.TransacaoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ContaService {

    private final ContaRepository contaRepository;
    private final TransacaoRepository transacaoRepository;

    private static final int NOME_MAX_LENGTH = 60;
    private static final double SALDO_INICIAL = 0.0;

    public ContaService(ContaRepository contaRepository, TransacaoRepository transacaoRepository) {
        this.contaRepository = contaRepository;
        this.transacaoRepository = transacaoRepository;
    }

    public Conta criarConta(String nomeTitular, String cpfTitular, TipoConta tipoConta) {
        if (nomeTitular == null || nomeTitular.trim().isEmpty()) {
            throw new ServiceException("O nome do titular é obrigatório.");
        }

        cpfTitular = cpfTitular != null ? cpfTitular.replaceAll("[^0-9]", "") : null;
        if (cpfTitular == null || cpfTitular.trim().isEmpty()) {
            throw new ServiceException("O CPF do titular é obrigatório.");
        }
        if (tipoConta == null) {
            throw new ServiceException("O tipo de conta é obrigatório.");
        }

        if (nomeTitular.length() > NOME_MAX_LENGTH) {
            throw new ServiceException("O nome do titular não pode exceder " + NOME_MAX_LENGTH + " caracteres.");
        }

        if (!CpfValidator.isCpfValido(cpfTitular)) {
            throw new ServiceException("CPF inválido.");
        }

        if (!nomeTitular.matches("^[A-Za-z\\s]+$")) {
            throw new ServiceException("O nome do titular deve conter apenas letras e espaços.");
        }

        if (contaRepository.findByCpfTitularAndTipoConta(cpfTitular, tipoConta).isPresent()) {
            throw new ServiceException("Já existe uma conta para este CPF com o tipo especificado.");
        }

        String numeroConta = generateAccountNumber();

        Conta conta = new Conta();
        conta.setNomeTitular(nomeTitular.toLowerCase());
        conta.setCpfTitular(cpfTitular);
        conta.setTipoConta(tipoConta);
        conta.setNumeroConta(numeroConta);
        conta.setSaldo(0.0);
        conta.setStatus(StatusConta.ativa);
        conta.setDataCriacao(LocalDateTime.now());

        return contaRepository.save(conta);
    }



    private String generateAccountNumber() {
        int accountNumber = (int) (Math.random() * 1_000_000_000);
        return String.format("%010d", accountNumber);
    }


    public List<ContaDTO> listarTodasContas() {
        return contaRepository.findAll().stream()
                .map(this::convertToContaDTO)
                .collect(Collectors.toList());
    }

    private ContaDTO convertToContaDTO(Conta conta) {
        ContaDTO dto = new ContaDTO();
        dto.setIdConta(conta.getIdConta());
        dto.setNomeTitular(conta.getNomeTitular());
        dto.setCpfTitular(conta.getCpfTitular());
        dto.setTipoConta(conta.getTipoConta());
        dto.setNumeroConta(conta.getNumeroConta());
        dto.setAgencia(conta.getAgencia());
        dto.setSaldo(conta.getSaldo());
        dto.setStatus(conta.getStatus());
        dto.setDataCriacao(conta.getDataCriacao());
        dto.setDataAtualizacao(conta.getDataAtualizacao());

        return dto;
    }

    public ContaDTO obterContaPorId(UUID id) {
        Conta conta = contaRepository.findById(id)
                .orElseThrow(() -> new ServiceException("Conta não encontrada."));
        return convertToContaDTO(conta);
    }

    public ContaDTO atualizarConta(UUID id, AtualizarContaDTO atualizarContaDTO) {
        Conta contaExistente = contaRepository.findById(id)
                .orElseThrow(() -> new ServiceException("Conta não encontrada."));

        if (atualizarContaDTO.getNomeTitular() != null && !atualizarContaDTO.getNomeTitular().trim().isEmpty()) {
            if (!atualizarContaDTO.getNomeTitular().matches("^[A-Za-z\\s]+$")) {
                throw new ServiceException("O nome do titular deve conter apenas letras e espaços.");
            }
            if (atualizarContaDTO.getNomeTitular().length() > NOME_MAX_LENGTH) {
                throw new ServiceException("O nome do titular não pode exceder " + NOME_MAX_LENGTH + " caracteres.");
            }
            contaExistente.setNomeTitular(atualizarContaDTO.getNomeTitular());
        }

        if (atualizarContaDTO.getStatus() == StatusConta.encerrada && contaExistente.getSaldo() > 0) {
            throw new ServiceException("Não é possível encerrar a conta com saldo positivo.");
        }

        if (atualizarContaDTO.getStatus() != null) {
            contaExistente.setStatus(atualizarContaDTO.getStatus());
        }

        Conta contaAtualizada = contaRepository.save(contaExistente);
        return convertToContaDTO(contaAtualizada);
    }



    public void deletarConta(UUID id) {
        Conta conta = contaRepository.findById(id)
                .orElseThrow(() -> new ServiceException("Conta não encontrada."));

        if (conta.getStatus() == StatusConta.encerrada) {
            throw new ServiceException("A conta com ID " + id + " já está encerrada.");
        }

        if (conta.getSaldo() > 0) {
            throw new ServiceException("Não é possível encerrar a conta com ID " + id + " porque ela possui saldo positivo.");
        }

        conta.setStatus(StatusConta.encerrada);

        contaRepository.save(conta);
}

}