package es.cic.bootcamp.individual06final.dto;

import java.time.LocalDate;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

public class CursoProgramadoDto {

	private Long id;
	
	@NotNull
	private Long idCurso;
	
	private String nombreCurso;
	
	@NotNull
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate fechaInicio;
	
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate fechaFin;
	
	private boolean inscripcion;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(Long idCurso) {
		this.idCurso = idCurso;
	}

	public String getNombreCurso() {
		return nombreCurso;
	}

	public void setNombreCurso(String nombreCurso) {
		this.nombreCurso = nombreCurso;
	}

	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDate getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
	}

	public boolean isInscripcion() {
		return inscripcion;
	}

	public void setInscripcion(boolean inscripcion) {
		this.inscripcion = inscripcion;
	}

	@Override
	public String toString() {
		String inscripciones = inscripcion ? "abiertas" : "cerradas";
		return "CursoProgramadoDto [nombreCurso=" + nombreCurso + ", fechaInicio=" + fechaInicio + ", fechaFin="
				+ fechaFin + ", inscripciones: " + inscripciones +"]";
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
		CursoProgramadoDto other = (CursoProgramadoDto) obj;
		return Objects.equals(id, other.id);
	}
	
	
}
