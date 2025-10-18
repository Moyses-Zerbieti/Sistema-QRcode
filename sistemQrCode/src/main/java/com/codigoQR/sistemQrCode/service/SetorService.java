package com.codigoQR.sistemQrCode.service;

import com.codigoQR.sistemQrCode.dto.SetorRequest;
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

    public SetorRequest salvarSetor(SetorRequest setorDTO){
        Setor novoSetor = new Setor();
        novoSetor.setNomeSetor(setorDTO.getNomeSetor());

        Setor salvo = setorRepository.save(novoSetor);

        SetorRequest request = new SetorRequest();
        request.setNomeSetor(novoSetor.getNomeSetor());

        return request;
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