package com.api.bytebank.controller;

import com.api.bytebank.dto.AtualizarContaDTO;
import com.api.bytebank.dto.ContaDTO;
import com.api.bytebank.model.Conta;
import com.api.bytebank.service.ContaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/contas")
@Tag(name = "ContaController", description = "Controlador para operações de contas bancárias")
public class ContaController {

    private final ContaService contaService;

    public ContaController(ContaService contaService) {
        this.contaService = contaService;
    }

    @PostMapping
    @Operation(summary = "Cria uma nova conta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Conta criada com sucesso",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Conta.class))}),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    public ResponseEntity<Conta> criarConta(@RequestBody Conta conta) {
        Conta novaConta = contaService.criarConta(conta.getNomeTitular(), conta.getCpfTitular(), conta.getTipoConta());
        return new ResponseEntity<>(novaConta, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Lista todas as contas")
    public ResponseEntity<List<ContaDTO>> listarTodasContas() {
        List<ContaDTO> contasDTO = contaService.listarTodasContas();
        return ResponseEntity.ok(contasDTO);
    }


    @GetMapping("/{id}")
    @Operation(summary = "Obtém uma conta por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conta encontrada",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ContaDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Conta não encontrada")
    })
    public ResponseEntity<ContaDTO> obterContaPorId(@PathVariable UUID id) {
        ContaDTO contaDTO = contaService.obterContaPorId(id);
        return ResponseEntity.ok(contaDTO);
    }


    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma conta existente por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conta atualizada com sucesso",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos para atualização"),
            @ApiResponse(responseCode = "404", description = "Conta não encontrada para atualização"),
            @ApiResponse(responseCode = "409", description = "Informações fornecidas violam as regras de negócio")
    })
    public ResponseEntity<ContaDTO> atualizarConta(@PathVariable UUID id, @RequestBody AtualizarContaDTO atualizarContaDTO) {
        ContaDTO contaAtualizadaDTO = contaService.atualizarConta(id, atualizarContaDTO);
        return ResponseEntity.ok(contaAtualizadaDTO);
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Encerra uma conta por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conta encerrada com sucesso",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Conta não encontrada para encerramento"),
            @ApiResponse(responseCode = "409", description = "A conta já está encerrada ou não pode ser encerrada devido a regras de negócio")
    })
    public ResponseEntity<String> deletarConta(@PathVariable UUID id) {
        contaService.deletarConta(id);
        return ResponseEntity.ok("Conta com ID " + id + " foi encerrada com sucesso.");
    }

}
