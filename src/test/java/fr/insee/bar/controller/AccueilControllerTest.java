package fr.insee.bar.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext.xml", "classpath:dispatcher-servlet.xml" })
@ActiveProfiles("serveur")
@WebAppConfiguration
public class AccueilControllerTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@Before
	public void before() {
		this.mockMvc = MockMvcBuilders
			.webAppContextSetup(this.webApplicationContext)
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
			.andExpect(view().name("accueil"))
			.andExpect(forwardedUrl("/WEB-INF/views/layouts/default-layout.jsp"));
	}
}
