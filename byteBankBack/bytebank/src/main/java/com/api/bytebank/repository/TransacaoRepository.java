package com.api.bytebank.repository;

import com.api.bytebank.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TransacaoRepository extends JpaRepository<Transacao, UUID> {

    @Query("SELECT t FROM Transacao t WHERE t.contaOrigem.idConta = :idConta OR t.contaDestino.idConta = :idConta")
    List<Transacao> findByContaOrigem_IdOrContaDestinoId(@Param("idConta") UUID contaOrigemId, @Param("idConta") UUID contaDestinoId);
}
