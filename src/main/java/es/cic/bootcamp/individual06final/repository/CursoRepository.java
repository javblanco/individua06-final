package es.cic.bootcamp.individual06final.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.cic.bootcamp.individual06final.model.Curso;

public interface CursoRepository extends CrudRepository<Curso, Long> {

	public boolean existsByTematicaId(Long id);
	
	public List<Curso> findAllByActivoTrue();
}
