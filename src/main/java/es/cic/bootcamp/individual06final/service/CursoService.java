package es.cic.bootcamp.individual06final.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cic.bootcamp.individual06final.dto.CursoDto;
import es.cic.bootcamp.individual06final.exception.CursoException;
import es.cic.bootcamp.individual06final.helper.CursoHelper;
import es.cic.bootcamp.individual06final.model.Curso;
import es.cic.bootcamp.individual06final.model.Tematica;
import es.cic.bootcamp.individual06final.repository.CursoRepository;
import es.cic.bootcamp.individual06final.repository.TematicaRepository;

@Service
@Transactional
public class CursoService {

	private static final String ALTA_ENTRADA = "Aun no se ha dado de alta esta entrada";

	@Autowired
	private CursoRepository cursoRepository;
	
	@Autowired
	private TematicaRepository tematicaRepository;
	
	@Autowired
	private CursoHelper cursoHelper;
	
	public Long create(CursoDto dto) {
		if(dto.getId() != null && cursoRepository.existsById(dto.getId())) {
			throw new CursoException("Ya se ha dado de alta esta entrada");
		}
		
		Tematica tematica = findTematica(dto);
		
		Curso curso = cursoHelper.dtoToEntity(dto, tematica);
		
		return cursoRepository.save(curso).getId();
	}

	public CursoDto read(Long id) {
		Optional<Curso> optional = cursoRepository.findById(id);
		
		if(optional.isPresent()) {
			return cursoHelper.entityToDto(optional.get());
		} else {
			return null;
		}
	}
	
	public List<CursoDto> list() {
		List<Curso> lista = new ArrayList<>();
		
		cursoRepository
		.findAll()
		.forEach(lista::add);
		
		return cursoHelper.listEntityToListDto(lista);
	}
	
	public void update(CursoDto dto) {
		if(dto.getId() == null) {
			throw new CursoException(ALTA_ENTRADA);			
		}
		Optional<Curso> optional = cursoRepository.findById(dto.getId());
		
		if(optional.isPresent()) {
			Curso curso = optional.get();
			
			Tematica tematica = findTematica(dto);
			
			cursoHelper.dtoToEntity(dto, curso, tematica);
			
			cursoRepository.save(curso);
		} else {
			throw new CursoException(ALTA_ENTRADA);	
		}
		
	}
	
	private Tematica findTematica(CursoDto dto) {
		if(dto.getIdTematica() !=null) {
			Optional<Tematica> optional = tematicaRepository.findById(dto.getIdTematica());
			
			if(optional.isPresent()) {
				return optional.get();
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
}
