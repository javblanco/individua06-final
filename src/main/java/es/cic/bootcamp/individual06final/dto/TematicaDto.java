package es.cic.bootcamp.individual06final.dto;

import java.util.List;
import java.util.Objects;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import es.cic.bootcamp.individual06final.enumeration.Categoria;

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
	
	@NotNull
	@Length(min = 5, max = 20)
	private String referencia;
	
	@Enumerated(EnumType.STRING)
	private Categoria categoria;
	
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
	
	
	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	@Override
	public String toString() {
		return "TematicaDto [nombre=" + nombre + ", descripcion=" + descripcion + ", subtematicas=" + subtematicas
				+ ", referencia=" + referencia + ", categoria=" + categoria + "]";
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
