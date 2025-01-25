package com.api.bytebank.service;

import com.api.bytebank.dto.DepositoRequestDTO;
import com.api.bytebank.dto.SaqueRequestDTO;
import com.api.bytebank.dto.TransacaoDTO;
import com.api.bytebank.dto.TransacaoTransferRequestDTO;
import com.api.bytebank.model.*;
import com.api.bytebank.repository.ContaRepository;
import com.api.bytebank.repository.TransacaoCanceladaRepository;
import com.api.bytebank.repository.TransacaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.api.bytebank.exception.ServiceException;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.List;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final ContaRepository contaRepository;
    private final TransacaoCanceladaRepository transacaoCanceladaRepository;

    private static final double MAX_TRANSACTION_PERCENTAGE = 0.5; // 50%

    public TransacaoService(TransacaoRepository transacaoRepository,
                            ContaRepository contaRepository,
                            TransacaoCanceladaRepository transacaoCanceladaRepository) {
        this.transacaoRepository = transacaoRepository;
        this.contaRepository = contaRepository;
        this.transacaoCanceladaRepository = transacaoCanceladaRepository;
    }

    //

    public TransacaoDTO realizarDeposito(UUID idConta, DepositoRequestDTO depositoRequestDTO) {
        final double LIMITE_MAXIMO_DEPOSITO = 10000.00;
        double valor = depositoRequestDTO.getValor();

        if (valor <= 0 || valor > LIMITE_MAXIMO_DEPOSITO) {
            throw new ServiceException("Valor do depósito inválido ou excede o limite máximo permitido.");
        }

        Conta conta = contaRepository.findById(idConta).orElseThrow(
                () -> new ServiceException("Conta não encontrada.")
        );

        if (conta.getStatus() != StatusConta.ativa) {
            throw new ServiceException("A conta não está ativa para receber depósitos.");
        }

        conta.setSaldo(conta.getSaldo() + valor);
        contaRepository.save(conta);

        Transacao transacao = new Transacao();
        transacao.setDataTransacao(LocalDateTime.now());
        transacao.setTipoTransacao(TipoTransacao.deposito);
        transacao.setValor(valor);
        transacao.setSaldoAposTransacao(conta.getSaldo());
        transacao.setContaDestino(conta);

        Transacao savedTransacao = transacaoRepository.save(transacao);
        return convertToTransacaoDTO(savedTransacao);
    }



    public TransacaoDTO realizarSaque(UUID idConta, SaqueRequestDTO saqueRequestDTO) {
        final double LIMITE_POR_SAQUE = 1000.00;
        double valor = saqueRequestDTO.getValor();

        if (valor <= 0 || valor > LIMITE_POR_SAQUE) {
            throw new ServiceException("Valor do saque inválido ou excede o limite por saque.");
        }

        Conta conta = contaRepository.findById(idConta).orElseThrow(
                () -> new ServiceException("Conta não encontrada.")
        );

        if (conta.getStatus() != StatusConta.ativa) {
            throw new ServiceException("A conta não está ativa para realizar saques.");
        }

        if (valor > conta.getSaldo()) {
            throw new ServiceException("O valor do saque não pode ser maior que o saldo da conta.");
        }

        conta.setSaldo(conta.getSaldo() - valor);
        contaRepository.save(conta);

        Transacao transacao = new Transacao();
        transacao.setDataTransacao(LocalDateTime.now());
        transacao.setTipoTransacao(TipoTransacao.saque);
        transacao.setValor(valor);
        transacao.setSaldoAposTransacao(conta.getSaldo());
        transacao.setContaOrigem(conta);
        transacao.setContaDestino(null);

        Transacao savedTransacao = transacaoRepository.save(transacao);
        return convertToTransacaoDTO(savedTransacao);
    }

    public TransacaoDTO realizarTransferencia(UUID idContaOrigem, TransacaoTransferRequestDTO transferRequestDTO) {
        if (idContaOrigem.equals(transferRequestDTO.getIdContaDestino())) {
            throw new ServiceException("A conta de origem e destino não podem ser as mesmas.");
        }

        double valor = transferRequestDTO.getValor();
        if (valor <= 0) {
            throw new ServiceException("O valor da transferência deve ser positivo.");
        }

        Conta contaOrigem = contaRepository.findById(idContaOrigem)
                .orElseThrow(() -> new ServiceException("Conta de origem não encontrada."));
        Conta contaDestino = contaRepository.findById(transferRequestDTO.getIdContaDestino())
                .orElseThrow(() -> new ServiceException("Conta de destino não encontrada."));

        if (contaOrigem.getStatus() != StatusConta.ativa || contaDestino.getStatus() != StatusConta.ativa) {
            throw new ServiceException("Ambas as contas devem estar ativas para realizar a transferência.");
        }

        if (valor > contaOrigem.getSaldo()) {
            throw new ServiceException("O valor da transferência não pode ser maior que o saldo da conta de origem.");
        }

        if (valor > contaOrigem.getSaldo() * MAX_TRANSACTION_PERCENTAGE) {
            throw new ServiceException("O valor da transferência não pode exceder 50% do saldo total da conta de origem.");
        }

        contaOrigem.setSaldo(contaOrigem.getSaldo() - valor);
        contaDestino.setSaldo(contaDestino.getSaldo() + valor);

        contaRepository.save(contaOrigem);
        contaRepository.save(contaDestino);

        Transacao transacao = new Transacao();
        transacao.setIdTransacao(UUID.randomUUID());
        transacao.setDataTransacao(LocalDateTime.now());
        transacao.setTipoTransacao(TipoTransacao.transferencia);
        transacao.setValor(valor);
        transacao.setSaldoAposTransacao(contaOrigem.getSaldo());
        transacao.setContaOrigem(contaOrigem);
        transacao.setContaDestino(contaDestino);

        Transacao savedTransacao = transacaoRepository.save(transacao);
        return convertToTransacaoDTO(savedTransacao);
    }


    public List<TransacaoDTO> obterExtrato(UUID idConta) {

        List<Transacao> extrato = transacaoRepository.findByContaOrigem_IdOrContaDestinoId(idConta, idConta);

        return extrato.stream()
                .map(this::convertToTransacaoDTO)
                .sorted(Comparator.comparing(TransacaoDTO::getDataTransacao))
                .collect(Collectors.toList());
    }

    private TransacaoDTO convertToTransacaoDTO(Transacao transacao) {
        TransacaoDTO dto = new TransacaoDTO();
        dto.setIdTransacao(transacao.getIdTransacao());
        dto.setDataTransacao(transacao.getDataTransacao());
        dto.setTipoTransacao(transacao.getTipoTransacao());
        dto.setValor(transacao.getValor());
        dto.setSaldoAposTransacao(transacao.getSaldoAposTransacao());
        dto.setContaOrigemId(transacao.getContaOrigem() != null ? transacao.getContaOrigem().getIdConta() : null);
        dto.setContaDestinoId(transacao.getContaDestino() != null ? transacao.getContaDestino().getIdConta() : null);
        dto.setStatus(transacao.getStatus());
        return dto;
    }

    @Transactional
    public void cancelarTransacao(UUID idTransacao) {
        Transacao transacao = transacaoRepository.findById(idTransacao)
                .orElseThrow(() -> new ServiceException("Transação não encontrada."));

        transacao.setStatus(StatusTransacao.cancelada);

        Conta contaOrigem = transacao.getContaOrigem();
        Conta contaDestino = transacao.getContaDestino();

        // Reverte a transação
        switch (transacao.getTipoTransacao()) {
            case deposito:
                if (contaDestino != null) {
                    contaDestino.setSaldo(contaDestino.getSaldo() - transacao.getValor());
                    if (contaDestino.getSaldo() < 0) {
                        throw new ServiceException("Cancelamento impossível: Saldo negativo resultante.");
                    }
                    contaRepository.save(contaDestino);
                }
                break;
            case saque:
                if (contaOrigem != null) {
                    contaOrigem.setSaldo(contaOrigem.getSaldo() + transacao.getValor());
                    contaRepository.save(contaOrigem);
                }
                break;
            case transferencia:
                if (contaOrigem != null && contaDestino != null) {
                    // Reverte a operação de transferência
                    contaOrigem.setSaldo(contaOrigem.getSaldo() + transacao.getValor());
                    contaDestino.setSaldo(contaDestino.getSaldo() - transacao.getValor());
                    contaRepository.save(contaOrigem);
                    contaRepository.save(contaDestino);
                }
                break;
            default:
                throw new ServiceException("Tipo de transação desconhecido.");
        }

        TransacaoCancelada transacaoCancelada = new TransacaoCancelada();
        transacaoCancelada.setTransacao(transacao);
        transacaoCancelada.setDataCancelamento(LocalDateTime.now());

        transacaoCanceladaRepository.save(transacaoCancelada);

    }

}