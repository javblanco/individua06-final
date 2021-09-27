package es.cic.bootcamp.individual06final.helper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import es.cic.bootcamp.individual06final.dto.CursoDto;
import es.cic.bootcamp.individual06final.model.Curso;
import es.cic.bootcamp.individual06final.model.Tematica;

@Component
public class CursoHelper {
	
	public CursoDto entityToDto(Curso curso) {
		CursoDto dto = new CursoDto();
		
		dto.setId(curso.getId());
		dto.setNombre(curso.getNombre());
		dto.setDescripcion(curso.getDescripcion());
		
		if(curso.getTematica() != null) {
			dto.setIdTematica(curso.getTematica().getId());
			dto.setNombreTematica(curso.getTematica().getNombre());
		}
		
		dto.setNumeroTemas(curso.getNumeroTemas());
		dto.setCantidadAlumnos(curso.getCantidadAlumnos());
		dto.setCertificacion(curso.getCertificacion());
		dto.setActivo(curso.isActivo());
		dto.setPrecio(curso.getPrecio());
		dto.setDuracion(curso.getDuracion());
		
		return dto;
	}
	
	public Curso dtoToEntity(CursoDto dto, Tematica tematica) {
		Curso curso = new Curso();
		
		if(tematica != null) {
			curso.setTematica(tematica);
		}
		
		curso.setId(dto.getId());
		curso.setNombre(dto.getNombre());
		curso.setDescripcion(dto.getDescripcion());
		curso.setActivo(dto.isActivo());
		curso.setCantidadAlumnos(dto.getCantidadAlumnos());
		curso.setNumeroTemas(dto.getNumeroTemas());
		curso.setDuracion(dto.getDuracion());
		curso.setCertificacion(dto.getCertificacion());
		curso.setPrecio(dto.getPrecio());
		
		return curso;
	}
	
	public void dtoToEntity(CursoDto dto, Curso curso, Tematica tematica) {
		if(tematica != null) {
			curso.setTematica(tematica);
		}
		
		curso.setActivo(dto.isActivo());
		curso.setNombre(dto.getNombre());
		curso.setDescripcion(dto.getDescripcion());
		curso.setCantidadAlumnos(dto.getCantidadAlumnos());
		curso.setCertificacion(dto.getCertificacion());
		curso.setDuracion(dto.getDuracion());
		curso.setNumeroTemas(dto.getNumeroTemas());
		curso.setPrecio(dto.getPrecio());
	}
	
	public List<CursoDto> listEntityToListDto(List<Curso> lista) {
		return lista.stream()
				.map(c -> this.entityToDto(c))
				.collect(Collectors.toList());
		
	}
	
}
