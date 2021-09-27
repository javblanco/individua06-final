package es.cic.bootcamp.individual06final.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import es.cic.bootcamp.individual06final.enumeration.Categoria;

@Entity
public class Tematica {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	@Length(max = 50)
	private String nombre;
	
	@Length(max = 1000)
	private String descripcion;
	
	@NotNull
	@Length(min = 5, max = 20)
	private String referencia;
	
	@Enumerated(EnumType.STRING)
	private Categoria categoria;
	
	private String subtematicas;
	
	private boolean activo;
	
	@PrePersist
	private void prePersist() {
		activo = true;
	}

	public Tematica() {
	}
	

	public Tematica(String nombre, String descripcion, String referencia, Categoria categoria, String subtematicas) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.referencia = referencia;
		this.categoria = categoria;
		this.subtematicas = subtematicas;
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
		return "Tematica [nombre=" + nombre + ", descripcion=" + descripcion + ", referencia=" + referencia
				+ ", categoria=" + categoria + ", subtematicas=" + subtematicas + "]";
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
		Tematica other = (Tematica) obj;
		return Objects.equals(id, other.id);
	}
	
	
	
	
}
