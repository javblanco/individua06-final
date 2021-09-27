package es.cic.bootcamp.individual06final.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cic.bootcamp.individual06final.dto.TematicaDto;
import es.cic.bootcamp.individual06final.exception.CursoException;
import es.cic.bootcamp.individual06final.exception.TematicaException;
import es.cic.bootcamp.individual06final.helper.TematicaHelper;
import es.cic.bootcamp.individual06final.model.Tematica;
import es.cic.bootcamp.individual06final.repository.CursoRepository;
import es.cic.bootcamp.individual06final.repository.TematicaRepository;

@Service
@Transactional
public class TematicaService {
	private static final String NO_EXISTE_LA_TEMATICA = "No existe la temática";

	private static final String ALTA_ENTRADA = "Aun no se ha dado de alta esta entrada";
	private static final Logger LOGGER = Logger.getLogger(TematicaService.class.getName());

	@Autowired
	private TematicaRepository tematicaRepository;
	
	@Autowired
	private CursoRepository cursoRepository;
	
	@Autowired
	private TematicaHelper tematicaHelper;
	
	public Long create(TematicaDto dto) {
		if(dto.getId() != null && tematicaRepository.existsById(dto.getId())) {			
			LOGGER.info("No se ha podido dar de alta la entrada porque ya existe la temática");
			throw new TematicaException("Ya se ha dado de alta esta entrada");
		}
		Tematica tematica = tematicaHelper.dtoToEntity(dto);
		
		LOGGER.info("Se ha dado de alta una nueva temática");
		return tematicaRepository.save(tematica).getId();
	}
	
	public TematicaDto read(Long id) {
		Optional<Tematica> optional = tematicaRepository.findById(id);
		
		if(optional.isPresent()) {
			TematicaDto dto = tematicaHelper.entityToDto(optional.get());
			
			LOGGER.info("Temática encontrada");			
			return dto;
		} else {
			LOGGER.info("No existe ningún registro con esa id");
			return null;
		}
	}


	public List<TematicaDto> list() {
		List<Tematica> lista = new ArrayList<>();
		
		tematicaRepository
		.findAll()
		.forEach(lista::add);
		LOGGER.info("Listado de temáticas");
		
		return  lista.stream()
				.map(t -> tematicaHelper.entityToDto(t, this.isEliminable(t.getId())))
				.collect(Collectors.toList());
	}
	
	public void update(TematicaDto dto) {
		if(dto.getId() == null) {
			LOGGER.info("No se ha proporcionado el id de la temática");
			throw new TematicaException(ALTA_ENTRADA);			
		}
		Optional<Tematica> optional = tematicaRepository.findById(dto.getId());
		
		if(optional.isPresent()) {
			Tematica tematica = optional.get();
			tematicaHelper.dtoToEntity(tematica, dto);
			
			LOGGER.info("Se ha actualizado el curso");
			tematicaRepository.save(tematica);
		} else {
			LOGGER.info(NO_EXISTE_LA_TEMATICA);
			throw new TematicaException(ALTA_ENTRADA);	
		}
		
	}
	
	public void delete(Long id) {
		if(tematicaRepository.existsById(id)) {
			if(this.isEliminable(id)) {
				LOGGER.info("Se ha eliminado la temática");
				tematicaRepository.deleteById(id);
			} else {
				LOGGER.info("No se puede eliminar la temática, tiene cursos asociados a la misma");
				throw new TematicaException("Existen cursos asociados a esta temática");
			}
			
		} else {
			LOGGER.info(NO_EXISTE_LA_TEMATICA);
			throw new TematicaException(ALTA_ENTRADA);	
		}
	}
	
	public void baja(Long id) {
		Optional<Tematica> optional = tematicaRepository.findById(id);
		
		if(optional.isPresent()) {
			Tematica tematica = optional.get();
			
			if(tematica.isActivo()) {
				LOGGER.info("Se ha dado de baja la temática");
				tematica.setActivo(false);
				
				tematicaRepository.save(tematica);
			} else {
				LOGGER.info("La temática ya se encontraba en estado de baja.");
				throw new CursoException("La temática ya estaba dada de baja");
			}
		} else {

			LOGGER.info(NO_EXISTE_LA_TEMATICA);
			throw new CursoException(ALTA_ENTRADA);	
		}
	}
	
	public void alta(Long id) {
		Optional<Tematica> optional = tematicaRepository.findById(id);
		
		if(optional.isPresent()) {
			Tematica tematica = optional.get();
			
			if(tematica.isActivo()) {
				LOGGER.info("La temática ya se encontraba en estado de alta.");
				throw new TematicaException("La temática ya está dada de alta");				
			} else {
				LOGGER.info("Se ha dado de alta la temática");
				tematica.setActivo(true);
				
				tematicaRepository.save(tematica);
			}
		} else {
			LOGGER.info(NO_EXISTE_LA_TEMATICA);
			throw new CursoException(ALTA_ENTRADA);	
		}
	}
	
	private boolean isEliminable(Long id) {
		
		return !cursoRepository.existsByTematicaId(id);
	}
}
