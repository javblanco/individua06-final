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

import es.cic.bootcamp.individual06final.dto.TematicaDto;
import es.cic.bootcamp.individual06final.enumeration.Categoria;
import es.cic.bootcamp.individual06final.helper.TematicaHelper;
import es.cic.bootcamp.individual06final.model.Curso;
import es.cic.bootcamp.individual06final.model.Tematica;
import es.cic.bootcamp.individual06final.repository.CursoRepository;
import es.cic.bootcamp.individual06final.repository.TematicaRepository;


@SpringBootTest
@AutoConfigureMockMvc
class TematicaControllerIntegrationTest {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private TematicaRepository tematicaRepository;
	
	@Autowired
	private TematicaHelper tematicaHelper;
	
	@Autowired
	private ObjectMapper mapper;
	
	@Autowired
	private CursoRepository cursoRepository;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testCreate() throws Exception {
		TematicaDto dto = generarTematicaDto();
		
		String body = mapper.writeValueAsString(dto);
		
		MockHttpServletRequestBuilder request =
				post("/api/tematica")
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
		
		Tematica tematicaEnBBDD = tematicaRepository.findById(idResultado).get();
		
		assertThat(tematicaEnBBDD)
		.usingRecursiveComparison()
		.ignoringFields("id")
		.isEqualTo(tematicaHelper.dtoToEntity(dto));
	}

	@Test
	void testRead() throws Exception {
		Tematica tematica = generarTematica();
		
		tematicaRepository.save(tematica);
		
		MockHttpServletRequestBuilder request =
				get("/api/tematica/{id}", tematica.getId())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		TematicaDto dto = tematicaHelper.entityToDto(tematica, false);
		dto.setId(tematica.getId());
		
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
		Tematica tematica1 = generarTematica();
		Tematica tematica2 = generarTematica();
		
		List<Tematica> lista = List.of(tematica1, tematica2);
		tematicaRepository.saveAll(lista);
		
		
		List<TematicaDto> dtos = tematicaHelper.listEntityToListDto(lista);
		
		MockHttpServletRequestBuilder request =
				get("/api/tematica")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$",hasSize(greaterThanOrEqualTo(2))))
				.andReturn();
		
		String resultado = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
		
		List<TematicaDto> dtoResultado = mapper.readValue(resultado, new TypeReference<List<TematicaDto>>(){});
		
		assertThat(dtoResultado)
		.containsAnyElementsOf(dtos);
	}

	@Test
	void testUpdate() throws Exception {
		Tematica tematica = generarTematica();
		
		tematicaRepository.save(tematica);
		
		TematicaDto dto = tematicaHelper.entityToDto(tematica, false);
		dto.setId(tematica.getId());
		dto.setNombre("TIC");
		
		Tematica tematicaAModificar = tematicaHelper.dtoToEntity(dto);
		
		String body = mapper.writeValueAsString(dto);
		
		MockHttpServletRequestBuilder request =
				put("/api/tematica")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(body);
		
		mvc.perform(request)
		.andDo(print())
		.andExpect(status().isOk());
		
		Tematica tematicaEnBBDD = tematicaRepository.findById(tematica.getId()).get();
		
		assertThat(tematicaEnBBDD)
		.isEqualTo(tematicaAModificar);
		
		
	}

	@Test
	void testDelete() throws Exception {
		Tematica tematica = generarTematica();
		
		tematicaRepository.save(tematica);
		
		MockHttpServletRequestBuilder request =
				delete("/api/tematica/{id}", tematica.getId())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		mvc.perform(request)
		.andDo(print())
		.andExpect(status().isOk());
		
		Optional<Tematica> optional = tematicaRepository.findById(tematica.getId());
		
		assertTrue(optional.isEmpty(), "No se ha borrado correctamente el registro");
	}

	@Test
	void testDeleteConCursos() throws Exception {
		Tematica tematica = generarTematica();
		
		tematicaRepository.save(tematica);
		
		Curso curso = generarCurso(tematica);
		cursoRepository.save(curso);
		
		MockHttpServletRequestBuilder request =
				delete("/api/tematica/{id}", tematica.getId())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		mvc.perform(request)
		.andDo(print())
		.andExpect(status().isBadRequest());
	}
	
	@Test
	void testBaja() throws Exception{
		Tematica tematica = tematicaRepository.save(generarTematica());
		
		MockHttpServletRequestBuilder request =
				post("/api/tematica/baja/{id}", tematica.getId())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		mvc.perform(request)
		.andDo(print())
		.andExpect(status().isOk());
		
		Tematica tematicaEnBBDD = tematicaRepository.findById(tematica.getId()).get();
		
		assertFalse(tematicaEnBBDD.isActivo(), "No se ha conseguido dar de baja la temática");
	}
	
	@Test
	void testAlta() throws Exception{
		Tematica tematica = tematicaRepository.save(generarTematica());
		tematica.setActivo(false);
		
		tematicaRepository.save(tematica);
		
		MockHttpServletRequestBuilder request =
				post("/api/tematica/alta/{id}", tematica.getId())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		mvc.perform(request)
		.andDo(print())
		.andExpect(status().isOk());
		
		Tematica tematicaEnBBDD = tematicaRepository.findById(tematica.getId()).get();
		
		assertTrue(tematicaEnBBDD.isActivo(), "No se ha conseguido dar de alta la temática");
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
	
	private TematicaDto generarTematicaDto() {
		TematicaDto dto = new TematicaDto();

		dto.setNombre("Informática");
		dto.setDescripcion("Esta temática es de informática");
		dto.setSubtematicas("Big Data, Machine Learning");
		dto.setCategoria(Categoria.TECNOLOGIA);
		dto.setReferencia("REF-INF-TEC");
		
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
}
