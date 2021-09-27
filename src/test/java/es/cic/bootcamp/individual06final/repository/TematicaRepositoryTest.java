package es.cic.bootcamp.individual06final.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import es.cic.bootcamp.individual06final.model.Tematica;

@DataJpaTest
class TematicaRepositoryTest {

	@Autowired
	private TematicaRepository tematicaRepository;
	
	@Autowired
	private TestEntityManager entityManager;

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testSave() {
		Tematica tematica = tematicaRepository.save(generarTematica());
		
		entityManager.flush();
		entityManager.detach(tematica);
		
		Tematica tematicaEnBBDD = entityManager.find(Tematica.class, tematica.getId());
		
		assertNotNull(tematica.getId(), "La clave primaria no debería ser nula");
		assertThat(tematica)
		.isEqualTo(tematicaEnBBDD);
	}

	@Test
	void testFindById() {
		Tematica tematica = generarTematica();
		
		entityManager.persistAndFlush(tematica);
		entityManager.detach(tematica);
		
		Optional<Tematica> resultado = tematicaRepository.findById(tematica.getId());
		
		assertTrue(resultado.isPresent(), "El registro no se ha leído correctamente");
		
		assertThat(tematica)
		.usingRecursiveComparison()
		.ignoringFields("id")
		.isEqualTo(resultado.get());
	}

	@Test
	void testFindAll() {
		Tematica tematica1 = generarTematica();
		Tematica tematica2 = generarTematica();
		
		entityManager.persist(tematica1);
		entityManager.persistAndFlush(tematica2);

		entityManager.detach(tematica1);
		entityManager.detach(tematica2);
		
		List<Tematica> lista = new ArrayList<>();
		
		tematicaRepository
		.findAll()
		.forEach(lista::add);
		
		assertThat(lista.size())
		.isGreaterThanOrEqualTo(2);
		
		
		assertThat(lista)
		.containsAll(List.of(tematica1, tematica2));
	}
	
	@Test
	void testUpdate() {
		Tematica tematica = generarTematica();
		
		entityManager.persistAndFlush(tematica);
		entityManager.detach(tematica);
		
		Tematica tematicaAModificar = tematicaRepository.findById(tematica.getId()).get();
		
		tematicaAModificar.setNombre("TIC");
		entityManager.flush();
		entityManager.detach(tematicaAModificar);
		
		Tematica tematicaEnBBDD = entityManager.find(Tematica.class, tematica.getId());
		
		assertThat(tematicaAModificar)
		.usingDefaultComparator()
		.isEqualTo(tematicaEnBBDD);
	}

	@Test
	void testDeleteById() {
		Tematica tematica = generarTematica();
		
		entityManager.persistAndFlush(tematica);
		
		tematicaRepository.deleteById(tematica.getId());
		entityManager.flush();
		
		Tematica tematicaEnBBDD = entityManager.find(Tematica.class, tematica.getId());
		
		assertNull(tematicaEnBBDD, "No se ha borrado el registro correctamente de la base de datos.");
	}

	
	private Tematica generarTematica() {
		Tematica tematica = new Tematica();

		tematica.setNombre("Informática");
		tematica.setDescripcion("Esta temática es de informática");
		tematica.setSubtematicas("Big Data, Machine Learning");
		
		return tematica;
	}

}
