package com.codigoQR.sistemQrCode.repository;
import com.codigoQR.sistemQrCode.model.FuncionarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionariosRepository extends JpaRepository<FuncionarioEntity, Integer> {

}
