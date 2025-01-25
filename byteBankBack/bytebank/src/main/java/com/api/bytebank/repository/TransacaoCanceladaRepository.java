package com.api.bytebank.repository;

import com.api.bytebank.model.TransacaoCancelada;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface TransacaoCanceladaRepository extends JpaRepository<TransacaoCancelada, UUID> {
}
