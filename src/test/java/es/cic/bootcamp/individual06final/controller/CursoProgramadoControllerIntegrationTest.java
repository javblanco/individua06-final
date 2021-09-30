package es.cic.bootcamp.individual06final.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.cic.bootcamp.individual06final.dto.CursoProgramadoDto;
import es.cic.bootcamp.individual06final.enumeration.Categoria;
import es.cic.bootcamp.individual06final.helper.CursoProgramadoHelper;
import es.cic.bootcamp.individual06final.model.Curso;
import es.cic.bootcamp.individual06final.model.CursoProgramado;
import es.cic.bootcamp.individual06final.model.Tematica;
import es.cic.bootcamp.individual06final.repository.CursoProgramadoRepository;
import es.cic.bootcamp.individual06final.repository.CursoRepository;
import es.cic.bootcamp.individual06final.repository.TematicaRepository;

@SpringBootTest
@AutoConfigureMockMvc
class CursoProgramadoControllerIntegrationTest {
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private CursoProgramadoRepository cursoProgramadoRepository;
	
	@Autowired
	private CursoProgramadoHelper cursoProgramadoHelper;
	
	@Autowired
	private CursoRepository cursoRepository;
	
	@Autowired
	private TematicaRepository tematicaRepository;	

	@Autowired
	private ObjectMapper mapper;
	

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testCreate() throws Exception {
		Tematica tematica = tematicaRepository.save(generarTematica());
		
		Curso curso = cursoRepository.save(generarCurso(tematica));
		
		
		
		CursoProgramadoDto dto = generarProgramadoDto(curso);
		
		String body = mapper.writeValueAsString(dto);
		
		MockHttpServletRequestBuilder request =
				post("/api/programacion")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(body);
		
		MvcResult result =  mvc.perform(request)
							.andDo(print())
							.andExpect(status().isCreated())
							.andExpect(jsonPath("$", notNullValue()))
							.andReturn();
		
		String respuesta = result.getResponse().getContentAsString();
		
		Long idResultado = Long.parseLong(respuesta);
		
		CursoProgramado cursoProgramadoEnBBDD = cursoProgramadoRepository.findById(idResultado).get();
		
		assertThat(cursoProgramadoEnBBDD)
		.usingRecursiveComparison()
		.ignoringFields("id")
		.isEqualTo(cursoProgramadoHelper.dtoToEntity(dto, curso));
	}

	

	@Test
	void testList() throws Exception {
		Tematica tematica = tematicaRepository.save(generarTematica());
		Curso curso = cursoRepository.save(generarCurso(tematica));
		CursoProgramado programado1 = generarProgramado(curso);
		CursoProgramado programado2 = generarProgramado(curso);
		
		List<CursoProgramado> lista = List.of(programado1, programado2);
		cursoProgramadoRepository.saveAll(lista);
		
		
		List<CursoProgramadoDto> dtos = cursoProgramadoHelper.listEntityToListDto(lista);
		
		MockHttpServletRequestBuilder request =
				get("/api/programacion")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$",hasSize(greaterThanOrEqualTo(2))))
				.andReturn();
		
		String resultado = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
		
		List<CursoProgramadoDto> dtoResultado = mapper.readValue(resultado, new TypeReference<List<CursoProgramadoDto>>(){});
		
		assertThat(dtoResultado)
		.containsAnyElementsOf(dtos);
	}

	@Test
	void testUpdate() throws Exception {
		Tematica tematica = tematicaRepository.save(generarTematica());
		Curso curso = cursoRepository.save(generarCurso(tematica));
		
		CursoProgramado cursoProgramado = cursoProgramadoRepository.save(generarProgramado(curso));
		
		CursoProgramadoDto dto = cursoProgramadoHelper.entityToDto(cursoProgramado);
		
		dto.setId(cursoProgramado.getId());
		dto.setFechaFin(null);
		
		CursoProgramado programadoAModificar = cursoProgramadoHelper.dtoToEntity(dto, curso);
		
		String body = mapper.writeValueAsString(dto);
		
		MockHttpServletRequestBuilder request =
				put("/api/programacion")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(body);
		
		mvc.perform(request)
		.andDo(print())
		.andExpect(status().isOk());
		
		CursoProgramado programadoEnBBDD = cursoProgramadoRepository.findById(cursoProgramado.getId()).get();
		
		assertThat(programadoEnBBDD)
		.usingRecursiveComparison()
		.isEqualTo(programadoAModificar);
		
		
	}
	
