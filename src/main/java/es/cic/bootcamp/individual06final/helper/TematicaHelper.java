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

	public TematicaDto entityToDto(Tematica tematica, boolean eliminable) {
		TematicaDto dto = entityToDto(tematica);
		dto.setEliminable(eliminable);
		
		
		return dto;
	}
	
	public TematicaDto entityToDto(Tematica tematica) {
		TematicaDto dto = new TematicaDto();
		dto.setId(tematica.getId());
		dto.setNombre(tematica.getNombre());
		dto.setDescripcion(tematica.getDescripcion());
		dto.setSubtematicas(tematica.getSubtematicas());
		dto.setActivo(tematica.isActivo());
		dto.setCategoria(tematica.getCategoria());
		dto.setReferencia(tematica.getReferencia());
		
		List<String> listaSubtematicas = new ArrayList<>();
		
		if(tematica.getSubtematicas() != null ) {
			listaSubtematicas = Arrays.asList(tematica.getSubtematicas().split(", "));
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
		tematica.setCategoria(dto.getCategoria());
		tematica.setReferencia(dto.getReferencia());
		
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
		
		if(dto.getListaSubtematicas() != null && !dto.getListaSubtematicas().isEmpty()) {
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

	public List<TematicaDto> listEntityToListDtoElimnable(List<Object[]> lista) {
		return lista.stream()
				.map(t -> this.entityToDto((Tematica) t[0], (boolean) t[1]))
				.collect(Collectors.toList());
	}
}
