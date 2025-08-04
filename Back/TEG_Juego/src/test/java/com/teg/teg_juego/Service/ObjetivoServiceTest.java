package com.teg.teg_juego.Service;


import com.teg.teg_juego.Repository.objetivoRepository;
import com.teg.teg_juego.model.DTO.objetivoDTO;
import com.teg.teg_juego.model.entities.Objetivo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ObjetivoServiceTest {

    @Mock
    private objetivoRepository objetivoRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ObjetivoService objetivoService;

    @Test
    void getAllObjetivos_returnsMappedDTOs() {
        Objetivo obj1 = new Objetivo();
        obj1.setId(1);
        obj1.setDescripcion("Objetivo 1");

        Objetivo obj2 = new Objetivo();
        obj2.setId(2);
        obj2.setDescripcion("Objetivo 2");

        when(objetivoRepository.findAll()).thenReturn(Arrays.asList(obj1, obj2));

        objetivoDTO dto1 = new objetivoDTO();
        dto1.setId(1);
        dto1.setDescripcion("Objetivo 1");

        objetivoDTO dto2 = new objetivoDTO();
        dto2.setId(2);
        dto2.setDescripcion("Objetivo 2");

        when(modelMapper.map(obj1, objetivoDTO.class)).thenReturn(dto1);
        when(modelMapper.map(obj2, objetivoDTO.class)).thenReturn(dto2);

        List<objetivoDTO> result = objetivoService.getAllObjetivos();

        assertEquals(2, result.size());
        assertEquals("Objetivo 1", result.get(0).getDescripcion());
        assertEquals("Objetivo 2", result.get(1).getDescripcion());
    }

    @Test
    void getObjetivoById_existingId_returnsDTO() {
        Objetivo obj = new Objetivo();
        obj.setId(5);
        obj.setDescripcion("Defender Oceanía");

        when(objetivoRepository.findById(5)).thenReturn(Optional.of(obj));

        objetivoDTO dto = new objetivoDTO();
        dto.setId(5);
        dto.setDescripcion("Defender Oceanía");

        when(modelMapper.map(obj, objetivoDTO.class)).thenReturn(dto);

        objetivoDTO result = objetivoService.getObjetivoById(5);

        assertEquals(5, result.getId());
        assertEquals("Defender Oceanía", result.getDescripcion());
    }

    @Test
    void getObjetivoById_nonExistingId_throwsException() {
        when(objetivoRepository.findById(999)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            objetivoService.getObjetivoById(999);
        });

        assertEquals("Objetivo no encontrado", ex.getMessage());
    }
}