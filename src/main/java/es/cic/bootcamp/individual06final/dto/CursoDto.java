package es.cic.bootcamp.individual06final.dto;

import java.math.BigDecimal;
import java.util.Objects;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Currency;
import org.hibernate.validator.constraints.Length;

public class CursoDto {
	
	private Long id;
	
	@NotBlank
	@Length(max = 50)
	private String nombre;
	
	@Length(max = 1000)
	private String descripcion;	
	
	@NotNull
	@Min(value = 0)
	private Integer duracion;
	@Min(value = 0)
	private Integer cantidadAlumnos;
	@Min(value = 0)
	private Integer numeroTemas;
	
	private Boolean certificacion;
	
	@NotNull
	private Long idTematica;
	
	private String nombreTematica;
	
	private boolean activo;
	
	@Digits(fraction = 2, integer = 6)
	@Currency(value = "EUR")
	private BigDecimal precio;
	
	public CursoDto() {
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

	public Long getIdTematica() {
		return idTematica;
	}
	public void setIdTematica(Long idTematica) {
		this.idTematica = idTematica;
	}
	public String getNombreTematica() {
		return nombreTematica;
	}
	public void setNombreTematica(String nombreTematica) {
		this.nombreTematica = nombreTematica;
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
	public boolean isActivo() {
		return activo;
	}


	public void setActivo(boolean activo) {
		this.activo = activo;
	}


	public BigDecimal getPrecio() {
		return precio;
	}


	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}


	@Override
	public String toString() {
		return "CursoDto [id=" + id + ", nombre=" + nombre + ", duracion=" + duracion + ", cantidadAlumnos="
				+ cantidadAlumnos + ", numeroTemas=" + numeroTemas + ", certificacion=" + certificacion
				+ ", nombreTematica=" + nombreTematica + "]";
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
		CursoDto other = (CursoDto) obj;
		return Objects.equals(id, other.id);
	}
	
	
}
