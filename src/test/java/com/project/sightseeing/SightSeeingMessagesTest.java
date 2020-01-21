package com.project.sightseeing;


import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class SightSeeingMessagesTest {

	
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void shouldReturnDefaultMessage() throws Exception {
		this.mockMvc.perform(get("/home")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("def")));
	}
	
	@Test
	public void shouldReturnLogin() throws Exception {
		this.mockMvc.perform(get("/home/login")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("login")));
	}
	
	@Test
	public void shouldReturnhtmlTemplate() throws Exception {
		this.mockMvc.perform(get("/object/addCitSh")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Wybierz miasto")));
	}
	
	@Test
	public void shouldReturnhtmlTemplate2() throws Exception {
		this.mockMvc.perform(get("/object//add/1")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Dodawanie Obiektów")));
	}
	
	@Test
	public void shouldReturnhtmlTemplate3() throws Exception {
		this.mockMvc.perform(get("/object//object/2")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Zalew Zakrzówek")));
	}
	
	@Test
	public void shouldReturnhtmlTemplate4() throws Exception {
		this.mockMvc.perform(get("/object//all")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Obiekty")));
	}
	
	@Test
	public void shouldReturnhtmlTemplate5() throws Exception {
		this.mockMvc.perform(get("/object/setDistance/2/2")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Wprowadz dystans")));
	}
	
	
	@Test
	public void shouldReturnhtmlTemplate6() throws Exception {
		this.mockMvc.perform(get("/comm/uadd/2/1")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Dodawanie Komentarza")));
	}

	
	@Test
	public void shouldReturnhtmlTemplate7() throws Exception {
		this.mockMvc.perform(get("/comm/all")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Komentarze")));
	}
	
	@Test
	public void shouldReturnhtmlTemplate8() throws Exception {
		this.mockMvc.perform(get("/city/sel")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Wybor miasta")));
	}
	
	@Test
	public void shouldReturnhtmlTemplate9() throws Exception {
		this.mockMvc.perform(get("/city//miasto/2")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Wybór obiektu")));
	}
	
	@Test
	public void shouldReturnhtmlTemplate10() throws Exception {
		this.mockMvc.perform(get("/city/all")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Miasta")));
	}
	
	
}
