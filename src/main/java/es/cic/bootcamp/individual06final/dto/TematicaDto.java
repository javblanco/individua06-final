package es.cic.bootcamp.individual06final.dto;

import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

public class TematicaDto {

	private Long id;
	@NotBlank
	@Length(max = 50)
	private String nombre;
	
	@Length(max = 1000)
	private String descripcion;
	
	private String subtematicas;
	
	private List<String> listaSubtematicas;
	
	boolean eliminable;
	
	boolean activo;
	
	public TematicaDto() {
		this.activo = true;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getSubtematicas() {
		return subtematicas;
	}
	public void setSubtematicas(String subtematicas) {
		this.subtematicas = subtematicas;
	}
	public List<String> getListaSubtematicas() {
		return listaSubtematicas;
	}
	public void setListaSubtematicas(List<String> listaSubtematicas) {
		this.listaSubtematicas = listaSubtematicas;
	}
	
	public boolean isEliminable() {
		return eliminable;
	}
	public void setEliminable(boolean eliminable) {
		this.eliminable = eliminable;
	}
	
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	
	@Override
	public String toString() {
		return "TematicaDto [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", subtematicas="
				+ subtematicas + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TematicaDto other = (TematicaDto) obj;
		return Objects.equals(id, other.id);
	}
	
	
}
