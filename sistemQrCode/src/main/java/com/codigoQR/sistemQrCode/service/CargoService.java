package com.codigoQR.sistemQrCode.service;

import com.codigoQR.sistemQrCode.exception.ResourceNotFoundException;
import com.codigoQR.sistemQrCode.model.Cargo;
import com.codigoQR.sistemQrCode.repository.CargoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CargoService {
    private CargoRepository cargoRepository;

    public CargoService (CargoRepository cargoRepository){
        this.cargoRepository=cargoRepository;
    }

    public Cargo salvarCargo(Cargo cargo){
        return cargoRepository.save(cargo);
    }

    public Optional<Cargo> obterPorId(Integer id){
        return cargoRepository.findById(id);
    }

    public void excluirCargo(Integer id){
        if(!cargoRepository.existsById(id)){
            throw new ResourceNotFoundException("Cargo com id:" + id + "não encontrado");
        }
        cargoRepository.deleteById(id);
        }
}