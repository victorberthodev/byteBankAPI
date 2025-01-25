package com.api.bytebank.dto;

import com.api.bytebank.model.StatusTransacao;
import com.api.bytebank.model.TipoTransacao;
import java.time.LocalDateTime;
import java.util.UUID;

public class TransacaoDTO {
    private UUID idTransacao;
    private LocalDateTime dataTransacao;
    private TipoTransacao tipoTransacao;
    private double valor;
    private double saldoAposTransacao;
    private UUID contaOrigemId;
    private UUID contaDestinoId;

    private StatusTransacao status;

    public TransacaoDTO() {
    }

    // Getters
    public UUID getIdTransacao() {
        return idTransacao;
    }

    public LocalDateTime getDataTransacao() {
        return dataTransacao;
    }

    public TipoTransacao getTipoTransacao() {
        return tipoTransacao;
    }

    public double getValor() {
        return valor;
    }

    public double getSaldoAposTransacao() {
        return saldoAposTransacao;
    }

    public UUID getContaOrigemId() {
        return contaOrigemId;
    }

    public UUID getContaDestinoId() {
        return contaDestinoId;
    }

    public StatusTransacao getStatus() {
        return status;
    }

    // Setters
    public void setIdTransacao(UUID idTransacao) {
        this.idTransacao = idTransacao;
    }

    public void setDataTransacao(LocalDateTime dataTransacao) {
        this.dataTransacao = dataTransacao;
    }

    public void setTipoTransacao(TipoTransacao tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public void setSaldoAposTransacao(double saldoAposTransacao) {
        this.saldoAposTransacao = saldoAposTransacao;
    }

    public void setContaOrigemId(UUID contaOrigemId) {
        this.contaOrigemId = contaOrigemId;
    }

    public void setContaDestinoId(UUID contaDestinoId) {
        this.contaDestinoId = contaDestinoId;
    }

    public void setStatus(StatusTransacao status) {
        this.status = status;
    }
}
