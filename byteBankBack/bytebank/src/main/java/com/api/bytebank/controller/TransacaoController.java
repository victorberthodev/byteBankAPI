package com.api.bytebank.controller;

import com.api.bytebank.dto.DepositoRequestDTO;
import com.api.bytebank.dto.SaqueRequestDTO;
import com.api.bytebank.dto.TransacaoDTO;
import com.api.bytebank.dto.TransacaoTransferRequestDTO;
import com.api.bytebank.exception.ServiceException;
import com.api.bytebank.model.Transacao;
import com.api.bytebank.service.TransacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/contas")
public class TransacaoController {

    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @PostMapping("/{idConta}/depositar")
    @Operation(summary = "Depositar valor em uma conta")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Depósito realizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Valor do depósito inválido ou excede o limite máximo permitido"),
            @ApiResponse(responseCode = "404", description = "Conta não encontrada"),
            @ApiResponse(responseCode = "409", description = "A conta não está ativa para receber depósitos")
    })
    public ResponseEntity<TransacaoDTO> depositar(@PathVariable UUID idConta, @RequestBody DepositoRequestDTO depositoRequestDTO) {
        TransacaoDTO transacaoDTO = transacaoService.realizarDeposito(idConta, depositoRequestDTO);
        return ResponseEntity.ok(transacaoDTO);
    }

    @PostMapping("/{idConta}/sacar")
    @Operation(summary = "Sacar valor de uma conta")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Saque realizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Valor do saque inválido ou excede o limite diário"),
            @ApiResponse(responseCode = "404", description = "Conta não encontrada"),
            @ApiResponse(responseCode = "409", description = "A conta não está ativa para realizar saques ou o saldo é insuficiente")
    })
    public ResponseEntity<TransacaoDTO> sacar(@PathVariable UUID idConta, @RequestBody SaqueRequestDTO saqueRequestDTO) {
        TransacaoDTO transacaoDTO = transacaoService.realizarSaque(idConta, saqueRequestDTO);
        return ResponseEntity.ok(transacaoDTO);
    }

    @PostMapping("/{idConta}/transferir")
    @Operation(summary = "Transferir valor de uma conta para outra")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transferência realizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Valor da transferência inválido ou excede o limite"),
            @ApiResponse(responseCode = "404", description = "Conta de origem ou destino não encontrada"),
            @ApiResponse(responseCode = "409", description = "Uma ou ambas as contas não estão ativas ou o saldo é insuficiente")
    })
    public ResponseEntity<TransacaoDTO> transferir(@PathVariable UUID idConta, @RequestBody TransacaoTransferRequestDTO transferRequestDTO) {
        TransacaoDTO transacaoDTO = transacaoService.realizarTransferencia(idConta, transferRequestDTO);
        return ResponseEntity.ok(transacaoDTO);
    }

    @GetMapping("/{idConta}/extrato")
    @Operation(summary = "Obter extrato de transações de uma conta")
    public ResponseEntity<List<TransacaoDTO>> extrato(@PathVariable UUID idConta) {
        List<TransacaoDTO> extratoDTO = transacaoService.obterExtrato(idConta);
        return ResponseEntity.ok(extratoDTO);
    }


    @DeleteMapping("/{idTransacao}/cancelar")
    @Operation(summary = "Cancelar uma transação específica")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transação cancelada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Não foi possível cancelar a transação"),
            @ApiResponse(responseCode = "404", description = "Transação não encontrada")
    })
    public ResponseEntity<String> cancelarTransacao(@PathVariable UUID idTransacao) {
        try {
            transacaoService.cancelarTransacao(idTransacao);
            return ResponseEntity.ok("Transação com ID " + idTransacao + " cancelada com sucesso.");
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }







    public static class TransacaoRequest {
        private double valor;

        // Getters e setters
        public double getValor() {
            return valor;
        }

        public void setValor(double valor) {
            this.valor = valor;
        }
    }

    public static class TransacaoTransferRequest {
        private UUID idContaDestino;
        private double valor;

        // Getters e setters
        public UUID getIdContaDestino() {
            return idContaDestino;
        }

        public void setIdContaDestino(UUID idContaDestino) {
            this.idContaDestino = idContaDestino;
        }

        public double getValor() {
            return valor;
        }

        public void setValor(double valor) {
            this.valor = valor;
        }
    }
}
