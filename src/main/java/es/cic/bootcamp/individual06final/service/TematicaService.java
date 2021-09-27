package es.cic.bootcamp.individual06final.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cic.bootcamp.individual06final.dto.TematicaDto;
import es.cic.bootcamp.individual06final.exception.TematicaException;
import es.cic.bootcamp.individual06final.helper.TematicaHelper;
import es.cic.bootcamp.individual06final.model.Tematica;
import es.cic.bootcamp.individual06final.repository.TematicaRepository;

@Service
@Transactional
public class TematicaService {

	private static final String ALTA_ENTRADA = "Aun no se ha dado de alta esta entrada";

	@Autowired
	private TematicaRepository tematicaRepository;
	
	@Autowired
	private TematicaHelper tematicaHelper;
	
	public Long create(TematicaDto dto) {
		if(dto.getId() != null && tematicaRepository.existsById(dto.getId())) {
			throw new TematicaException("Ya se ha dado de alta esta entrada");
		}
		Tematica tematica = tematicaHelper.dtoToEntity(dto);
		
		return tematicaRepository.save(tematica).getId();
	}
	
	public TematicaDto read(Long id) {
		Optional<Tematica> optional = tematicaRepository.findById(id);
		
		if(optional.isPresent()) {
			TematicaDto dto = tematicaHelper.entityToDto(optional.get());
			
			dto.setEliminable(this.isEliminable(dto));
			
			return dto;
		} else {
			return null;
		}
	}


	public List<TematicaDto> list() {
		List<Tematica> lista = new ArrayList<>();
		
		tematicaRepository
		.findAll()
		.forEach(lista::add);
		
		return tematicaHelper.listEntityToListDto(lista);
	}
	
	public void update(TematicaDto dto) {
		if(dto.getId() == null) {
			throw new TematicaException(ALTA_ENTRADA);			
		}
		Optional<Tematica> optional = tematicaRepository.findById(dto.getId());
		
		if(optional.isPresent()) {
			Tematica tematica = optional.get();
			tematicaHelper.dtoToEntity(tematica, dto);
			
			tematicaRepository.save(tematica);
		} else {
			throw new TematicaException(ALTA_ENTRADA);	
		}
		
	}
	
	public void delete(Long id) {
		if(tematicaRepository.existsById(id)) {
			tematicaRepository.deleteById(id);
		} else {
			throw new TematicaException(ALTA_ENTRADA);	
		}
	}
	
	private boolean isEliminable(TematicaDto dto) {
		return false;
	}
}
