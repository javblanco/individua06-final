package es.cic.bootcamp.individual06final;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import es.cic.bootcamp.individual06final.repository.TematicaRepository;
import es.cic.bootcamp.individual06final.repository.CursoRepository;
import es.cic.bootcamp.individual06final.enumeration.Categoria;
import es.cic.bootcamp.individual06final.model.Tematica;
import es.cic.bootcamp.individual06final.model.Curso;

@SpringBootApplication
public class Individual06FinalApplication {

	public static void main(String[] args) {
		SpringApplication.run(Individual06FinalApplication.class, args);
	}

	@Bean
  	public CommandLineRunner demo(TematicaRepository tematicaRepository, CursoRepository cursoRepository) {
		  return args -> {
			  Tematica tematica = generarTematica();
			  Tematica tematicaSave = tematicaRepository.save(tematica);

			  cursoRepository.save(generarCurso(tematicaSave));
		  };
	}

	private Tematica generarTematica() {
		Tematica tematica = new Tematica();

		tematica.setNombre("Informática");
		tematica.setDescripcion("Esta temática es de informática");
		tematica.setSubtematicas("Big Data, Machine Learning, Big Data, Machine Learning");
		tematica.setCategoria(Categoria.TECNOLOGIA);
		tematica.setReferencia("REF-INF-TEC");
		
		return tematica;
	}

	private Curso generarCurso(Tematica tematica) {
		Curso curso = new Curso();
		
		curso.setNombre("Big data en analítica");
		curso.setDescripcion("Un curso de big data");
		curso.setCantidadAlumnos(30);
		curso.setNumeroTemas(12);
		curso.setDuracion(600);
		curso.setCertificacion(true);
		curso.setPrecio(new BigDecimal("35.60"));
		curso.setTematica(tematica);
		
		return curso;
	}
}
