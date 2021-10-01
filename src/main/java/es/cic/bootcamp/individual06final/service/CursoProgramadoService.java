package es.cic.bootcamp.individual06final.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cic.bootcamp.individual06final.dto.CursoProgramadoDto;
import es.cic.bootcamp.individual06final.exception.CursoException;
import es.cic.bootcamp.individual06final.exception.CursoProgramadoException;
import es.cic.bootcamp.individual06final.helper.CursoProgramadoHelper;
import es.cic.bootcamp.individual06final.model.Curso;
import es.cic.bootcamp.individual06final.model.CursoProgramado;
import es.cic.bootcamp.individual06final.repository.CursoProgramadoRepository;
import es.cic.bootcamp.individual06final.repository.CursoRepository;

@Service
@Transactional
public class CursoProgramadoService {

	private static final String NO_EXISTE_LA_PROGRAMACION = "No existe esta programación";

	private static final Logger LOGGER = Logger.getLogger(CursoProgramadoService.class.getName());
	
	@Autowired
	private CursoProgramadoRepository cursoProgramadoRepository;
	
	@Autowired
	private CursoProgramadoHelper cursoProgramadoHelper;
	
	@Autowired
	private CursoRepository cursoRepository;
	
	public Long create(CursoProgramadoDto dto) {
		
		if(dto.getId() != null && cursoProgramadoRepository.existsById(dto.getId())) {
			LOGGER.info("No se ha podido dar de alta la entrada porque ya existe la programación para este curso.");
			throw new CursoProgramadoException("Ya se ha dado de alta esta entrada");
		}
		
		comprobarFecha(dto);
		Curso curso = findCurso(dto);
		
		CursoProgramado cursoProgramado = cursoProgramadoHelper.dtoToEntity(dto, curso);
		
		LOGGER.info("Se ha programado el curso");
		return cursoProgramadoRepository.save(cursoProgramado).getId();
	}
	

	public List<CursoProgramadoDto> list() {
		List<CursoProgramado> lista = new ArrayList<>(); 
		
		cursoProgramadoRepository.findAll()
		.forEach(lista::add);
		
		LOGGER.info("Se han listado las diferentes programaciones de los cursos.");
		return cursoProgramadoHelper.listEntityToListDto(lista);
	}
	
	public void update(CursoProgramadoDto dto) {
		if(dto.getId() == null) {
			LOGGER.info("No se ha proporcionado el id para la entrada de la programación de este curso");
			
			throw new CursoProgramadoException("No se ha encontrado la programación del curso.");
		}
		
		Optional<CursoProgramado> optional = cursoProgramadoRepository.findById(dto.getId());
		
		if(optional.isPresent()) {
			CursoProgramado cursoProgramado = optional.get();
			
			cursoProgramadoHelper.dtoToEntity(cursoProgramado, dto);			

			this.comprobarFecha(dto);
			LOGGER.info("Se ha modificado la programación seleccionada");
			cursoProgramadoRepository.save(cursoProgramado);
		} else {
			throw new CursoProgramadoException("No se ha encontrado la programación del curso.");
		}
	}
	
	public void delete(Long id) {
		if(cursoProgramadoRepository.existsById(id)) {
			LOGGER.info("Se ha eliminado la programación para el curso seleccionado");
			cursoProgramadoRepository.deleteById(id);
			
		} else {
			LOGGER.info(NO_EXISTE_LA_PROGRAMACION);
			throw new CursoProgramadoException(NO_EXISTE_LA_PROGRAMACION);	
		}
	}
	
	public void abrirInscripciones(Long id) {
		Optional<CursoProgramado> optional = cursoProgramadoRepository.findById(id);
		
		if(optional.isPresent()) {
			CursoProgramado cursoProgramado = optional.get();
			
			if(!cursoProgramado.isInscripcion()) {
				LOGGER.info("Se han abierto las inscripciones");
				cursoProgramado.setInscripcion(true);
				
				cursoProgramadoRepository.save(cursoProgramado);
			} else {
				LOGGER.info("Las inscripciones están abiertas");
				throw new CursoProgramadoException("Las inscripciones ya están abiertas");
			}
		} else {

			LOGGER.info(NO_EXISTE_LA_PROGRAMACION);
			throw new CursoProgramadoException(NO_EXISTE_LA_PROGRAMACION);	
		}
		
	}
	
	public void cerrarInscripciones(Long id) {
		Optional<CursoProgramado> optional = cursoProgramadoRepository.findById(id);
		
		if(optional.isPresent()) {
			CursoProgramado cursoProgramado = optional.get();
			
			if(cursoProgramado.isInscripcion()) {
				LOGGER.info("Se han cerrado las inscripciones");
				cursoProgramado.setInscripcion(false);
				
				cursoProgramadoRepository.save(cursoProgramado);
			} else {
				LOGGER.info("Las inscripciones están cerradas");
				throw new CursoProgramadoException("Las inscripciones ya están cerradas");
			}
		} else {

			LOGGER.info(NO_EXISTE_LA_PROGRAMACION);
			throw new CursoProgramadoException(NO_EXISTE_LA_PROGRAMACION);	
		}
		
	}
	
	private Curso findCurso(CursoProgramadoDto dto) {
		if(dto.getIdCurso() != null) {
			
			Optional<Curso> optional = cursoRepository.findById(dto.getIdCurso());
			
			if(optional.isPresent()) {
				Curso curso = optional.get();
				
				if(curso.isActivo()) {
					LOGGER.info("Se ha encontrado el curso");
					return curso;
				} else {
					LOGGER.info("El curso no está activo");
					throw new CursoException("El curso seleccionado no está activo.");
				}
			} else {

				LOGGER.info("El curso no existe");
				throw new CursoException("El curso seleccionado no existe.");
			}
		} else {
			LOGGER.info("No se ha seleccionado ningún curso");
			throw new CursoException("No se ha seleccionado el curso.");
		}
		
	}


	private void comprobarFecha(CursoProgramadoDto dto) {

		if(dto.getFechaInicio() == null) {
			throw new CursoProgramadoException("Debe seleccionar al menos la fecha de inicio.");
		}  

		boolean existeFechaFin = dto.getFechaFin() != null && !dto.getFechaFin().toString().equals("");

		if(existeFechaFin && (dto.getFechaInicio().isAfter(dto.getFechaFin()) || dto.getFechaInicio().isEqual(dto.getFechaFin()))) {
			throw new CursoProgramadoException("La fecha de inicio debe ser anterior a la fecha de finalización.");
		}
	}
}
