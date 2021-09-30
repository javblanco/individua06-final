package es.cic.bootcamp.individual06final.model;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
public class CursoProgramado {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	private Curso curso;
	
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

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
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
		return "CursoProgramado [id=" + id + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin
				+ ", inscripcion=" + inscripcion + "]";
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
		CursoProgramado other = (CursoProgramado) obj;
		return Objects.equals(id, other.id);
	}
	
	
}
