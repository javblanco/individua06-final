package es.cic.bootcamp.individual06final.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import es.cic.bootcamp.individual06final.model.Curso;
import es.cic.bootcamp.individual06final.model.Tematica;

@DataJpaTest
class CursoRepositoryTest {

	@Autowired
	private CursoRepository cursoRepository;
	
	@Autowired
	private TestEntityManager entityManager;

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testSave() {
		Tematica tematica = generarTematica();
		
		entityManager.persistAndFlush(tematica);
		
		Curso curso = generarCurso(tematica);
		
		cursoRepository.save(curso);
		entityManager.flush();
		entityManager.detach(curso);
		
		
		Curso cursoEnBBDD = entityManager.find(Curso.class, curso.getId());
		
		assertNotNull(curso.getId(), "La clave primaria no debería ser nula");
		assertThat(curso)
		.isEqualTo(cursoEnBBDD);
	}

	@Test
	void testFindById() {
		Tematica tematica = generarTematica();
		
		entityManager.persistAndFlush(tematica);
		
		Curso curso = generarCurso(tematica);
		entityManager.persistAndFlush(curso);
		entityManager.detach(curso);
		
		Optional<Curso> resultado = cursoRepository.findById(curso.getId());
		
		assertTrue(resultado.isPresent(), "El registro no se ha leído correctamente");
		
		assertThat(curso)
		.usingRecursiveComparison()
		.ignoringFields("id")
		.isEqualTo(resultado.get());
	}

	@Test
	void testFindAll() {
		Tematica tematica = generarTematica();

		entityManager.persistAndFlush(tematica);

		Curso curso1 = generarCurso(tematica);
		Curso curso2 = generarCurso(tematica);
		entityManager.persist(curso1);
		entityManager.persistAndFlush(curso2);
		
		entityManager.detach(curso1);
		entityManager.detach(curso2);
		
		List<Curso> lista = new ArrayList<>();
		
		cursoRepository
		.findAll()
		.forEach(lista::add);
		
		assertThat(lista.size())
		.isGreaterThanOrEqualTo(2);
		
		
		assertThat(lista)
		.containsAll(List.of(curso1, curso2));
	}
	
	@Test
	void testUpdate() {
		Tematica tematica = generarTematica();
		
		entityManager.persistAndFlush(tematica);
		
		Curso curso = generarCurso(tematica);
		entityManager.persistAndFlush(curso);
		entityManager.detach(curso);
		
		Curso cursoAModificar = cursoRepository.findById(curso.getId()).get();
		cursoAModificar.setNombre("BBDD 101");
		
		Curso cursoEnBBDD = entityManager.find(Curso.class, curso.getId());
		
		assertThat(cursoAModificar)
		.usingDefaultComparator()
		.isEqualTo(cursoEnBBDD);
	}

	@Test
	void testDeleteById() {
		Tematica tematica = generarTematica();
		
		entityManager.persistAndFlush(tematica);
		
		Curso curso = generarCurso(tematica);

		entityManager.persistAndFlush(curso);
		
		cursoRepository.deleteById(curso.getId());
		entityManager.flush();
		
		Curso cursoEnBBDD = entityManager.find(Curso.class, curso.getId());
		
		assertNull(cursoEnBBDD, "No se ha borrado el registro correctamente de la base de datos.");
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
		
		return tematica;
	}

}
