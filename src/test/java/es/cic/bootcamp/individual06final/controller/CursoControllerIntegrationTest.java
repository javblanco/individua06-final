package es.cic.bootcamp.individual06final.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
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

import es.cic.bootcamp.individual06final.dto.CursoDto;
import es.cic.bootcamp.individual06final.helper.CursoHelper;
import es.cic.bootcamp.individual06final.model.Curso;
import es.cic.bootcamp.individual06final.model.Tematica;
import es.cic.bootcamp.individual06final.repository.CursoRepository;
import es.cic.bootcamp.individual06final.repository.TematicaRepository;

@SpringBootTest
@AutoConfigureMockMvc
class CursoControllerIntegrationTest {


	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private CursoRepository cursoRepository;
	
	@Autowired
	private TematicaRepository tematicaRepository;
	
	@Autowired
	private CursoHelper cursoHelper;
	
	@Autowired
	private ObjectMapper mapper;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testCreate() throws Exception {
		Tematica tematica = tematicaRepository.save(generarTematica());
		
		CursoDto dto = generarCursoDto(tematica);
		
		String body = mapper.writeValueAsString(dto);
		
		MockHttpServletRequestBuilder request =
				post("/api/curso")
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
		
		Curso cursoEnBBDD = cursoRepository.findById(idResultado).get();
		
		assertThat(cursoEnBBDD)
		.usingRecursiveComparison()
		.ignoringFields("id")
		.isEqualTo(cursoHelper.dtoToEntity(dto, tematica));
	}

	@Test
	void testRead() throws Exception {
		Tematica tematica = tematicaRepository.save(generarTematica());
		Curso curso = generarCurso(tematica);
		
		cursoRepository.save(curso);
		
		MockHttpServletRequestBuilder request =
				get("/api/curso/{id}", curso.getId())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		CursoDto dto = cursoHelper.entityToDto(curso);
		dto.setId(curso.getId());
		
		String respuesta = mapper.writeValueAsString(dto);
		
		MvcResult result = 
				mvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", notNullValue()))
				.andReturn();
		
		String resultado = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
		
		assertEquals(mapper.readTree(respuesta), mapper.readTree(resultado), "El registro no es el esperado");
	}

	@Test
	void testList() throws Exception {
		Tematica tematica = tematicaRepository.save(generarTematica());
		Curso curso1 = generarCurso(tematica);
		Curso curso = generarCurso(tematica);
		
		List<Curso> lista = List.of(curso1, curso);
		cursoRepository.saveAll(lista);
		
		
		List<CursoDto> dtos = cursoHelper.listEntityToListDto(lista);
		
		MockHttpServletRequestBuilder request =
				get("/api/curso")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$",hasSize(greaterThanOrEqualTo(2))))
				.andReturn();
		
		String resultado = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
		
		List<CursoDto> dtoResultado = mapper.readValue(resultado, new TypeReference<List<CursoDto>>(){});
		
		assertThat(dtoResultado)
		.containsAnyElementsOf(dtos);
	}

	@Test
	void testUpdate() throws Exception {
		Tematica tematica = tematicaRepository.save(generarTematica());
		Curso curso = generarCurso(tematica);
		
		cursoRepository.save(curso);
		
		CursoDto dto = cursoHelper.entityToDto(curso);
		dto.setId(curso.getId());
		dto.setNombre("BBDD 101");
		
		Curso cursoAModificar = cursoHelper.dtoToEntity(dto, tematica);
		
		String body = mapper.writeValueAsString(dto);
		
		MockHttpServletRequestBuilder request =
				put("/api/curso")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(body);
		
		mvc.perform(request)
		.andDo(print())
		.andExpect(status().isOk());
		
		Curso cursoEnBBDD = cursoRepository.findById(curso.getId()).get();
		
		assertThat(cursoEnBBDD)
		.usingRecursiveComparison()
		.isEqualTo(cursoAModificar);
		
		
	}
	
	@Test
	void testBaja() throws Exception{
		Tematica tematica = tematicaRepository.save(generarTematica());
		Curso curso = generarCurso(tematica);
		
		cursoRepository.save(curso);
		
		MockHttpServletRequestBuilder request =
				post("/api/curso/baja/{id}", curso.getId())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		mvc.perform(request)
		.andDo(print())
		.andExpect(status().isOk());
		
		Curso cursoEnBBDD = cursoRepository.findById(curso.getId()).get();
		
		assertFalse(cursoEnBBDD.isActivo(), "No se ha conseguido dar de baja el curso");
	}
	
	@Test
	void testAlta() throws Exception{
		Tematica tematica = tematicaRepository.save(generarTematica());
		Curso curso = generarCurso(tematica);
		
		cursoRepository.save(curso);
		curso.setActivo(false);
		cursoRepository.save(curso);
		
		MockHttpServletRequestBuilder request =
				post("/api/curso/alta/{id}", curso.getId())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		mvc.perform(request)
		.andDo(print())
		.andExpect(status().isOk());
		
		Curso cursoEnBBDD = cursoRepository.findById(curso.getId()).get();
		
		assertTrue(cursoEnBBDD.isActivo(), "No se ha conseguido dar de alta el curso");
	}
	
	private CursoDto generarCursoDto(Tematica tematica) {
		CursoDto dto = new CursoDto();
		
		dto.setNombre("Big data en analítica");
		dto.setDescripcion("Un curso de big data");
		dto.setCantidadAlumnos(30);
		dto.setNumeroTemas(12);
		dto.setDuracion(600);
		dto.setCertificacion(true);
		dto.setPrecio(new BigDecimal("35.60"));
		dto.setIdTematica(tematica.getId());
		
		return dto;
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
		
		return tematica;
	}

}
