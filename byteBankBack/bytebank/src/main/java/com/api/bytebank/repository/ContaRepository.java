package com.api.bytebank.repository;

import com.api.bytebank.model.Conta;
import com.api.bytebank.model.TipoConta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface ContaRepository extends JpaRepository<Conta, UUID> {
    Optional<Conta> findByCpfTitularAndTipoConta(String cpfTitular, TipoConta tipoConta);
}
