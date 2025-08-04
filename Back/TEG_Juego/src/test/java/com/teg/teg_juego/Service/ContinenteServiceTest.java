package com.teg.teg_juego.Service;


import com.teg.teg_juego.Repository.continenteRepository;
import com.teg.teg_juego.model.entities.Continente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContinenteServiceTest {

    @Mock
    private continenteRepository ContinenteRepository;

    @InjectMocks
    private ContinenteService continenteService;

    private Continente continente1;
    private Continente continente2;
    private List<Continente> continentesList;

    @BeforeEach
    void setUp() {
        // Configuración de datos de prueba
        continente1 = new Continente();
        continente1.setId(1);
        continente1.setNombre("América");

        continente2 = new Continente();
        continente2.setId(2);
        continente2.setNombre("Europa");

        continentesList = Arrays.asList(continente1, continente2);
    }

    @Test
    void testGetAll_ShouldReturnListOfContinentes_WhenContinentesExist() {
        // Arrange
        when(ContinenteRepository.findAll()).thenReturn(continentesList);

        // Act
        List<Continente> result = continenteService.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("América", result.get(0).getNombre());
        assertEquals("Europa", result.get(1).getNombre());

        // Verificar que el método del repositorio fue llamado exactamente una vez
        verify(ContinenteRepository, times(1)).findAll();
    }

    @Test
    void testGetAll_ShouldReturnEmptyList_WhenNoContinentesExist() {
        // Arrange
        when(ContinenteRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<Continente> result = continenteService.getAll();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        assertEquals(0, result.size());

        // Verificar que el método del repositorio fue llamado exactamente una vez
        verify(ContinenteRepository, times(1)).findAll();
    }


    @Test
    void testGetAll_ShouldVerifyRepositoryInteraction() {
        // Arrange
        when(ContinenteRepository.findAll()).thenReturn(continentesList);

        // Act
        continenteService.getAll();

        // Assert - Verificar que no hay más interacciones con el mock
        verify(ContinenteRepository, times(1)).findAll();
        verifyNoMoreInteractions(ContinenteRepository);
    }

    @Test
    void testServiceInstantiation() {
        // Assert - Verificar que el servicio se instancia correctamente
        assertNotNull(continenteService);
    }
}