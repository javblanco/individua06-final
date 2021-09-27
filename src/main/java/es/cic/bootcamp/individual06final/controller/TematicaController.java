package es.cic.bootcamp.individual06final.controller;

import java.util.List;

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

import es.cic.bootcamp.individual06final.dto.TematicaDto;
import es.cic.bootcamp.individual06final.service.TematicaService;

@RestController
@RequestMapping("/api/tematica")
public class TematicaController {

	@Autowired
	private TematicaService tematicaService;
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public Long create(@RequestBody TematicaDto dto) {
		return tematicaService.create(dto);
	}
	
	@GetMapping("/{id}")
	public TematicaDto read(@PathVariable(required = true, name = "id") Long id) {
		return tematicaService.read(id);
	}
	
	@GetMapping
	public List<TematicaDto> list() {
		return tematicaService.list();
	}
	
	@PutMapping
	public void update(@RequestBody TematicaDto dto) {
		tematicaService.update(dto);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable(required = true, name = "id") Long id) {
		tematicaService.delete(id);
	}
	
}
