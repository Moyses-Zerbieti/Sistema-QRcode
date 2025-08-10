package com.codigoQR.sistemQrCode.service;

import com.codigoQR.sistemQrCode.exception.ResourceNotFoundException;
import com.codigoQR.sistemQrCode.model.Setor;
import com.codigoQR.sistemQrCode.repository.SetorRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class SetorService {

    private SetorRepository setorRepository;

    public SetorService(SetorRepository setorRepository) {
        this.setorRepository = setorRepository;
    }

    public Setor salvarSetor(Setor setor){
        return setorRepository.save(setor);
    }

    public Optional<Setor> obterPorId(Integer id){
        return setorRepository.findById(id);
    }

    public void excluirSetor(Integer id){
        if(!setorRepository.existsById(id)){
            throw new ResourceNotFoundException("Setor com id:" + id + "não encontrado");
        }
        setorRepository.deleteById(id);
    }
}