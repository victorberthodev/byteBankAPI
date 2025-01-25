package com.api.bytebank.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "contas")
public class Conta {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id_conta", updatable = false, nullable = false)
    private UUID idConta;

    @Column(nullable = false)
    private String nomeTitular;

    @Column(nullable = false, unique = true)
    private String cpfTitular;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoConta tipoConta;

    @Column(nullable = false, unique = true)
    private String numeroConta;

    @Column(nullable = false)
    private String agencia = "0001";

    @Column(nullable = false)
    private double saldo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusConta status;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(nullable = true)
    private LocalDateTime dataAtualizacao;

    public void setNomeTitular(String nomeTitular) {
        this.nomeTitular = nomeTitular != null ? nomeTitular.toLowerCase() : null;
    }

    public void setCpfTitular(String cpfTitular) {
        this.cpfTitular = cpfTitular != null ? cpfTitular.toLowerCase() : null;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta != null ? numeroConta.toLowerCase() : null;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia != null ? agencia.toLowerCase() : null;
    }

    public void setIdConta(UUID idConta) {
        this.idConta = idConta;
    }

    public void setTipoConta(TipoConta tipoConta) {
        this.tipoConta = tipoConta;
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
        this.dataAtualizacao = dataAtualizacao;
    }


}
