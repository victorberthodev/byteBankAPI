package com.api.bytebank.dto;

import com.api.bytebank.model.StatusConta;

public class AtualizarContaDTO {
    private String nomeTitular;
    private StatusConta status;

    // Getters e Setters
    public String getNomeTitular() {
        return nomeTitular;
    }

    public void setNomeTitular(String nomeTitular) {
        this.nomeTitular = nomeTitular;
    }

    public StatusConta getStatus() {
        return status;
    }

    public void setStatus(StatusConta status) {
        this.status = status;
    }
}
