package es.cic.bootcamp.individual06final.controller;

import java.util.List;

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

	@Autowired
	private CursoService cursoService;
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public Long create(@RequestBody CursoDto dto) {
		return cursoService.create(dto);
	}
	
	@GetMapping("/{id}")
	public CursoDto read(@PathVariable(required = true, name = "id") Long id) {
		return cursoService.read(id);
	}
	
	@GetMapping
	public List<CursoDto> list() {
		return cursoService.list();
	}
	
	@PutMapping
	public void update(@RequestBody CursoDto dto) {
		cursoService.update(dto);
	}
}
