package es.cic.bootcamp.individual06final.controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import es.cic.bootcamp.individual06final.dto.TematicaDto;
import es.cic.bootcamp.individual06final.service.TematicaService;

@RestController
@RequestMapping("/api/tematica")
@CrossOrigin(origins = "http://localhost:4200")
public class TematicaController {
	
	private static final Logger LOGGER = Logger.getLogger(TematicaController.class.getName());
	
	@Autowired
	private TematicaService tematicaService;
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public Long create(@Valid @RequestBody TematicaDto dto) {		
		LOGGER.info("Método post de /api/tematica. Creación de una nueva temática");

		return tematicaService.create(dto);
	}
	
	@GetMapping("/{id}")
	public TematicaDto read(@PathVariable(required = true, name = "id") Long id) {
		LOGGER.log(Level.INFO, "Método get de /api/tematica. Leyendo el registro de la temática con {0}", id);
		return tematicaService.read(id);
	}
	
	@GetMapping
	public List<TematicaDto> list() {
		LOGGER.log(Level.INFO, "Método get de /api/tematica. Leyendo lista de tematicas");
		return tematicaService.list();
	}
	
	@PutMapping
	public void update(@Valid @RequestBody TematicaDto dto) {
		LOGGER.log(Level.INFO, "Método put de /api/tematica. Modificación de una temática");
		tematicaService.update(dto);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable(required = true, name = "id") Long id) {
		LOGGER.log(Level.INFO, "Método delete de /api/tematica. Borrando el registro de la temática con id {0} si no tiene cursos asociados", id);
		tematicaService.delete(id);
	}
	
	@PostMapping("/baja/{id}")
	public void baja(@PathVariable(required = true, name = "id") Long id) {
		LOGGER.log(Level.INFO, "Método post de /api/tematica/baja. Dando de baja el registro de la temática con id {0}", id);
		tematicaService.baja(id);
	}
	
	@PostMapping("/alta/{id}")
	public void alta(@PathVariable(required = true, name = "id") Long id) {
		LOGGER.log(Level.INFO, "Método post de /api/tematica/alta. Dando de alta el registro de la temática con id {0}", id);
		tematicaService.alta(id);
	}
	
	@GetMapping("/activos")
	public List<TematicaDto> listActivos() {
		LOGGER.log(Level.INFO, "Método get de /api/tematica/activos. Leyendo registros activos actualmente");

		return tematicaService.listActivos();
	}


	@GetMapping("/curso/{id}")
	public List<TematicaDto> listActivosWithCursoActual(@PathVariable(required = true, name = "id") Long id) {
		LOGGER.log(Level.INFO, "Método get de /api/tematica/curso/{0}. Leyendo registros activos actualmente y la temática asociada al curso que se está visualizando.");

		return tematicaService.listActivosConCurso(id);
	}
}
