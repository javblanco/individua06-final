package es.cic.bootcamp.individual06final.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import es.cic.bootcamp.individual06final.model.Tematica;

public interface TematicaRepository extends CrudRepository<Tematica, Long> {

	public List<Tematica> findAllByActivoTrue();

	@Query("select DISTINCT t, CASE WHEN (c is null) THEN true ELSE false END from Tematica t left join Curso c on c.tematica = t")
	public List<Object[]> findAllWithEliminable();
}
