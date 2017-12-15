package fr.insee.bar.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
public class NouveauClientControllerTest {

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
	public void nouveauClientServeur() throws Exception {
		this.mockMvc
			.perform(get("/client/nouveau"))
			.andExpect(model().attributeDoesNotExist("client"))
			.andExpect(status().isForbidden())
			.andExpect(view().name("exception"));
	}

	@Test
	public void nouveauClientPostSuccess() throws Exception {
		this.mockMvc
			.perform(post("/client/nouveau")
				.param("nom", "Pr�nom Nom")
				.param("email", "prenom.nom@gmail.com")
				.param("dateNaissance", "21/04/1984"))
			.andExpect(status().isFound())
			.andExpect(model().attributeDoesNotExist("client"))
			.andExpect(model().hasNoErrors())
			.andExpect(redirectedUrl("/clients"))
			.andExpect(flash().attributeExists("nouveauClient"));
	}

	@Test
	public void nouveauClientPostError() throws Exception {
		this.mockMvc
			.perform(post("/client/nouveau")
				.param("nom", "Pr�nom Nom")
				.param("email", "")
				.param("dateNaissance", "21/04/1984"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("client"))
			.andExpect(model().errorCount(1))
			.andExpect(view().name("nouveau-client"));
	}
}
