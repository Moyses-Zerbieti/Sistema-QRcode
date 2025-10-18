package com.codigoQR.sistemQrCode.serviceTest;

import com.codigoQR.sistemQrCode.dto.CargoRequest;
import com.codigoQR.sistemQrCode.exception.ResourceNotFoundException;
import com.codigoQR.sistemQrCode.model.Cargo;
import com.codigoQR.sistemQrCode.repository.CargoRepository;
import com.codigoQR.sistemQrCode.service.CargoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CargoServiceTest {

    @Mock
    CargoRepository cargoRepository;

    @InjectMocks
    CargoService cargoService;

    @Test
    void salvarCargoTest(){
        CargoRequest cargoTest = new CargoRequest();
        cargoTest.setNomeCargo("Diretor");

        Cargo cargoEntity = new Cargo();
        cargoEntity.setNomeCargo("Diretor");

        when(cargoRepository.save(any(Cargo.class))).thenReturn(cargoEntity);

        CargoRequest retorno = cargoService.salvarCargo(cargoTest);

        assertEquals("Diretor", retorno.getNomeCargo());
        verify(cargoRepository, times(1)).save(any(Cargo.class));
    }

    @Test
    void obterCargoPorId() {
        Cargo cargoTest = new Cargo();
        cargoTest.setId(1);
        cargoTest.setNomeCargo("Diretor");

        when(cargoRepository.findById(1)).thenReturn(Optional.of(cargoTest));

        Optional<Cargo> retorno = cargoService.obterPorId(1);

        assertTrue(retorno.isPresent());
        assertEquals("Diretor", retorno.get().getNomeCargo());

        verify(cargoRepository, times(1)).findById(1);
    }

    @Test
    void obterCargoPorIdNaoEncontradoTest(){
       when(cargoRepository.findById(99)).thenReturn(Optional.empty());

       Optional<Cargo> retorno = cargoService.obterPorId(99);

       assertFalse(retorno.isPresent());
       verify(cargoRepository, times(1)).findById(99);

    }

    @Test
    void excluirCargoPorId(){
        when(cargoRepository.existsById(1)).thenReturn(true);

        cargoService.excluirCargo(1);

        verify(cargoRepository,times(1)).deleteById(1);
    }

    @Test
    void excluirCargoNaoExisteTest(){
        when(cargoRepository.existsById(99)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                ()->cargoService.excluirCargo(99));

        verify(cargoRepository, never()).deleteById(anyInt());
    }
}
