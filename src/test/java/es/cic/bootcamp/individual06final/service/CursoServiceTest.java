package es.cic.bootcamp.individual06final.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import es.cic.bootcamp.individual06final.dto.CursoDto;
import es.cic.bootcamp.individual06final.enumeration.Categoria;
import es.cic.bootcamp.individual06final.helper.CursoHelper;
import es.cic.bootcamp.individual06final.model.Curso;
import es.cic.bootcamp.individual06final.model.Tematica;
import es.cic.bootcamp.individual06final.repository.CursoRepository;
import es.cic.bootcamp.individual06final.repository.TematicaRepository;

@SpringBootTest
public class CursoServiceTest {
    
    @Autowired
    private CursoService cursoService;

    @MockBean
    private CursoRepository cursoRepository;

    @MockBean
    private CursoHelper cursoHelper;

    @MockBean
    private TematicaRepository tematicaRepository;

    @BeforeEach
	void setUp() throws Exception {
	}

    @Test
	void testCreate() {
        Tematica tematica = generarTematica();
        Optional<Tematica> optional = Optional.of(tematica);
        Curso curso = generarCurso(tematica);
        CursoDto dto = generarCursoDto(tematica);

        when(tematicaRepository.findById(dto.getIdTematica())).thenReturn(optional);
        when(cursoRepository.existsById(dto.getId())).thenReturn(false);
        when(cursoHelper.dtoToEntity(dto, tematica)).thenReturn(curso);
        when(cursoRepository.save(curso)).thenReturn(curso);

        Long id = cursoService.create(dto);

        assertEquals(dto.getId(), id, "Los ids no coinciden");

        verify(tematicaRepository, times(1)).findById(dto.getIdTematica());
        verify(cursoRepository, times(1)).existsById(dto.getId());
        verify(cursoHelper, times(1)).dtoToEntity(dto, tematica);
        verify(cursoRepository, times(1)).save(curso);
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

    private CursoDto generarCursoDto(Tematica tematica) {
		CursoDto dto = new CursoDto();
		
        dto.setId(1L);
		dto.setNombre("Big data en analítica");
		dto.setDescripcion("Un curso de big data");
		dto.setCantidadAlumnos(30);
		dto.setNumeroTemas(12);
		dto.setDuracion(600);
		dto.setCertificacion(true);
		dto.setPrecio(new BigDecimal("35.60"));
		dto.setIdTematica(tematica.getId());
		
		return dto;
	}
	
	private Curso generarCurso(Tematica tematica) {
		Curso curso = new Curso();
		
        curso.setId(1L);
		curso.setNombre("Big data en analítica");
		curso.setDescripcion("Un curso de big data");
		curso.setCantidadAlumnos(30);
		curso.setNumeroTemas(12);
		curso.setDuracion(600);
		curso.setCertificacion(true);
		curso.setPrecio(new BigDecimal("35.60"));
		curso.setTematica(tematica);
		
		return curso;
	}
}
