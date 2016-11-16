package fr.insee.bar.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.reflection.Whitebox;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

public class AccueilControllerTestCase {

	@InjectMocks
	private AccueilController accueilController;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		Whitebox.setInternalState(accueilController, "name", "Spring MVC");
		this.mockMvc = MockMvcBuilders
			.standaloneSetup(accueilController)
			.setViewResolvers(new InternalResourceViewResolver("/WEB-INF/views/", ".jsp"))
			.build();
	}

	@Test
	public void welcome() throws Exception {
		mockMvc
			.perform(get("/"))
			.andExpect(status().is(HttpStatus.MOVED_PERMANENTLY.value()))
			.andExpect(model().attributeDoesNotExist("message"));
	}

	@Test
	public void hello() throws Exception {
		mockMvc
			.perform(get("/accueil"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("message"))
			.andExpect(view().name("accueil"));
	}
}
