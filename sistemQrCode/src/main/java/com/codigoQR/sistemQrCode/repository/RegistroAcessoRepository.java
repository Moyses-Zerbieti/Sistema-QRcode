package com.codigoQR.sistemQrCode.repository;

import com.codigoQR.sistemQrCode.model.RegistroAcessoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RegistroAcessoRepository extends JpaRepository <RegistroAcessoEntity, Integer> {
    @Query("SELECT r " +
            "FROM RegistroAcessoEntity r" +
            " WHERE r.funcionario.cpf = :cpf" +
            " ORDER BY r.dataHoraAcesso DESC")
    Optional<RegistroAcessoEntity> findUltimoAcesso(String cpf);
}