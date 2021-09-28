package es.cic.bootcamp.individual06final;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import es.cic.bootcamp.individual06final.repository.TematicaRepository;
import es.cic.bootcamp.individual06final.enumeration.Categoria;
import es.cic.bootcamp.individual06final.model.Tematica;

@SpringBootApplication
public class Individual06FinalApplication {

	public static void main(String[] args) {
		SpringApplication.run(Individual06FinalApplication.class, args);
	}

	@Bean
  	public CommandLineRunner demo(TematicaRepository tematicaRepository) {
		  return args -> {
			  tematicaRepository.save(generarTematica());
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
}
