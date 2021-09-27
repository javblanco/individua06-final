package es.cic.bootcamp.individual06final.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import es.cic.bootcamp.individual06final.dto.TematicaDto;
import es.cic.bootcamp.individual06final.model.Tematica;

@Component
public class TematicaHelper {

	public TematicaDto entityToDto(Tematica tematica) {
		TematicaDto dto = new TematicaDto();
		dto.setId(tematica.getId());
		dto.setDescripcion(dto.getDescripcion());
		dto.setSubtematicas(tematica.getSubtematicas());
		dto.setActivo(tematica.isActivo());
		
		List<String> listaSubtematicas = new ArrayList<>();
		
		if(tematica.getSubtematicas() != null) {
			listaSubtematicas = Arrays.asList(tematica.getSubtematicas().split(","));
		}
		
		dto.setListaSubtematicas(listaSubtematicas);
		
		
		return dto;
	}
	
	public Tematica dtoToEntity(TematicaDto dto) {
		Tematica tematica = new Tematica();
		tematica.setId(dto.getId());
		tematica.setNombre(dto.getNombre());
		tematica.setDescripcion(dto.getDescripcion());
		tematica.setActivo(dto.isActivo());
		
		StringBuilder subtematicas = listarSubtematicas(dto);
		
		tematica.setSubtematicas(subtematicas.toString());
		
		return tematica;
	}
	
	public void dtoToEntity(Tematica tematica, TematicaDto dto) {
		tematica.setNombre(dto.getNombre());
		tematica.setDescripcion(dto.getDescripcion());
		tematica.setActivo(dto.isActivo());
		
		StringBuilder subtematicas = listarSubtematicas(dto);
		
		tematica.setSubtematicas(subtematicas.toString());
	}

	private StringBuilder listarSubtematicas(TematicaDto dto) {
		StringBuilder subtematicas = new StringBuilder();
		
		if(dto.getListaSubtematicas() != null) {
			dto.getListaSubtematicas()
			.forEach(s -> subtematicas.append(String.format("%s, ", s)));
			
			subtematicas.deleteCharAt(subtematicas.length()-1);
			subtematicas.deleteCharAt(subtematicas.length()-1);
		}
		return subtematicas;
	}

	public List<TematicaDto> listEntityToListDto(List<Tematica> lista) {
		return lista.stream()
				.map(t -> this.entityToDto(t))
				.collect(Collectors.toList());
	}
}
