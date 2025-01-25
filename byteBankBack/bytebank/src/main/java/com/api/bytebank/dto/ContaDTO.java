package com.api.bytebank.dto;

import com.api.bytebank.model.StatusConta;
import com.api.bytebank.model.TipoConta;

import java.time.LocalDateTime;
import java.util.UUID;

public class ContaDTO {

    private UUID idConta;
    private String nomeTitular;
    private String cpfTitular;
    private TipoConta tipoConta;
    private String numeroConta;
    private String agencia;
    private double saldo;
    private StatusConta status;
    private LocalDateTime dataCriacao;

    private LocalDateTime dataAtualizacao;


    // Construtores, Getters e Setters

    public ContaDTO() {
    }

    // Getters

    public UUID getIdConta() {
        return idConta;
    }

    public String getNomeTitular() {
        return nomeTitular;
    }

    public String getCpfTitular() {
        return cpfTitular;
    }

    public TipoConta getTipoConta() {
        return tipoConta;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public String getAgencia() {
        return agencia;
    }

    public double getSaldo() {
        return saldo;
    }

    public StatusConta getStatus() {
        return status;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;}


    // Setters

    public void setIdConta(UUID idConta) {
        this.idConta = idConta;
    }

    public void setNomeTitular(String nomeTitular) {
        this.nomeTitular = nomeTitular;
    }

    public void setCpfTitular(String cpfTitular) {
        this.cpfTitular = cpfTitular;
    }

    public void setTipoConta(TipoConta tipoConta) {
        this.tipoConta = tipoConta;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public void setStatus(StatusConta status) {
        this.status = status;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;}

}
