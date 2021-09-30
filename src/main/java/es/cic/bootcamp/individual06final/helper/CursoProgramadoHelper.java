package es.cic.bootcamp.individual06final.helper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import es.cic.bootcamp.individual06final.dto.CursoProgramadoDto;
import es.cic.bootcamp.individual06final.model.Curso;
import es.cic.bootcamp.individual06final.model.CursoProgramado;

@Component
public class CursoProgramadoHelper {

	public CursoProgramadoDto entityToDto(CursoProgramado cursoProgramado) {
		CursoProgramadoDto dto = new CursoProgramadoDto();
		
		dto.setId(cursoProgramado.getId());
		
		if(cursoProgramado.getCurso() != null) {
			dto.setIdCurso(cursoProgramado.getId());
			dto.setNombreCurso(cursoProgramado.getCurso().getNombre());
		}
		
		dto.setFechaInicio(cursoProgramado.getFechaInicio());
		dto.setFechaFin(cursoProgramado.getFechaFin());
		dto.setInscripcion(cursoProgramado.isInscripcion());
		
		return dto;
	}
	
	public CursoProgramado dtoToEntity(CursoProgramadoDto dto, Curso curso) {
		CursoProgramado cursoProgramado = new CursoProgramado();
		
		cursoProgramado.setId(dto.getId());
		 
		if(curso !=null) {
			cursoProgramado.setCurso(curso);
		}
		
		cursoProgramado.setInscripcion(dto.isInscripcion());
		cursoProgramado.setFechaInicio(dto.getFechaInicio());
		cursoProgramado.setFechaFin(dto.getFechaFin());
		
		return cursoProgramado;
	}
	
	public void dtoToEntity(CursoProgramado cursoProgramado, CursoProgramadoDto dto) {		
		cursoProgramado.setInscripcion(dto.isInscripcion());
		cursoProgramado.setFechaInicio(dto.getFechaInicio());
		cursoProgramado.setFechaFin(dto.getFechaFin());
	}

	public List<CursoProgramadoDto> listEntityToListDto(List<CursoProgramado> lista) {
		return lista.stream().map(c -> this.entityToDto(c))
				.collect(Collectors.toList());
	}
	
	
}
