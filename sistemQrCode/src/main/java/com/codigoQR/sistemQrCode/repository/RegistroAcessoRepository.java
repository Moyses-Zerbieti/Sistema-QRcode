package com.codigoQR.sistemQrCode.repository;

import com.codigoQR.sistemQrCode.model.RegistroAcesso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface RegistroAcessoRepository extends JpaRepository <RegistroAcesso, Integer> {
    @Query(value = "SELECT * FROM registro_acesso WHERE funcionario_id = :id ORDER BY data_hora_acesso DESC LIMIT 1", nativeQuery = true)
    Optional<RegistroAcesso> findUltimoAcesso(Integer id);
}