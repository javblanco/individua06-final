package es.cic.bootcamp.individual06final.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import es.cic.bootcamp.individual06final.enumeration.Categoria;
import es.cic.bootcamp.individual06final.model.Curso;
import es.cic.bootcamp.individual06final.model.CursoProgramado;
import es.cic.bootcamp.individual06final.model.Tematica;

@DataJpaTest
class CursoProgramadoRepositoryTest {
	
	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private CursoProgramadoRepository cursoProgramadoRepository;

	@BeforeEach
	void setUp() throws Exception {
	}
	@Test
	void testSave() {
		Tematica tematica = generarTematica();		
		entityManager.persistAndFlush(tematica);
		
		Curso curso = generarCurso(tematica);
		entityManager.persistAndFlush(curso);
		
		CursoProgramado programado = generarProgramado(curso);
		
		
		cursoProgramadoRepository.save(programado);
		entityManager.flush();
		entityManager.detach(programado);
		
		
		CursoProgramado programadoEnBBDD = entityManager.find(CursoProgramado.class, programado.getId());
		
		assertNotNull(programado.getId(), "La clave primaria no debería ser nula");
		assertThat(programado)
		.isEqualTo(programadoEnBBDD);
	}

	@Test
	void testFindById() {
		Tematica tematica = generarTematica();
		
		entityManager.persistAndFlush(tematica);
		
		Curso curso = generarCurso(tematica);
		entityManager.persistAndFlush(curso);
		
		CursoProgramado programado = generarProgramado(curso);	
		entityManager.persistAndFlush(programado);
		entityManager.detach(curso);
		
		Optional<CursoProgramado> resultado = cursoProgramadoRepository.findById(programado.getId());
		
		assertTrue(resultado.isPresent(), "El registro no se ha leído correctamente");
		
		assertThat(programado)
		.usingRecursiveComparison()
		.ignoringFields("id")
		.isEqualTo(resultado.get());
	}

	@Test
	void testFindAll() {
		Tematica tematica = generarTematica();

		entityManager.persistAndFlush(tematica);

		Curso curso = generarCurso(tematica);
		entityManager.persistAndFlush(curso);
		

		CursoProgramado programado1 = generarProgramado(curso);
		CursoProgramado programado2 = generarProgramado(curso);
		entityManager.persist(programado1);
		entityManager.persistAndFlush(programado2);
		
		entityManager.detach(programado1);
		entityManager.detach(programado2);
		
		List<CursoProgramado> lista = new ArrayList<>();
		
		cursoProgramadoRepository
		.findAll()
		.forEach(lista::add);
		
		assertThat(lista.size())
		.isGreaterThanOrEqualTo(2);
		
		
		assertThat(lista)
		.containsAll(List.of(programado1, programado2));
	}
	
	@Test
	void testUpdate() {
		Tematica tematica = generarTematica();
		
		entityManager.persistAndFlush(tematica);
		
		Curso curso = generarCurso(tematica);
		entityManager.persist(curso);
		
		CursoProgramado programado = generarProgramado(curso);
		entityManager.persistAndFlush(programado);
		entityManager.detach(programado);
		
		
		CursoProgramado programadoAModificar = cursoProgramadoRepository.findById(programado.getId()).get();
		programadoAModificar.setFechaFin(null);
		
		CursoProgramado programadoEnBBDD = entityManager.find(CursoProgramado.class, programado.getId());
		
		assertThat(programadoAModificar)
		.usingDefaultComparator()
		.isEqualTo(programadoEnBBDD);
	}

	@Test
	void testDeleteById() {
		Tematica tematica = generarTematica();
		
		entityManager.persistAndFlush(tematica);
		
		Curso curso = generarCurso(tematica);

		entityManager.persistAndFlush(curso);
		
		CursoProgramado programado = generarProgramado(curso);

		entityManager.persistAndFlush(programado);
		
		cursoProgramadoRepository.deleteById(programado.getId());
		entityManager.flush();
		
		CursoProgramado programadoEnBBDD = entityManager.find(CursoProgramado.class, programado.getId());
		
		assertNull(programadoEnBBDD, "No se ha borrado el registro correctamente de la base de datos.");
	}
	
	private CursoProgramado generarProgramado(Curso curso) {
		CursoProgramado cursoProgramado = new CursoProgramado();
		
		cursoProgramado.setCurso(curso);
		cursoProgramado.setInscripcion(false);
		cursoProgramado.setFechaInicio(LocalDate.of(2021, Month.APRIL, 14));
		cursoProgramado.setFechaFin(LocalDate.of(2021, Month.SEPTEMBER, 30));
		
		return cursoProgramado;
	}

	
	private Curso generarCurso(Tematica tematica) {
		Curso curso = new Curso();
		
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
	
	private Tematica generarTematica() {
		Tematica tematica = new Tematica();

		tematica.setNombre("Informática");
		tematica.setDescripcion("Esta temática es de informática");
		tematica.setSubtematicas("Big Data, Machine Learning");
		tematica.setCategoria(Categoria.TECNOLOGIA);
		tematica.setReferencia("REF-INF-TEC");
		
		return tematica;
	}
}
