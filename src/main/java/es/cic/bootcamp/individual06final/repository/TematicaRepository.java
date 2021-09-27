package es.cic.bootcamp.individual06final.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import es.cic.bootcamp.individual06final.model.Tematica;

public interface TematicaRepository extends CrudRepository<Tematica, Long> {

	public List<Tematica> findAllByActivoTrue();
}
