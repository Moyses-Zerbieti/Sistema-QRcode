package com.codigoQR.sistemQrCode.serviceTest;

import com.codigoQR.sistemQrCode.dto.SetorRequest;
import com.codigoQR.sistemQrCode.exception.ResourceNotFoundException;
import com.codigoQR.sistemQrCode.model.Setor;
import com.codigoQR.sistemQrCode.repository.SetorRepository;
import com.codigoQR.sistemQrCode.service.SetorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SetorServiceTest {

    @Mock
    SetorRepository setorRepository;

    @InjectMocks
    SetorService setorService;

    @Test
    void salvarSetorTest(){
        SetorRequest setorTest = new SetorRequest();
        setorTest.setNomeSetor("Financeiro");

        Setor setorEntity = new Setor();
        setorEntity.setNomeSetor("Financeiro");

        when(setorRepository.save(any(Setor.class))).thenReturn(setorEntity);

        SetorRequest retorno = this.setorService.salvarSetor(setorTest);

        assertEquals("Financeiro", retorno.getNomeSetor());
        verify(setorRepository, times(1)).save(any(Setor.class));
    }

    @Test
    void obterSetorPorIdTest(){
        Setor setorTest = new Setor();
        setorTest.setId(1);
        setorTest.setNomeSetor("Financeiro");

        when(setorRepository.findById(1)).thenReturn(Optional.of(setorTest));

        Optional<Setor> retorno = setorService.obterPorId(1);

        assertTrue(retorno.isPresent());
        assertEquals("Financeiro", retorno.get().getNomeSetor());

        verify(setorRepository, times(1)).findById(1);
    }

    @Test
    void obterSetorPorIdNaoEncontradoTest(){

        when(setorRepository.findById(99)).thenReturn(Optional.empty());

        Optional<Setor> retorno = setorService.obterPorId(99);

        assertFalse(retorno.isPresent());
        verify(setorRepository, times(1)).findById(99);
     }

     @Test
    void excluirSetorTest(){
        when(setorRepository.existsById(1)).thenReturn(true);

        setorService.excluirSetor(1);

        verify(setorRepository,times(1)).deleteById(1);
     }

     @Test
    void excluirSetorNaoExisteTest(){
        when(setorRepository.existsById(99)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                ()-> setorService.excluirSetor(99));

        verify(setorRepository, never()).deleteById(anyInt());
    }
}