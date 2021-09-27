package es.cic.bootcamp.individual06final.controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import es.cic.bootcamp.individual06final.dto.CursoDto;
import es.cic.bootcamp.individual06final.service.CursoService;

@RestController
@RequestMapping("/api/curso")
public class CursoController {

	private static final Logger LOGGER = Logger.getLogger(CursoController.class.getName());
	
	
	@Autowired
	private CursoService cursoService;
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public Long create(@RequestBody CursoDto dto) {
		LOGGER.info("Método post de /api/curso. Creación de un nuevo curso");
		return cursoService.create(dto);
	}
	
	@GetMapping("/{id}")
	public CursoDto read(@PathVariable(required = true, name = "id") Long id) {
		LOGGER.log(Level.INFO, "Método get de /api/curso. Leyendo el registro del curso con {0}", id);  
		return cursoService.read(id);
	}
	
	@GetMapping
	public List<CursoDto> list() {
		LOGGER.info("Método get de /api/curso. Leyendo la lista de cursos.");
		return cursoService.list();
	}
	
	@PutMapping
	public void update(@RequestBody CursoDto dto) {
		LOGGER.info("Método put de /api/curso. Modificación de un curso");
		cursoService.update(dto);
	}
	
	@PostMapping("/baja/{id}")
	public void baja(@PathVariable(required = true, name = "id") Long id) {
		LOGGER.log(Level.INFO, "Método post de /api/curso/baja. Procesando la baja del curso con {0}", id);  
		cursoService.baja(id);
	}
	

	@PostMapping("/alta/{id}")
	public void alta(@PathVariable(required = true, name = "id") Long id) {
		LOGGER.log(Level.INFO, "Método post de /api/curso/baja. Procesando el alta del curso con {0}", id);  
		cursoService.alta(id);
	}
}
