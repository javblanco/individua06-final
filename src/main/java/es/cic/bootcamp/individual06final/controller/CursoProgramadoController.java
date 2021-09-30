package es.cic.bootcamp.individual06final.controller;

import java.util.List;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import es.cic.bootcamp.individual06final.dto.CursoProgramadoDto;
import es.cic.bootcamp.individual06final.service.CursoProgramadoService;

@RestController
@RequestMapping("/api/programacion")
public class CursoProgramadoController {

	private static final Logger LOGGER = Logger.getLogger(CursoProgramadoController.class.getName());
	
	@Autowired
	private CursoProgramadoService cursoProgramadoService;
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public Long create(@Valid @RequestBody CursoProgramadoDto dto) {
		LOGGER.info("Método post de /api/programado. Nueva programación de un curso");
		return cursoProgramadoService.create(dto);
	}
	
	@GetMapping
	public List<CursoProgramadoDto> list() {
		LOGGER.info("Método get de /api/programado. Listado de programaciones actuales de los cursos");
		return cursoProgramadoService.list();
	}
	
	@PutMapping
	public void update(@Valid @RequestBody CursoProgramadoDto dto) {
		LOGGER.info("Método put de /api/programado. Modificación de la programación de un curso");
		cursoProgramadoService.update(dto);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable(required = true, name = "id") Long id) {
		LOGGER.info("Método delete de /api/programado. Borrado programación de un curso");
		cursoProgramadoService.delete(id);
	}
	
	@PostMapping("/abrir/{id}")
	public void abrirInscripciones(@PathVariable(required = true, name = "id") Long id) {
		LOGGER.info("Método post de /api/programado/abrir. Se abren las inscripciones para un curso");
		cursoProgramadoService.abrirInscripciones(id);
	}

	@PostMapping("/cerrar/{id}")
	public void cerrarInscripciones(@PathVariable(required = true, name = "id") Long id) {
		LOGGER.info("Método post de /api/programado/cerrar. Se cierran las inscripciones para un curso");
		cursoProgramadoService.cerrarInscripciones(id);
	}
}
