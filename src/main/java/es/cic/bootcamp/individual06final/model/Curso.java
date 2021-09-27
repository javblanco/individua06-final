package es.cic.bootcamp.individual06final.model;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;


@Entity
public class Curso {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	@Length(max = 50)
	private String nombre;
	
	@Length(max = 1000)
	private String descripcion;
	
	@ManyToOne
	private Tematica tematica;
	
	@javax.validation.constraints.NotNull
	@Min(value = 0)
	private Integer duracion;
	@Min(value = 0)
	private Integer cantidadAlumnos;
	@Min(value = 0)
	private Integer numeroTemas;
	private Boolean certificacion;
	
	@Digits(fraction = 2, integer = 6)
	private BigDecimal precio;
	
	private boolean activo;
	
	@PrePersist
	private void prePersist() {
		this.setActivo(true);
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
	public Tematica getTematica() {
		return tematica;
	}
	public void setTematica(Tematica tematica) {
		this.tematica = tematica;
	}
	public Integer getDuracion() {
		return duracion;
	}
	public void setDuracion(Integer duracion) {
		this.duracion = duracion;
	}
	public Integer getCantidadAlumnos() {
		return cantidadAlumnos;
	}
	public void setCantidadAlumnos(Integer cantidadAlumnos) {
		this.cantidadAlumnos = cantidadAlumnos;
	}
	public Integer getNumeroTemas() {
		return numeroTemas;
	}
	public void setNumeroTemas(Integer numeroTemas) {
		this.numeroTemas = numeroTemas;
	}
	public Boolean getCertificacion() {
		return certificacion;
	}
	public void setCertificacion(Boolean certificacion) {
		this.certificacion = certificacion;
	}
	public BigDecimal getPrecio() {
		return precio;
	}
	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}
	
	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}


	@Override
	public String toString() {
		return "Curso [nombre=" + nombre + ", duracion=" + duracion + ", cantidadAlumnos=" + cantidadAlumnos
				+ ", numeroTemas=" + numeroTemas + ", certificacion=" + certificacion + ", precio=" + precio + "]";
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
		Curso other = (Curso) obj;
		return Objects.equals(id, other.id);
	}
	
	
}
