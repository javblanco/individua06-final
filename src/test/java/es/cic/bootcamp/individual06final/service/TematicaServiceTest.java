package es.cic.bootcamp.individual06final.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import es.cic.bootcamp.individual06final.dto.TematicaDto;
import es.cic.bootcamp.individual06final.enumeration.Categoria;
import es.cic.bootcamp.individual06final.helper.TematicaHelper;
import es.cic.bootcamp.individual06final.model.Tematica;
import es.cic.bootcamp.individual06final.repository.TematicaRepository;

@SpringBootTest
public class TematicaServiceTest {

    @Autowired
    private TematicaService tematicaService;

    @MockBean
    private TematicaRepository tematicaRepository;

    @MockBean
    private TematicaHelper tematicaHelper;
    
    @BeforeEach
	void setUp() throws Exception {
	}

    @Test
	void testCreate() {
        Tematica tematica = generarTematica();
        TematicaDto dto = generarDto();

        when(tematicaRepository.existsById(dto.getId())).thenReturn(false);
        when(tematicaHelper.dtoToEntity(dto)).thenReturn(tematica);
        when(tematicaRepository.save(tematica)).thenReturn(tematica);

        Long id = tematicaService.create(dto);

        assertEquals(dto.getId(), id, "Las ids no coinciden");

        verify(tematicaRepository, times(1)).existsById(dto.getId());
        verify(tematicaHelper, times(1)).dtoToEntity(dto);
        verify(tematicaRepository, times(1)).save(tematica);
    }

    public TematicaDto generarDto() {
        TematicaDto dto = new TematicaDto();

        dto.setId(1L);
        dto.setActivo(true);
        dto.setCategoria(Categoria.CIENCIAS);
        dto.setDescripcion("Descripción1");
        dto.setNombre("Nombre1");
        dto.setEliminable(false);
        dto.setListaSubtematicas(null);
        dto.setReferencia("Referencia1");
        dto.setSubtematicas("subtematicas1");

        return dto;
    }

    public Tematica generarTematica() {
        Tematica tematica = new Tematica();

        tematica.setId(1L);
        tematica.setActivo(true);
        tematica.setCategoria(Categoria.CIENCIAS);
        tematica.setDescripcion("Descripción1");
        tematica.setNombre("Nombre1");
        tematica.setReferencia("Referencia1");
        tematica.setSubtematicas("subtematicas1");

        return tematica;
    }
}
