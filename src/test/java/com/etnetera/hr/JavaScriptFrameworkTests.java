package com.etnetera.hr;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.repository.JavaScriptFrameworkRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Class used for Spring Boot/MVC based tests.
 * 
 * @author Etnetera
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class JavaScriptFrameworkTests {

	@Autowired
	private MockMvc mockMvc;

	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private JavaScriptFrameworkRepository repository;

	@Before
	public void prepareData() throws Exception {
		JavaScriptFramework react = new JavaScriptFramework("ReactJS", 1.1, "2019-1-1", 10);
		JavaScriptFramework vue = new JavaScriptFramework("Vue.js", 1.2, "2019-2-1", 12);

		repository.save(react);
		repository.save(vue);
	}

	@Test
	public void frameworksTest() throws Exception {
//		prepareData();

		mockMvc.perform(get("/frameworks")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].id", is(1)))
				.andExpect(jsonPath("$[0].name", is("ReactJS")))
				.andExpect(jsonPath("$[0].version", is(1.1)))
				.andExpect(jsonPath("$[0].deprecationDate", is("2019-1-1")))
				.andExpect(jsonPath("$[0].hypeLevel", is(10)))
				.andExpect(jsonPath("$[1].id", is(2)))
				.andExpect(jsonPath("$[1].name", is("Vue.js")))
				.andExpect(jsonPath("$[1].version", is(1.2)))
				.andExpect(jsonPath("$[1].deprecationDate", is("2019-2-1")))
				.andExpect(jsonPath("$[1].hypeLevel", is(12)));
	}

	@Test
	public void addFrameworkInvalid() throws JsonProcessingException, Exception {
		JavaScriptFramework framework = new JavaScriptFramework();
		mockMvc.perform(
				post("/add").contentType(MediaType.APPLICATION_JSON_UTF8).content(mapper.writeValueAsBytes(framework)))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.errors", hasSize(1)))
				.andExpect(jsonPath("$.errors[0].field", is("name")))
				.andExpect(jsonPath("$.errors[0].message", is("NotEmpty")));

		framework.setName("verylongnameofthejavascriptframeworkjavaisthebest");
		mockMvc.perform(
				post("/add").contentType(MediaType.APPLICATION_JSON_UTF8).content(mapper.writeValueAsBytes(framework)))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.errors", hasSize(1)))
				.andExpect(jsonPath("$.errors[0].field", is("name")))
				.andExpect(jsonPath("$.errors[0].message", is("Size")));
	
	}

	
	 @Test 
	 public void createNewFramework() throws Exception {
	 mockMvc.perform(post("/add") .content(mapper.writeValueAsBytes(new
	 JavaScriptFramework("ReactJS",1.1,"2019-1-1",10)))
	  .contentType(MediaType.APPLICATION_JSON_UTF8)
	  .accept(MediaType.APPLICATION_JSON_UTF8)) .andExpect(status().isCreated()); }
	 
	@Test
	public void updateFramework() throws Exception {
//		prepareData();
		mockMvc.perform(put("/frameworks/{id}", 2)
				.content(mapper.writeValueAsBytes(new JavaScriptFramework("ReactJS2", 1.15, "2019-1-8", 25)))
				.contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk()).andExpect(jsonPath("$.name").value("ReactJS2"))
				.andExpect(jsonPath("$.version").value(1.15)).andExpect(jsonPath("$.deprecationDate").value("2019-1-8"))
				.andExpect(jsonPath("$.hypeLevel").value(25));
	}

	@Test
	public void deleteFramework() throws Exception {
//		prepareData();
		mockMvc.perform(delete("/frameworks/{id}", 1)).andExpect(status().isAccepted());
	}

	@Test
	public void getFramework() throws Exception {
//		prepareData();
		mockMvc.perform(get("/frameworks/{id}", 1)
				.accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk()).andExpect(jsonPath("$.id").value(1));
	}
}