	@Test
	void testDelete() throws Exception {
		Tematica tematica = tematicaRepository.save(generarTematica());
		Curso curso = cursoRepository.save(generarCurso(tematica));
		
		CursoProgramado cursoProgramado = cursoProgramadoRepository.save(generarProgramado(curso));

		MockHttpServletRequestBuilder request =
				delete("/api/programacion/{id}", cursoProgramado.getId())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		mvc.perform(request)
		.andDo(print())
		.andExpect(status().isOk());
		
		Optional<CursoProgramado> optional = cursoProgramadoRepository.findById(cursoProgramado.getId());

		assertTrue(optional.isEmpty(), "No se ha borrado la entrada.");
	}
	
	@Test
	void testAbrirInscripciones() throws Exception{
		Tematica tematica = tematicaRepository.save(generarTematica());
		Curso curso = cursoRepository.save(generarCurso(tematica));
		
		CursoProgramado cursoProgramado = cursoProgramadoRepository.save(generarProgramado(curso));
		
		MockHttpServletRequestBuilder request =
				post("/api/programacion/abrir/{id}", cursoProgramado.getId())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		mvc.perform(request)
		.andDo(print())
		.andExpect(status().isOk());
		
		CursoProgramado cursoProgramadoEnBBDD = cursoProgramadoRepository.findById(cursoProgramado.getId()).get();
		
		assertTrue(cursoProgramadoEnBBDD.isInscripcion(), "No se ha conseguido abrir las inscripciones al curso el curso");
	}
	
	@Test
	void testCerrarInscripciones() throws Exception{
		Tematica tematica = tematicaRepository.save(generarTematica());
		Curso curso = cursoRepository.save(generarCurso(tematica));
		
		CursoProgramado cursoProgramado = cursoProgramadoRepository.save(generarProgramado(curso));
		
		cursoProgramado.setInscripcion(true);
		cursoProgramadoRepository.save(cursoProgramado);
		
		MockHttpServletRequestBuilder request =
				post("/api/programacion/cerrar/{id}", cursoProgramado.getId())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		mvc.perform(request)
		.andDo(print())
		.andExpect(status().isOk());
		
		CursoProgramado cursoProgramadoEnBBDD = cursoProgramadoRepository.findById(cursoProgramado.getId()).get();
		
		assertFalse(cursoProgramadoEnBBDD.isInscripcion(), "No se ha conseguido cerrar las inscripciones al curso el curso");
	}
	
	private CursoProgramadoDto generarProgramadoDto (Curso curso) {
		CursoProgramadoDto dto = new CursoProgramadoDto();
		
		dto.setIdCurso(curso.getId());
		dto.setNombreCurso(curso.getNombre());
		dto.setInscripcion(false);
		dto.setFechaInicio(LocalDate.of(2021, Month.APRIL, 14));
		dto.setFechaFin(LocalDate.of(2021, Month.SEPTEMBER, 30));
		
		return dto;
	}
	
	private CursoProgramado generarProgramado(Curso curso) {
		CursoProgramado cursoProgramado = new CursoProgramado();
		
		cursoProgramado.setCurso(curso);
		cursoProgramado.setInscripcion(false);
		cursoProgramado.setFechaInicio(LocalDate.of(2021, Month.APRIL, 14));
		cursoProgramado.setFechaFin(LocalDate.of(2021, Month.SEPTEMBER, 30));
		
		return cursoProgramado;
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
	
	private Tematica generarTematica() {
		Tematica tematica = new Tematica();

		tematica.setNombre("Informática");
		tematica.setDescripcion("Esta temática es de informática");
		tematica.setSubtematicas("Big Data, Machine Learning");
		tematica.setCategoria(Categoria.TECNOLOGIA);
		tematica.setReferencia("REF-INF-TEC");
		
		return tematica;
	}


}
