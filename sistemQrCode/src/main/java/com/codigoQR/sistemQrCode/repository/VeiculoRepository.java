package com.codigoQR.sistemQrCode.repository;

import com.codigoQR.sistemQrCode.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeiculoRepository extends JpaRepository<Veiculo, Integer> {

    Veiculo findByPlaca(String placa);

    boolean existsByPlaca(String placa);
}