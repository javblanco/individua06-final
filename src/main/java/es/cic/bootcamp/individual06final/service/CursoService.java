package es.cic.bootcamp.individual06final.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cic.bootcamp.individual06final.dto.CursoDto;
import es.cic.bootcamp.individual06final.exception.CursoException;
import es.cic.bootcamp.individual06final.exception.TematicaException;
import es.cic.bootcamp.individual06final.helper.CursoHelper;
import es.cic.bootcamp.individual06final.model.Curso;
import es.cic.bootcamp.individual06final.model.Tematica;
import es.cic.bootcamp.individual06final.repository.CursoRepository;
import es.cic.bootcamp.individual06final.repository.TematicaRepository;

@Service
@Transactional
public class CursoService {

	private static final String NO_EXISTE_EL_CURSO = "No existe el curso";

	private static final Logger LOGGER = Logger.getLogger(CursoService.class.getName());
	
	private static final String ALTA_ENTRADA = "Aun no se ha dado de alta esta entrada";

	@Autowired
	private CursoRepository cursoRepository;
	
	@Autowired
	private TematicaRepository tematicaRepository;
	
	@Autowired
	private CursoHelper cursoHelper;
	
	public Long create(CursoDto dto) {
		if(dto.getId() != null && cursoRepository.existsById(dto.getId())) {
			LOGGER.info("No se ha podido dar de alta la entrada porque ya existe el curso");
			throw new CursoException("Ya se ha dado de alta esta entrada");
		}
		
		Tematica tematica = findTematica(dto);
		
		Curso curso = cursoHelper.dtoToEntity(dto, tematica);

		LOGGER.info("Se ha dado de alta un nuevo curso");
		return cursoRepository.save(curso).getId();
	}

	@Transactional(readOnly = true)
	public CursoDto read(Long id) {
		Optional<Curso> optional = cursoRepository.findById(id);
		
		if(optional.isPresent()) {

			LOGGER.info("Curso encontrado");
			return cursoHelper.entityToDto(optional.get());
		} else {

			LOGGER.info("No existe ningún registro con esa id");
			throw new CursoException("No existe el curso que se está buscando.");
		}
	}
	
	@Transactional(readOnly = true)
	public List<CursoDto> list() {
		List<Curso> lista = new ArrayList<>();
		
		cursoRepository
		.findAll()
		.forEach(lista::add);
		

		LOGGER.info("Listado de cursos");
		
		return cursoHelper.listEntityToListDto(lista);
	}
	
	public void update(CursoDto dto) {
		if(dto.getId() == null) {

			LOGGER.info("No se ha proporcionado el id del curso");
			throw new CursoException(ALTA_ENTRADA);			
		}
		Optional<Curso> optional = cursoRepository.findById(dto.getId());
		
		if(optional.isPresent()) {
			Curso curso = optional.get();

			Tematica tematica = null;

			if(dto.getIdTematica() != curso.getTematica().getId()) {
				tematica = findTematica(dto);
			}
			
			
			cursoHelper.dtoToEntity(dto, curso, tematica);
			

			LOGGER.info("Se ha actualizado el curso");
			cursoRepository.save(curso);
		} else {

			LOGGER.info(NO_EXISTE_EL_CURSO);
			throw new CursoException(ALTA_ENTRADA);	
		}
		
	}
	
	public void baja(Long id) {
		Optional<Curso> optional = cursoRepository.findById(id);
		
		if(optional.isPresent()) {
			Curso curso = optional.get();
			
			if(curso.isActivo()) {
				LOGGER.info("Se ha dado de baja el curso");
				curso.setActivo(false);
				
				cursoRepository.save(curso);
			} else {
				LOGGER.info("El curso ya se encontraba en estado de baja.");
				throw new CursoException("El curso ya estaba dado de baja");
			}
		} else {

			LOGGER.info(NO_EXISTE_EL_CURSO);
			throw new CursoException(ALTA_ENTRADA);	
		}
	}
	
	public void alta(Long id) {
		Optional<Curso> optional = cursoRepository.findById(id);
		
		if(optional.isPresent()) {
			Curso curso = optional.get();
			
			if(curso.isActivo()) {
				LOGGER.info("El curso ya se encontraba en estado de alta.");
				throw new CursoException("El curso ya está dado de alta");				
			} else {
				LOGGER.info("Se ha dado de alta el curso");
				curso.setActivo(true);
				
				cursoRepository.save(curso);
			}
		} else {
			LOGGER.info(NO_EXISTE_EL_CURSO);
			throw new CursoException(ALTA_ENTRADA);	
		}
	}
	
	public List<CursoDto> listActivos() {

		LOGGER.info("Se listan todos los cursos actualmente activos.");
		return cursoHelper.listEntityToListDto(cursoRepository.findAllByActivoTrue());
	}
	
	private Tematica findTematica(CursoDto dto) {
		if(dto.getIdTematica() !=null) {
			Optional<Tematica> optional = tematicaRepository.findById(dto.getIdTematica());
			
			if(optional.isPresent()) {

				LOGGER.info("Se ha leído la temática asociada a este curso.");
				Tematica tematica = optional.get();

				if(tematica.isActivo()) {
					return optional.get();
				} else {
					throw new TematicaException("No se puede seleccionar una temática inhabilitada");
				}
			} else {
				LOGGER.info("No existe la temática asociada a este curso");
				throw new TematicaException("No existe la temática seleccionada");
			}
		} else {

			LOGGER.info("No se ha seleccionado una temática");
			throw new TematicaException("No existe la temática seleccionada");
		}
	}
}
