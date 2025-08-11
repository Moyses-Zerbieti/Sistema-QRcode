package com.codigoQR.sistemQrCode.repository;

import com.codigoQR.sistemQrCode.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {
    boolean existsByCpf(String cpf);
    boolean existsByEmailCorporativo(String emailCorporativo);

    Funcionario findByCpf(String cpf);
    Funcionario findByEmailCorporativo(String emailCorporativo);
}