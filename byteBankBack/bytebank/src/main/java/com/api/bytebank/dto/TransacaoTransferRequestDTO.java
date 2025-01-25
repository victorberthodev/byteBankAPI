package com.api.bytebank.dto;

import java.util.UUID;

public class TransacaoTransferRequestDTO {
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
